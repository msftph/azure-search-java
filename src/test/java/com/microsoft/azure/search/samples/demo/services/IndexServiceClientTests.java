package com.microsoft.azure.search.samples.demo.services;

import static org.junit.jupiter.api.Assertions.*;

import com.microsoft.azure.search.samples.demo.DemoApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
class IndexServiceClientTests {

	@Autowired
	private IndexServiceClient client;

    @Test
	void canGetIndexes() throws Exception{
		// TODO: hook up mock api
		assertNotNull(client);
		var indexes = client.list();
		assertNotNull(indexes);
		assertEquals(2, indexes.size());
	}
}