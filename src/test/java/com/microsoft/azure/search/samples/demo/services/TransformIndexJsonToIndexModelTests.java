package com.microsoft.azure.search.samples.demo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.microsoft.azure.search.samples.demo.DemoApplication;
import com.microsoft.azure.search.samples.demo.models.Field;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class)
public class TransformIndexJsonToIndexModelTests {

    @Autowired
    TransformIndexJsonToIndexModel transform;    

    @Test
    public void canMapResponse() throws JSONException, SecurityException, NullPointerException, IOException,
            OutOfMemoryError, URISyntaxException {

        final var resourceName = "index.json";
        var jsonText = new String(getClass().getClassLoader().getResourceAsStream(resourceName).readAllBytes());
        var json = new JSONObject(jsonText);
        
        var index = transform.transform(json);
        assertNotNull(index);

        var fields = index.getFields();
        assertNotNull(fields);
        assertEquals(14, fields.size());

        for(var i=0;i<fields.size();i++){
            var field = fields.get(i);
            assertNotNull(field);
            assertNotNull(field.getName());
        }
    }
}