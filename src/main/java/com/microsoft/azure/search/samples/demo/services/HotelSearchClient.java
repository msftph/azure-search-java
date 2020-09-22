package com.microsoft.azure.search.samples.demo.services;

import java.io.IOException;
import com.microsoft.azure.search.samples.demo.global.Constants;
import com.microsoft.azure.search.samples.demo.models.Hotel;
import com.microsoft.azure.search.samples.demo.models.SearchResult;

import org.springframework.stereotype.Service;

@Service
public class HotelSearchClient extends SearchClient{

    private TransformHotelSearchToModel hotelJsonToModel;

    public HotelSearchClient(SearchClientContext context, TransformHotelSearchToModel hotelJsonToModel) {
        super(context);
        this.hotelJsonToModel = hotelJsonToModel;
    }

    public SearchResult<Hotel> search(String text)
        throws IOException, InterruptedException{
            var response = searchRaw(Constants.HOTEL_INDEX_NAME, text);
            return hotelJsonToModel.transform(response);
        
    }
}
