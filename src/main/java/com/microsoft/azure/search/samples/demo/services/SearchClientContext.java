package com.microsoft.azure.search.samples.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SearchClientContext {

    @Value( "${azureSearch.serviceName}" )
    private String serviceName;

    @Value( "${azureSearch.apiVersion}" )
    private String apiVersion;

    @Value("${azureSearch.apiKey}")
    private String apiKey;
    

    public String getServiceName() {
        return serviceName;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}