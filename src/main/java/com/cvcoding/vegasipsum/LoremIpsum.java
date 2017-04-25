/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvcoding.vegasipsum;

import java.util.List;

/**
 *
 * @author johnr
 */
public interface LoremIpsum {
    
    public void shuffle();
    
    // return a bunch of words but formulated as a single string.
    public String getWords(int min, int max);
    
    // return a list paragraphs where each paragraph is a string.
    public List<String> getParagraphs(int min, int max);
    
    // the default version of LoremIpsum should load in the latin
    // words already. the functions below allow you to augment this
    // vocabulary with your own fun words.
    // If you want "hi-ho silver" to be in your lorem but to appear
    // more frequently than average set count to some number > 10.
    public void addWords(String phrase);
    public void addWords(String phrase, int count);

    // configure how many sentences per paragraph
    public void setParagraphSizes(int min, int max);
    
}
