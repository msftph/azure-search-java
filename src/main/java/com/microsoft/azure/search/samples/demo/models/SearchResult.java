package com.microsoft.azure.search.samples.demo.models;

import java.util.List;

public class SearchResult {
    private List<SearchItem> items;
    private Pagination pagination;

    public List<SearchItem> getItems() {
        return items;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public void setItems(List<SearchItem> items) {
        this.items = items;
    }
}