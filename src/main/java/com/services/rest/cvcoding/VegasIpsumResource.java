/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.rest.cvcoding;

import com.cvcoding.vegasipsum.VegasIpsum;

// jackson databind
import com.fasterxml.jackson.databind.ObjectMapper;

// regular java stuff
import java.io.IOException;
import java.io.OutputStream;
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
    @Produces(MediaType.APPLICATION_JSON)
    public StreamingOutput getSomeIpsum(@DefaultValue("1") @QueryParam("minp") int minp,
                               @DefaultValue("1") @QueryParam("maxp") int maxp,
                               @DefaultValue("false") @QueryParam("start") boolean start) {
        
        final List<String> P = _vegasIpsumGenerator.getParagraphs(minp, maxp);
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
