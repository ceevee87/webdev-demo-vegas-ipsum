/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.rest.cvcoding;

import com.cvcoding.vegasipsum.VegasIpsum;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

// jersey bundle
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    
    // bogus hack --- this could easily fail if this code is deployed on
    // another machine. For now, though, my focus is getting the vegasipsum
    // generator going. I'll come back to this later and put in more generic
    // code for determining where to pick up supplemental text files shipped
    // and deployed with this application.
    private final String _baseLoremTxtFile = "/opt/tomcat/webapps/VegasIpsum/lorem.ipsum.seed.txt";
    private final String _vegasLoremTxtFile = "/opt/tomcat/webapps/VegasIpsum/vegas.ipsum.txt";
    
    public VegasIpsumResource() {
        this._vegasIpsumGenerator = new VegasIpsum();
        _vegasIpsumGenerator.readLoremText(_baseLoremTxtFile);            
        _vegasIpsumGenerator.readVegasText(_vegasLoremTxtFile);
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
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public StreamingOutput getSomeIpsum() {
        final List<String> P = _vegasIpsumGenerator.getParagraphs(4, 7);
        return streamOutStringsArr(P);
        
//        String w;
//        if (P == null) 
//            w = "Nothing in the generator.\n";
//        else {
//            StringBuilder res = new StringBuilder();
//            for (String paragraph : P) {
//                res.append(paragraph);
//                res.append('\n');
//            }
//            w = res.toString();
//        }
//
//        return w;
    }
}
