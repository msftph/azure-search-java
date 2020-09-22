package com.microsoft.azure.search.samples.demo.services;

import java.util.*;

import com.microsoft.azure.search.samples.demo.models.*;

import org.json.*;
import org.springframework.stereotype.Service;

@Service
public class TransformIndexJsonToIndexModel {
    public Index transform(JSONObject response){
        return mapIndex(response);
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
