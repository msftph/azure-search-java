package com.microsoft.azure.search.samples.demo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import com.microsoft.azure.search.samples.demo.DemoApplication;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
public class TransformHotelSearchToModelTests {
        
    @Autowired
    TransformHotelSearchToModel transform;
    
    @Test
    public void canMapRequest() throws JSONException,SecurityException,NullPointerException,IOException, OutOfMemoryError{
        final var resourceName = "hotel-search.json";
        var jsonText = new String(getClass().getClassLoader().getResourceAsStream(resourceName).readAllBytes());
        var json = new JSONObject(jsonText);
        var hotelList = transform.transform(json).getItems();
        assertNotNull(hotelList);
        assertEquals(3, hotelList.size());

        for(var i=0;i<hotelList.size();i++){
            var hotel = hotelList.get(i);
            assertNotNull(hotel.getName());
            assertNotNull(hotel.getId());
            assertNotNull(hotel.getAddress());
        }
    }
}
