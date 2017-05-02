/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cvcoding.vegasipsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author johnr
 */
public class VegasIpsumTest {
    
    private String _basicLoremFileName;
    private String _stdLoremFileName;
    private String _punctuationLoremFileName;
    private String _hugeLoremFileName;
    
    public VegasIpsumTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this._basicLoremFileName = "src/test/resources/lorem.ipsum.basic";
        this._punctuationLoremFileName = "src/test/resources/lorem.ipsum.punct";
        this._hugeLoremFileName = "src/test/resources/lorem.ipsum.huge";
        this._stdLoremFileName = "src/test/resources/lorem.ipsum.std";
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getLoremChain method, of class VegasIpsum.
     */
    @Test
    public void testGetLoremChain_1() {
        System.out.println("getLoremChain array size");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        List<String> result = instance.getLoremChain();
        assertEquals(result.size(), 1786);
    }
    @Test
    public void testGetLoremChain_2() {
        System.out.println("getLoremChain non null");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        List<String> result = instance.getLoremChain();
        assertNotEquals(result, null);
    }
    @Test
    public void testGetLoremChain_3() {
        System.out.println("getLoremChain check 1st word");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        List<String> result = instance.getLoremChain();
        assertEquals(result.get(0), "lorem");
    }
    
    @Test
    public void testGetLoremChain_4() {
        System.out.println("getLoremChain compare arraylists");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_basicLoremFileName);

        List<String> expResult = new ArrayList<>();
        expResult.add("lorem");
        expResult.add("ipsum");
        expResult.add("dolor");
        expResult.add("sit");
        expResult.add("amet");
        expResult.add("consectetur");
        expResult.add("adipiscing");
        expResult.add("elit");

        List<String> result = instance.getLoremChain();
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test of getLoremChainStr method, of class VegasIpsum.
     */
    @Test
    public void testGetLoremChainStr_1() {
        System.out.println("getLoremChainStr check string length.");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        String result = instance.getLoremChainStr();
        assertEquals(11857, result.length());
    }

    @Test
    public void testGetLoremChainStr_2() {
        System.out.println("getLoremChainStr string not null.");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        String result = instance.getLoremChainStr();
        assertNotEquals(null, result);
    }

    @Test
    public void testGetLoremChainStr_3() {
        System.out.println("getLoremChainStr (punct) string not null.");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_punctuationLoremFileName);
        String result = instance.getLoremChainStr();
        assertNotEquals(null, result);
    }
    
    @Test
    public void testGetLoremChainStr_4() {
        System.out.println("getLoremChainStr (punct) string doens't contain punctuation.");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_punctuationLoremFileName);
        String result = instance.getLoremChainStr();
        String expResult = "lorem ipsum dolor sit amet consectetur adipiscing elit cras fermentum venenatis luctus maecenas tempus nulla sit amet eros porttitor convallis suspendisse fermentum non tortor at bibendum";
        assertEquals(expResult, result);
    }
    
//    /**
//     * Test of readVegasText method, of class VegasIpsum.
//     */
//    @Test
//    public void testReadVegasText() {
//        System.out.println("readVegasText");
//        String fname = "";
//        VegasIpsum instance = new VegasIpsum();
//        instance.readVegasText(fname);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of shuffle method, of class VegasIpsum.
//     */
//    @Test
//    public void testShuffle() {
//        System.out.println("shuffle");
//        VegasIpsum instance = new VegasIpsum();
//        instance.shuffle();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    /**
     * Test of getWords method, of class VegasIpsum.
     */
    @Test
    public void testGetWords_1() {
        System.out.println("getWords (small word count) not null string.");
        int min = 1;
        int max = 8;
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_basicLoremFileName);
        instance.setSeed(0L);
        String result = instance.getWords(min, max);
        assertNotEquals(null, result);
    }
    @Test
    public void testGetWords_2() {
        System.out.println("getWords (med word count) not null string.");
        int min = 55;
        int max = 110;
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        instance.setSeed(0L);
        String result = instance.getWords(min, max);
        assertNotEquals(null, result);
    }
    @Test
    public void testGetWords_3() {
        System.out.println("getWords (large word count) not null string.");
        int min = 500;
        int max = 1590;
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        instance.setSeed(0L);
        String result = instance.getWords(min, max);
        assertNotEquals(null, result);
    }
    @Test
    public void testGetWords_4() {
        System.out.println("getWords (oversize word count) not null string.");
        // the min and max are both larger than the lorem word count.
        // this should not fail because we should "wrap-around" when fetching
        // words from the loremChain.
        int min = 2000;
        int max = 5000;
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        instance.setSeed(0L);
        String result = instance.getWords(min, max);
        assertNotEquals(null, result);
    }

    @Test
    public void testGetWords_5() {
        System.out.println("getWords (min==max==1 word count)");
        // the min and max are both larger than the lorem word count.
        // this should not fail because we should "wrap-around" when fetching
        // words from the loremChain.
        int min = 1;
        int max = 1;
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_basicLoremFileName);
        instance.setSeed(0L);
        String result = instance.getWords(min, max);
        assertEquals("lorem", result);
    }

    @Test
    public void testGetWords_6() {
        System.out.println("getWords (get all words)");
        // the min and max are both larger than the lorem word count.
        // this should not fail because we should "wrap-around" when fetching
        // words from the loremChain.
        int min = 8;
        int max = 8;
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_basicLoremFileName);
        instance.setSeed(0L);
        String result = instance.getWords(min, max);
        String expResult = "lorem ipsum dolor sit amet consectetur adipiscing elit";
//        System.out.println("expect: " + expResult);
//        System.out.println("actual: " + result);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetWords_7() {
        System.out.println("getWords (from entire range of all words)");
        // the min and max are both larger than the lorem word count.
        // this should not fail because we should "wrap-around" when fetching
        // words from the loremChain.
        int min = 1;
        int max = 8;
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_basicLoremFileName);
        instance.setSeed(0L);
        String result = instance.getWords(min, max);
        String expResult = "lorem ipsum dolor sit amet consectetur";
        System.out.println("expect: " + expResult);
        System.out.println("actual: " + result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getParagraphs method, of class VegasIpsum.
     */
    @Test
    public void testGetParagraphs_1() {
        System.out.println("getParagraphs---check locations of periods.");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        instance.setSeed(0L);

        int min = 7;
        int max = 17;
        List<String> paragraphs = instance.getParagraphs(min, max);
        assertEquals(7, paragraphs.size());
        
        // check the location of the first period in each paragraph.
        int[] expResult = {38,42,32,37,50,30,150};
        int[] result = new int[7];
        
        for (int ii = 0; ii < paragraphs.size(); ii++) {
            result[ii] = paragraphs.get(ii).indexOf(".");
        }
        Assert.assertArrayEquals(expResult, result);
    }
    @Test
    public void testGetParagraphs_2() {
        System.out.println("getParagraphs--check paragraph lengths");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        instance.setSeed(0L);

        int min = 4;
        int max = 7;
        List<String> paragraphs = instance.getParagraphs(min, max);
        assertEquals(6, paragraphs.size());
        
        // check the length of each paragraph.
        int[] expResult = {394,321,178,248,318,127};
        int[] result = new int[6];
        
        for (int ii = 0; ii < paragraphs.size(); ii++) {
            result[ii] = paragraphs.get(ii).length();
        }
        Assert.assertArrayEquals(expResult, result);
    }
    @Test
    public void testGetParagraphs_3() {
        System.out.println("getParagraphs--check default paragraph counts.");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        instance.setSeed(0L);

        int defaultParagraphCnt = 3;
        List<String> paragraphs = instance.getParagraphs();
        assertEquals(defaultParagraphCnt, paragraphs.size());
        
        // check the length of each paragraph.
        int[] expResult = {324, 269, 240};
        int[] result = new int[defaultParagraphCnt];
        
        for (int ii = 0; ii < paragraphs.size(); ii++) {
            result[ii] = paragraphs.get(ii).length();
        }
        Assert.assertArrayEquals(expResult, result);
    }
    @Test
    public void testGetParagraphs_4() {
        System.out.println("getParagraphs--check paragraph counts when max < min");
        VegasIpsum instance = new VegasIpsum();
        instance.readLoremText(_stdLoremFileName);
        instance.setSeed(0L);

        int min = 10;
        int max = 4;
        List<String> paragraphs = instance.getParagraphs(min, max);
        assertEquals(9, paragraphs.size());
        
        // check the length of each paragraph.
        int[] expResult = {394, 321, 178, 248, 318, 127, 152, 130, 312};
        int[] result = new int[9];
        
        for (int ii = 0; ii < paragraphs.size(); ii++) {
            result[ii] = paragraphs.get(ii).length();
        }
        System.out.println("New period locations="+Arrays.toString(result));
        Assert.assertArrayEquals(expResult, result);
    }

//    /**
//     * Test of addWords method, of class VegasIpsum.
//     */
//    @Test
//    public void testAddWords_String() {
//        System.out.println("addWords");
//        String phrase = "";
//        VegasIpsum instance = new VegasIpsum();
//        instance.addWords(phrase);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addWords method, of class VegasIpsum.
//     */
//    @Test
//    public void testAddWords_String_int() {
//        System.out.println("addWords");
//        String phrase = "";
//        int count = 0;
//        VegasIpsum instance = new VegasIpsum();
//        instance.addWords(phrase, count);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setParagraphSizes method, of class VegasIpsum.
//     */
//    @Test
//    public void testSetParagraphSizes() {
//        System.out.println("setParagraphSizes");
//        int min = 0;
//        int max = 0;
//        VegasIpsum instance = new VegasIpsum();
//        instance.setParagraphSizes(min, max);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
