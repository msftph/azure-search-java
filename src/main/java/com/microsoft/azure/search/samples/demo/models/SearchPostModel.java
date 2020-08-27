package com.microsoft.azure.search.samples.demo.models;

public class SearchPostModel {
    private String text;
    private String index;

    public String getText() {
        return text;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setText(String text) {
        this.text = text;
    }

}