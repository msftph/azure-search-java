package com.microsoft.azure.search.samples.demo.services;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.util.*;

import com.microsoft.azure.search.samples.demo.models.*;

import org.json.*;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceClient extends SearchClient {

    private TransformIndexJsonToIndexModel transformIndexJsonToIndexModel;
    
    public IndexServiceClient(SearchClientContext context, TransformIndexJsonToIndexModel transformIndexJsonToIndexModel) {
        super(context);
        this.transformIndexJsonToIndexModel = transformIndexJsonToIndexModel;
    }
    
    public Index get(String name) throws IOException, InterruptedException {

        try (var formatter = new Formatter()) {
            String url = formatter.format("https://%s.search.windows.net/indexes/%s?api-version=%s",
                    context.getServiceName(), name, context.getApiVersion()).out().toString();
            var uri = URI.create(url);
            var apiRequest = createHttpRequest(uri, context.getApiKey(), "GET", null);
            var apiResponse = client.send(apiRequest, HttpResponse.BodyHandlers.ofString());

            var jsonObject = new JSONObject(apiResponse.body());
            return transformIndexJsonToIndexModel.transform(jsonObject);
        }
    }

    public List<Index> list() throws IOException, InterruptedException {

        try (var formatter = new Formatter()) {
            String url = formatter.format("https://%s.search.windows.net/indexes?api-version=%s",
                    context.getServiceName(), context.getApiVersion()).out().toString();
            var uri = URI.create(url);
            var apiRequest = createHttpRequest(uri, context.getApiKey(), "GET", null);
            var apiResponse = client.send(apiRequest, HttpResponse.BodyHandlers.ofString());

            var jsonObject = new JSONObject(apiResponse.body());
            var value = jsonObject.getJSONArray("value");

            var indexes = new ArrayList<Index>();
            for (var i = 0; i < value.length(); i++) {
                var child = value.getJSONObject(i);
                var index = transformIndexJsonToIndexModel.transform(child);
                indexes.add(index);
            }
            return indexes;
        }
    }
}