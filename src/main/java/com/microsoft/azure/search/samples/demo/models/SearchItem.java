package com.microsoft.azure.search.samples.demo.models;

import java.util.List;

public class SearchItem {
    private List<SearchItem> items;

    private String name;
    private String type;
    private String value;
    private Double score;

    public List<SearchItem> getItems() {
        return items;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<SearchItem> items) {
        this.items = items;
    }
}