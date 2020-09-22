package com.microsoft.azure.search.samples.demo.services;

import java.io.IOException;
import java.net.URISyntaxException;

import com.microsoft.azure.search.samples.demo.models.RealEstate;
import com.microsoft.azure.search.samples.demo.models.SearchResult;
import com.microsoft.azure.search.samples.demo.global.Constants;

import org.springframework.stereotype.Service;

@Service
public class RealEstateSearchClient extends SearchClient {

    private TransformRealEstateSearchToModel transformRealEstateJsonToModel;

    public RealEstateSearchClient(SearchClientContext context,
            TransformRealEstateSearchToModel transformRealEstateJsonToModel) {
        super(context);
        this.transformRealEstateJsonToModel = transformRealEstateJsonToModel;
    }

    public SearchResult<RealEstate> search(String text) throws IOException, InterruptedException, URISyntaxException {
        var jsonResponse = searchRaw(Constants.REAL_ESTATE_INDEX_NAME, text);        
        return transformRealEstateJsonToModel.transform(jsonResponse);
    }
}
