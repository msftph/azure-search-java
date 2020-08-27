package com.microsoft.azure.search.samples.demo.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.*;
import com.microsoft.azure.search.samples.demo.models.*;
import org.json.*;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceClient extends SearchClient {
    
    private IndexServiceClient indexServiceClient;

    public SearchServiceClient(SearchClientContext context, IndexServiceClient indexServiceClient) {
        super(context);   
        if(indexServiceClient ==  null)
            throw new IllegalArgumentException("indexServiceClient is null");     
        this.indexServiceClient = indexServiceClient;        
    }

    public SearchResult search(String indexName, String text) throws IOException, InterruptedException{        
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

            var jsonObject = new JSONObject(apiResponse.body());
            var values = jsonObject.getJSONArray("value");

            // get the fields for the index
            var index = indexServiceClient.get(indexName);

            // map the fields and values into a search item list
            var items = mapSearchItemList(index.getFields(), values);
            
            var result = new SearchResult();
            result.setItems(items);
            return result;
        }
    }

    private static List<SearchItem> mapSearchItemList(List<Field> fields, JSONArray array){
        var list = new ArrayList<SearchItem>();        
        for (var i=0;i<array.length();i++) {
            var value = array.getJSONObject(i);            
            var searchItem = mapSearchItem(fields, value);
            list.add(searchItem);
        }
        return list;
    }
    
    private static SearchItem mapSearchItem(List<Field> fields, JSONObject object){
        
        var items = new ArrayList<SearchItem>();
        for(var i=0;i<fields.size();i++){
            var field = fields.get(i);
            if(object.isNull(field.getName()))
                continue;
            var searchItem = mapSearchItem(field, object);
            items.add(searchItem);
        }
        var parentSearchItem = new SearchItem();
        parentSearchItem.setItems(items);
        return parentSearchItem;
    }

    private static SearchItem mapSearchItem(Field field, JSONObject object){        
        var searchItem = new SearchItem();
        searchItem.setName(field.getName());
        searchItem.setType(field.getType());
        
        var fieldType = field.getType();
        
        // this is a collection of complex types
        if(fieldType.equals("Collection(Edm.ComplexType)")){
            var items = mapSearchItemList(field.getFields(), object.getJSONArray(field.getName()));
            searchItem.setItems(items);
            return searchItem;
        }

        // this is a collection of primative types
        if(fieldType.startsWith("Collection(")){
            var array = object.getJSONArray(field.getName());
            var items = new ArrayList<SearchItem>();
            for(var i=0;i<array.length();i++){
                var childItem = new SearchItem();
                var value = array.get(i).toString();
                childItem.setValue(value);
                items.add(childItem);
            }
            searchItem.setItems(items);
            return searchItem;
        }        

        if(fieldType.equals("Edm.ComplexType")){
            var complexObject = object.getJSONObject(field.getName());
            var childItem = mapSearchItem(field.getFields(), complexObject);            
            
            childItem.setName(field.getName());
            childItem.setType(field.getType());
            return childItem;
        }

        if(fieldType.equals("Edm.GeographyPoint")){
            var complexObject = object.getJSONObject(field.getName());
            
            var crs = new Field();
            crs.setName("crs");
            crs.setType("Edm.ComplexType");

            var coordinates = new Field();
            coordinates.setName("coordinates");
            coordinates.setType("Collection(Edm.Double)");

            var type = new Field();
            type.setName("type");
            type.setType("Edm.String");

            var geoSearchItem = mapSearchItem(Arrays.asList(type, coordinates), complexObject);
            geoSearchItem.setName(field.getName());
            geoSearchItem.setType(field.getType());

            return geoSearchItem;
        }
        searchItem.setValue(object.get(field.getName()).toString()); 
        return searchItem;
    }

}