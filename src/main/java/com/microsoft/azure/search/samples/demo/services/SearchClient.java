package com.microsoft.azure.search.samples.demo.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class SearchClient {
    protected SearchClientContext context;

    protected final static HttpClient client = HttpClient.newHttpClient();

    public SearchClient(SearchClientContext context) {
        if(context == null)
            throw new IllegalArgumentException("context is null");
        this.context = context;
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