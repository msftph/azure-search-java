package com.microsoft.azure.search.samples.demo.models;
import java.util.List;

public class Index {
    private String name;
    private List<Field> fields;

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<Field> getFields(){
        return this.fields;
    }

    public void setFields(List<Field> fields){
        this.fields = fields;
    }
}