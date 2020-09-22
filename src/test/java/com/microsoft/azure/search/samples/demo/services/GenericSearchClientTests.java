package com.microsoft.azure.search.samples.demo.services;

import static org.junit.jupiter.api.Assertions.*;

import com.microsoft.azure.search.samples.demo.DemoApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
public class GenericSearchClientTests {

    @Autowired
    GenericSearchClient client;

    @Test
	void canGetResults() throws Exception{
		// TODO: hook up mock api
        assertNotNull(client);
        
		var result = client.search("hotels-sample-index", "Seattle");
        assertNotNull(result);        
        assertNotNull(result.getItems());
	}
}