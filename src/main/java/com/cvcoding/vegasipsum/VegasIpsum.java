/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvcoding.vegasipsum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johnr
 */
public class VegasIpsum implements LoremIpsum {

    private static final Random RAND = new Random();
    private static final String LOREM_FILE_NAME = "./src/main/resources/lorem.ipsum.seed.txt";
    
    // the default number of paragraphs for the generator is 3.
    private final int _DEFAULT_PARAGRAPH_CNT = 3;
    
    // if people want to get a sentence at a time one option is to set min
    // and max to 1.
    private int _minParagraphSize;
    
    // if people want huge paragraphs (large number of sentences) then this 
    // is what they'll want to change.
    private int _maxParagraphSize;
    
    // sentences need to have some length (min/max) associated with them.
    private int _minSentenceSize;
    private int _maxSentenceSize;
    
    // a list of lorem ipsum words *or* phrases. So, it is possible to
    // have an array that looks like:
    // [0] : 'lorem'
    // [1] : 'ipsum'
    // [2] : 'high roller'
    // [3] : 'what happens in vegas stays in vegas'
    // [4] : 'dolor'
    // this is important so that entire phrases can stay intact. 
    // the phrases are not important for the standard lorem ipusm but they
    // are for people who want to customize (insert) their own content into
    // the lorem ipsum vocabulary. 
    private final ArrayList<String> _loremChain;
    
    // as we fetch words or paragraphs we simply iterate through entire
    // loremChain array, wrapping around if necessary.
    private int _curWordLocation;
    
    public VegasIpsum() {
        // standard paragraphs are 3 to 6 sentences long.
        this._minParagraphSize = 3;
        this._maxParagraphSize = 6;
        
        // standard sentences are 5 to 10 "words" long (see note on
        // contents of a loremChain)
        this._minSentenceSize = 5;
        this._maxSentenceSize = 10;

        this._loremChain = new ArrayList<>();
        this._curWordLocation = 0;
    }

    public void setSeed(long seed) {
        RAND.setSeed(seed);
    }

    public List<String> getLoremChain() {
        return _loremChain;
    }

    // java-8. I don't think this will work work on versions before 8.
    public String getLoremChainStr() {
        return String.join(" ", _loremChain);
    }
    
    public void readVegasText(InputStream is) {
        if (is == null) return;

        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        if (in == null) return;
        
        List<String> lines = new ArrayList<>();
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null)
                lines.add(inputLine);
        } catch (IOException ex) { 
            // I don't want to bring down the entire application.
            // we can leave the _loremChain empty.
        } 
        try {
            in.close();
        } catch (IOException ex) {
            // I don't want to bring down the entire application.
            // we can leave the _loremChain empty.
        }

        for (String line : lines) {
            if (line.length() == 0) continue ;
            String[] data = line.split(":");
            int freq;
            try {
                freq = Integer.parseInt(data[1]);
            } catch (NumberFormatException ex) {
//                System.out.println("Malformatted line: " + line);
                continue;
            }
            if (freq < 1) continue;
            addWords(data[0], freq);
        }
    }
    
    public void readVegasText(String fname) {
        // see if the file exists. if not then bail out.
        // otherwise, create a stream and call the core procedure to read
        // in the data
        
        File fh = new File(fname); 
        if (!fh.exists()) {
            return;
        }
        
        try {
            InputStream is = new FileInputStream(fh);
            readVegasText(is);
        } catch (FileNotFoundException ex) {
            // paranoid ? yes, I know where are checking this twice.
            // I don't want to bring down the entire application.
            // we can leave the _loremChain empty.
        }
    }

    public void readLoremText(InputStream is) {
        // load the _loremChain variable with base lorem ipusm text that
        // is already included in the project build.
        // later, it will be up to the user to add in additional text items.
        
        if (is == null) return;

        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        if (in == null) return;
        
        List<String> lines = new ArrayList<>();
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null)
                lines.add(inputLine);
        } catch (IOException ex) {
            // I don't want to bring down the entire application.
            // we can leave the _loremChain empty.
        } 
        try {
            in.close();
        } catch (IOException ex) {
            // I don't want to bring down the entire application.
            // we can leave the _loremChain empty.
        }

        // I want to remove all punctuation. right now, I'm only concerned with
        // storing words. 
        // we'll deal with putting punctuation back in when the user 
        // uses the getParagraphs method.
        if (_loremChain.size() > 0) _loremChain.clear();
        String punctRegex = "[^\\s\\w]";
        for (String line : lines) {
            if (line.length() == 0) continue ;
            String reduced = line.toLowerCase().replaceAll(punctRegex, " ");

            StringTokenizer tokenizer = new StringTokenizer(reduced, " ");
            int numberOfWords = tokenizer.countTokens();
            if (numberOfWords == 0) continue ;

            while(tokenizer.hasMoreTokens()) {
                _loremChain.add(tokenizer.nextToken());
            }
        }        
    }
    
    public final void readLoremText(String fname) {
        // see if the file exists. if not then bail out.
        // otherwise, create a stream and call the core procedure to read
        // in the data
        File fh = new File(fname); 
        if (!fh.exists()) {
            return;
        }
        
        try {
            InputStream is = new FileInputStream(fh);
            readLoremText(is);
        } catch (FileNotFoundException ex) {
            // yes, we are overchecking here. this is a safe-guard if this
            // code ever gets cut-n-pasted somewhere else.
        }        
    }

    @Override
    public void shuffle() {
        if (_loremChain.isEmpty()) return;
        
        // we shuffle the _loremChain to get random stream of words we 
        // can use for fetching words or paragraphs.
        _curWordLocation = 0;
        
        // we maintain an index that points to the end of the word chain.
        // for each iteration we'll pick a random location from 0 to ii-1
        // inclusive and swap the two elements.
        for (int ii = _loremChain.size(); ii > 1; ii--) {
            int randWordLoc = RAND.nextInt(ii);
            if (randWordLoc == ii-1) continue;

            // swap words at location ii-1 and randWordLoc
            String tmp = _loremChain.get(randWordLoc);
            _loremChain.set(randWordLoc, _loremChain.get(ii-1));
            _loremChain.set(ii-1, tmp);
        }
    }

    private String capitalize(String s) {
        // capitalize the first char in the string. watch out, though.
        // you'll get a nasty exception of the string is one char long.
        // so, we check for that condition.
        if (s.length() > 1)
             return s.substring(0,1).toUpperCase() + s.substring(1);
        else if (s.length() == 1)
             return s.substring(0).toUpperCase();
        return s;
    }

    int getRandomCount(int min, int max) {
        if (min == max) return min;
        if (min > max) {
            // fixing this could incur bad-habits on the user side but
            // i'd really rather not throw an error
            int tmp = min;
            min = max;
            max = tmp;
        }        
        return RAND.nextInt(max-min+1) + min;
    }
    
    @Override
    public String getWords(int min, int max) {
        if (_loremChain.isEmpty()) return "";
        
        int numWords = getRandomCount(min, max);
        
        StringBuilder sentence = new StringBuilder();
        // de-complicate the iteration of words from loremChain by not
        // worrying about if we're fetching more words than are in the list.
        // we'll use modulo to 'wrap-around' to the beginning if we fetch
        // more words than are in the loremChar variable.
        for (int ii = 0; ii < numWords; ii++) {
            String w = _loremChain.get((_curWordLocation + ii) % _loremChain.size());
            sentence.append(w);
            sentence.append(" ");
            
        }
        // remember, we keep track of where we are fetching words in the
        // loremChain array. We need to advance this counter based on the
        // the number of words we fetched above.
        _curWordLocation += numWords;
        return sentence.toString().trim();
    }

    private char getSentenceTerminator() {
        // end 80% of the sentences with periods
        //     10% with question marks
        //     10% with exclamation marks
        
        int rnum = RAND.nextInt(10);
        if (rnum < 8)
            return '.';
        else if (rnum > 8)
            return '?';
        return '!';
    }
    
    private int getRandomParagraphSize() {
        // paragraph size == the number of sentences in a paragraph.
        
        // the code check below is a bit excessive. however,
        // we must ensure that max is always >= to min internally.
        if (_maxParagraphSize < _minParagraphSize) {
            int tmp = _maxParagraphSize;
            _maxParagraphSize = _minParagraphSize;
            _minParagraphSize = tmp;
        }
        return getRandomCount(_minParagraphSize, _maxParagraphSize);
    }
    
    @Override
    public List<String> getParagraphs(int min, int max) {
        if (_loremChain.isEmpty()) return null;
        
        // how many paragraphs?
        int numParagraphs = getRandomCount(min, max);

        List<String> res = new ArrayList<>();
        for (int ii = 0; ii < numParagraphs; ii++) {
            // how many sentences in this paragraph??
            int numSentences = getRandomParagraphSize();
            StringBuilder paragraph = new StringBuilder();
            for (int jj = 0; jj < numSentences; jj++) {
                StringBuilder sentence = new StringBuilder(getWords(_minSentenceSize, _maxSentenceSize));
                sentence.append(getSentenceTerminator());
                sentence.append(' ');
                paragraph.append(capitalize(sentence.toString()));
            }
            res.add(paragraph.toString());
        }
        // send back an array of sentences (strings).
        return res;
    }

    @Override
    public List<String> getParagraphs() {
        return getParagraphs(_DEFAULT_PARAGRAPH_CNT, _DEFAULT_PARAGRAPH_CNT);
    }
    
    @Override
    public void addWords(String phrase) {
        addWords(phrase, 1);
    }

    @Override
    public void addWords(String phrase, int count) {
        // remember, just as easily as we can add 'lorem' we can
        // also add 'bet big or go home' as a single "word". 
        // hence, the term 'phrase' for input variable.
        while (count-- > 0) {
            _loremChain.add(phrase);
        }
    }

    @Override
    public void setParagraphSizes(int min, int max) {
        if (min > max) {
            // fixing this could incur bad-habits on the user side but
            // i'd really rather not throw an error
            int tmp = min;
            min = max;
            max = tmp;
        }
        
        // should this be an all or none operation? I'm not sure if we should
        // either set both or set none.
        if (min > 0) 
            this._minParagraphSize = min;
        if (max > 0)
            this._maxParagraphSize = max;
    }
}
