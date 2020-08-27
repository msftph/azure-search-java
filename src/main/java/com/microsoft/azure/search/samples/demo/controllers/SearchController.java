package com.microsoft.azure.search.samples.demo.controllers;

import java.io.IOException;

import com.microsoft.azure.search.samples.demo.models.SearchPostModel;
import com.microsoft.azure.search.samples.demo.models.SearchResult;
import com.microsoft.azure.search.samples.demo.services.IndexServiceClient;
import com.microsoft.azure.search.samples.demo.services.SearchServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
public class SearchController {

    @Autowired
    private IndexServiceClient indexServiceClient;
    
    @Autowired
    private SearchServiceClient searchServiceClient;

    @GetMapping("/search")
    public String search(Model model) 
        throws IOException, InterruptedException{
        
        var indexes = indexServiceClient.list();
        model.addAttribute("indexes",indexes);
        return "search";
    }

    @PostMapping(
        path="/search",
        consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody SearchResult  search( SearchPostModel data)
    throws IOException, InterruptedException{ 
        return searchServiceClient.search(data.getIndex(), data.getText());
    }
}