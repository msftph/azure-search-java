package com.microsoft.azure.search.samples.demo.services;

import java.util.*;

import com.microsoft.azure.search.samples.demo.models.Address;
import com.microsoft.azure.search.samples.demo.models.Hotel;
import com.microsoft.azure.search.samples.demo.models.SearchResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TransformHotelSearchToModel {

    public SearchResult<Hotel> transform(JSONObject hotelsJson) {
        return mapHotelList(hotelsJson);
    }

    private SearchResult<Hotel> mapHotelList(JSONObject response) {
        var hotelsJsonArray = response.getJSONArray("value");
        var hotels = new ArrayList<Hotel>();
        for (var i = 0; i < hotelsJsonArray.length(); i++) {
            var hotelJson = hotelsJsonArray.getJSONObject(i);
            var hotel = mapHotel(hotelJson);
            hotels.add(hotel);
        }
        var searchResult = new SearchResult<Hotel>();
        searchResult.setItems(hotels);
        return searchResult;
    }

    private Hotel mapHotel(JSONObject hotelJson) {
        var hotel = new Hotel();
        hotel.setName(hotelJson.getString("HotelName"));
        hotel.setId(hotelJson.getString("HotelId"));
        hotel.setDescription(hotelJson.getString("Description"));
        hotel.setTags(mapTags(hotelJson.getJSONArray("Tags")));
        hotel.setAddress(mapAddress(hotelJson.getJSONObject("Address")));
        return hotel;
    }

    private Address mapAddress(JSONObject jsonObject) {
        var address = new Address();
        address.setStreet(jsonObject.getString("StreetAddress"));
        address.setCity(jsonObject.getString("City"));
        address.setStateProvince(jsonObject.getString("StateProvince"));
        address.setCountry(jsonObject.getString("Country"));
        return address;
    }

    private List<String> mapTags(JSONArray tagsJson) {
        var tags = new ArrayList<String>();
        for(var i=0;i<tagsJson.length();i++){
            tags.add(tagsJson.getString(i));
        }
        return tags;
    }
}
