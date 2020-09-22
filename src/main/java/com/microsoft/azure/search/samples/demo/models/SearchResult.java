package com.microsoft.azure.search.samples.demo.models;

import java.util.List;

public class SearchResult<T> {
    private List<T> items;
    private Pagination pagination;

    public SearchResult(){}
    
    public SearchResult(List<T> items){
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}