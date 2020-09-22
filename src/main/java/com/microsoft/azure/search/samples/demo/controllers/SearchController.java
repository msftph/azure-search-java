package com.microsoft.azure.search.samples.demo.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import com.microsoft.azure.search.samples.demo.global.Constants;
import com.microsoft.azure.search.samples.demo.models.SearchData;
import com.microsoft.azure.search.samples.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
public class SearchController {

    @Autowired
    private HotelSearchClient hotelSearchClient;

    @Autowired
    private RealEstateSearchClient realEstateSearchClient;

    @Autowired
    private IndexServiceClient indexServiceClient;

    @Autowired
    private GenericSearchClient genericSearchClient;

    @GetMapping("/search")
    public String search(Model model) throws IOException, InterruptedException {

        var indexes = indexServiceClient.list();
        model.addAttribute("indexes", indexes);
        return "search";
    }

    private static final String RESULTS_FIELD_NAME = "results";
    private static final String GENERIC_RESULTS_VIEW_NAME = "generic-search";

    @PostMapping(path = "/search", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public String search(SearchData data, Model model)
            throws IOException, InterruptedException, URISyntaxException {
        
        var index = data.getIndex();

        // view is the name of the thymeleaf view used to display the results
        // the view selected will be a view fragment
        var view = index;        

        
        if(index.equalsIgnoreCase(Constants.HOTEL_INDEX_NAME)){
            // if the index is the hotel sample index, use the hotel sample
            var results = hotelSearchClient.search(data.getText());
            model.addAttribute(RESULTS_FIELD_NAME, results);        
        }else if (index.equalsIgnoreCase(Constants.REAL_ESTATE_INDEX_NAME)){
            // if the index is the realestate sample index, use the real estate sample
            var results = realEstateSearchClient.search(data.getText());
            model.addAttribute(RESULTS_FIELD_NAME, results);
        }else{
            // otherwise use the generic search client
            var results = genericSearchClient.search(data.getIndex(), data.getText());
            model.addAttribute(RESULTS_FIELD_NAME, results);
            view = GENERIC_RESULTS_VIEW_NAME;
        }
            
        // this is a special thymeleaf selector to sub select the results th:fragment
        return String.format("%s :: results", view);
    }

    @GetMapping("/facets")
    public String facets(SearchData data){
        return "";
    }

    @GetMapping("/page")
    public String page(SearchData data){
        return "";
    }
}