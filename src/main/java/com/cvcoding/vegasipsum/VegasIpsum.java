/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvcoding.vegasipsum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
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
    
    // if people want to get a sentence at a time one option is to set min
    // and max to 1.
    private int _minParagraphSize;
    
    // if people want huge paragraphs (large number of sentences) then this 
    // is what they'll want to change.
    private int _maxParagraphSize;
    
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
    private ArrayList<String> _loremChain;
    
    // as we fetch words or paragraphs we simply iterate through entire
    // loremChain array, wrapping around if necessary.
    private int _curWordLocation;
    
    // not sure if we'll use this or not.
    private Iterator<String> _wordItr;

    public VegasIpsum() {
        // standard paragraphs are 3-6 sentences long.
        this._minParagraphSize = 3;
        this._maxParagraphSize = 6;
        
        this._loremChain = new ArrayList<>();
        this._curWordLocation = 0;
        this._wordItr = null;
        
        // load up the base lorem ipsum text
        readBaseLoremIpsumText();
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
    
    public void readVegasText(String fname) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fname));
        } catch (IOException ex) {
            Logger.getLogger(Solution.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        
        for (String line : lines) {
            if (line.length() == 0) continue ;
            String[] data = line.split(":");
            int freq;
            try {
                freq = Integer.parseInt(data[1]);
            } catch (NumberFormatException ex) {
                System.out.println("Malformatted line: " + line);
                continue;
            }
            if (freq < 1) continue;
            addWords(data[0], freq);
//            System.out.print("phrase: " + data[0]);
//            System.out.println(" , frequency: " + freq);
        }
    }
    
    private void readBaseLoremIpsumText() {
        // load the _loremChain variable with base lorem ipusm text that
        // is already included in the project build.
        // later, it will be up to the user to add in additional text items.
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(LOREM_FILE_NAME));
        } catch (IOException ex) {
            Logger.getLogger(Solution.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        
        // I want to remove all punctuation. right now, I'm only concerned with
        // storing words. 
        // we'll deal with putting punctuation back in when the user 
        // uses the getParagraphs method.
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

    @Override
    public void shuffle() {
        // we shuffle the _loremChain to get random stream of words we 
        // can use for fetching words or paragraphs.
        
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

    @Override
    public String getWords(int min, int max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getParagraphs(int min, int max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addWords(String phrase) {
        addWords(phrase, 1);
    }

    @Override
    public void addWords(String phrase, int count) {
        while (count-- > 0) {
            _loremChain.add(phrase);
        }
    }

    @Override
    public void addWords(List<String> phrases) {
        addWords(phrases, 1);
    }

    @Override
    public void addWords(List<String> phrases, int count) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
