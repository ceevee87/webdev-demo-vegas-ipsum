/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.rest.cvcoding;

import com.cvcoding.vegasipsum.VegasIpsum;

// jackson databind
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;

// regular java stuff
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

// jersey bundle
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author johnr
 */
@Path("/")
public class VegasIpsumResource {
    private final VegasIpsum _vegasIpsumGenerator;
    
    // this stuff is all relative to the .class file in the WAR because
    // I am using the class.getResourceAsStream method.
    private final String _baseLoremTxtFile = "../../../lorem.ipsum.seed.txt";
    private final String _vegasLoremTxtFile = "../../../vegas.ipsum.txt";
    private InputStream _is;
    
    public VegasIpsumResource() {
        this._vegasIpsumGenerator = new VegasIpsum();
        
        _is = VegasIpsum.class.getResourceAsStream(_baseLoremTxtFile);
        _vegasIpsumGenerator.readLoremText(_is);

        _is = VegasIpsum.class.getResourceAsStream(_vegasLoremTxtFile);
        _vegasIpsumGenerator.readVegasText(_is);

        _vegasIpsumGenerator.setParagraphSizes(6, 10); 
        _vegasIpsumGenerator.shuffle();
        _vegasIpsumGenerator.shuffle();
    }

    private StreamingOutput streamOutStringsArr(final List<String> paragraphs) {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException, 
                        WebApplicationException {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(os, paragraphs); 
            }
        };        
    }
    
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public StreamingOutput getSomeIpsum(@DefaultValue("1") @QueryParam("minp") int minp,
                               @DefaultValue("1") @QueryParam("maxp") int maxp,
                               @DefaultValue("false") @QueryParam("start") boolean start) {
        
        final List<String> P = _vegasIpsumGenerator.getParagraphs(minp, maxp);
        if (P == null) 
            return streamOutStringsArr(P);

        // this is a total low-tech solution here to starting the lorem ipsum
        // text as shown.
        // what items did I skip over by doing this cheap solution?
        //   - what if the first several characters were already 'vegas ipsum...'?
        //   - why should this functionality be in the GET resource? shouldn't
        //     this be a switch we send to get paragraphs?
        //   - why stop at offering the starting text to be 'vegas ipsum ...'?
        //     shouldn't we be able to configure (pick a custom text) how
        //     to start the generated text?
        
        if (start) {
            StringBuilder newParagraph = new StringBuilder("Vegas ipsum dolor sit amet, ");
            newParagraph.append(P.remove(0).toLowerCase());
            P.add(0, newParagraph.toString());
        }
        return streamOutStringsArr(P);
    }
}
