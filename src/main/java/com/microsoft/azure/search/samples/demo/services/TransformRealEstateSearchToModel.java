package com.microsoft.azure.search.samples.demo.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import com.microsoft.azure.search.samples.demo.models.Address;
import com.microsoft.azure.search.samples.demo.models.RealEstate;
import com.microsoft.azure.search.samples.demo.models.SearchResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TransformRealEstateSearchToModel {
    public SearchResult<RealEstate> transform(JSONObject jsonObject) throws URISyntaxException {
        return mapToRealEstateList(jsonObject);
    }

    private SearchResult<RealEstate> mapToRealEstateList(JSONObject jsonObject) throws URISyntaxException {
        var realEstateList = new ArrayList<RealEstate>();
        var realEstateJsonArray = jsonObject.getJSONArray("value");
        for (var i = 0; i < realEstateJsonArray.length(); i++) {
            var realEstateListingJson = realEstateJsonArray.getJSONObject(i);
            var realEstateListing = mapRealEstateListing(realEstateListingJson);
            realEstateList.add(realEstateListing);
        }
        return new SearchResult<RealEstate>(realEstateList);
    }

    private RealEstate mapRealEstateListing(JSONObject realEstateListingJson) throws URISyntaxException {
        var realEstateListing = new RealEstate();
        realEstateListing.setId(realEstateListingJson.getString("listingId"));
        realEstateListing.setBaths(realEstateListingJson.getInt("baths"));
        realEstateListing.setBeds(realEstateListingJson.getInt("beds"));
        realEstateListing.setDescription(realEstateListingJson.getString("description"));
        realEstateListing.setPrice(realEstateListingJson.getInt("price"));
        realEstateListing.setThumbnail(new URI(realEstateListingJson.getString("thumbnail")));
        realEstateListing.setAddress(mapAddress(realEstateListingJson));
        realEstateListing.setSquareFeet(realEstateListingJson.getInt("sqft"));  
        realEstateListing.setTags(mapTags(realEstateListingJson.getJSONArray("tags")));
        return realEstateListing;
    }

    private Address mapAddress(JSONObject realEstateListingJson) {
        var address = new Address();
        address.setStreet(realEstateListingJson.getString("street"));
        address.setCity(realEstateListingJson.getString("city"));
        address.setStateProvince(realEstateListingJson.getString("region"));
        address.setPostalCode(realEstateListingJson.getString("postCode"));
        address.setCountry(realEstateListingJson.getString("countryCode"));
        return address;
    }

    private List<String> mapTags(JSONArray tagsJson) {
        var tags = new ArrayList<String>();
        for(var i=0;i<tagsJson.length();i++){
            var tag = tagsJson.getString(i);
            tags.add(tag);
        }
        return tags;
    }
}
