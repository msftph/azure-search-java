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

    public IndexServiceClient(SearchClientContext context) {
        super(context);
    }

    public Index get(String name) throws IOException, InterruptedException {

        try (var formatter = new Formatter()) {
            String url = formatter.format("https://%s.search.windows.net/indexes/%s?api-version=%s",
                    context.getServiceName(), name, context.getApiVersion()).out().toString();
            var uri = URI.create(url);
            var apiRequest = createHttpRequest(uri, context.getApiKey(), "GET", null);
            var apiResponse = client.send(apiRequest, HttpResponse.BodyHandlers.ofString());

            var jsonObject = new JSONObject(apiResponse.body());
            return mapIndex(jsonObject);
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
                var index = mapIndex(child);
                indexes.add(index);
            }
            return indexes;
        }
    }

    private static Index mapIndex(JSONObject jsonObj) throws JSONException {
        var index = new Index();
        index.setName(jsonObj.getString("name"));
        index.setFields(mapFields(jsonObj));
        return index;
    }

    private static List<Field> mapFields(JSONObject jsonObj) {
        var array = jsonObj.getJSONArray("fields");
        var fields = new ArrayList<Field>();

        for (int i = 0; i < array.length(); i++) {
            var field = new Field();
            var child = array.getJSONObject(i);
            if(!child.isNull("name"))
                field.setName(child.getString("name"));
            if(!child.isNull("searchable"))
                field.setSearchable(child.getBoolean("searchable"));
            if(!child.isNull("sortable"))
                field.setSortable(child.getBoolean("sortable"));
            if(!child.isNull("type"))
                field.setType(child.getString("type"));
            if(!child.isNull("filterable"))
                field.setFilterable(child.getBoolean("filterable"));
            if (!child.isNull("fields")) {
                field.setFields(mapFields(child));
            }
            fields.add(field);
        }
        return fields;
    }
}