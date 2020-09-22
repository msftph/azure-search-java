package com.microsoft.azure.search.samples.demo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;

import com.microsoft.azure.search.samples.demo.DemoApplication;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
public class TransformRealEstateSearchToModelTests {

    @Autowired
    TransformRealEstateSearchToModel transform;

    @Test
    public void canMapRequest() throws JSONException, SecurityException, NullPointerException, IOException,
            OutOfMemoryError, URISyntaxException {

        final var resourceName = "real-estate-search.json";
        var jsonText = new String(getClass().getClassLoader().getResourceAsStream(resourceName).readAllBytes());
        var json = new JSONObject(jsonText);
        var listingList = transform.transform(json).getItems();
        assertNotNull(listingList);
        assertEquals(6, listingList.size());

        for(var i=0;i<listingList.size();i++){
            var listing = listingList.get(i);
            assertNotEquals(0, listing.getPrice());
            assertNotNull(listing.getId());
            assertNotNull(listing.getAddress());
            var tags = listing.getTags();
            assertNotNull(tags);
            assertNotEquals(0, tags.size());
        }
    }
}
