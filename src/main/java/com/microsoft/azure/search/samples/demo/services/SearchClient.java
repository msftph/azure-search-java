package com.microsoft.azure.search.samples.demo.services;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.util.Formatter;

import org.json.JSONObject;

public class SearchClient {
    protected SearchClientContext context;

    protected final static HttpClient client = HttpClient.newHttpClient();

    public SearchClient(SearchClientContext context) {
        if(context == null)
            throw new IllegalArgumentException("context is null");
        this.context = context;
    }

    protected JSONObject searchRaw(String indexName, String text) 
    throws IOException, InterruptedException{
    try (var formatter = new Formatter()) {
        String url = formatter.format("https://%s.search.windows.net/indexes/%s/docs?api-version=%s&search=%s",
                context.getServiceName(), 
                indexName,                     
                context.getApiVersion(),
                text)
            .out()
            .toString();
        
        // create the api request    
        var uri = URI.create(url);
        var apiRequest = createHttpRequest(uri, context.getApiKey(), "GET", null);

        // extract the api response
        var apiResponse = client.send(apiRequest, HttpResponse.BodyHandlers.ofString());

        if(apiResponse.statusCode() != HttpURLConnection.HTTP_OK)
            throw new IOException(
                String.format("Expected http response 200, found %s", apiResponse.statusCode()));

        return new JSONObject(apiResponse.body());
    }
}

    protected HttpRequest createHttpRequest(URI uri, String key, String method, String contents) {
        contents = contents == null ? "" : contents;
        var builder = HttpRequest.newBuilder();
        builder.uri(uri);
        builder.setHeader("content-type", "application/json");
        builder.setHeader("api-key", key);

        switch (method) {
            case "GET":
                builder = builder.GET();
                break;
            case "HEAD":
                builder = builder.GET();
                break;
            case "DELETE":
                builder = builder.DELETE();
                break;
            case "PUT":
                builder = builder.PUT(HttpRequest.BodyPublishers.ofString(contents));
                break;
            case "POST":
                builder = builder.POST(HttpRequest.BodyPublishers.ofString(contents));
                break;
            default:
                throw new IllegalArgumentException(String.format("Can't create request for method '%s'", method));
        }
        return builder.build();
    }
}