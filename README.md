# Spring Boot MVC Azure Search Demo

## Getting Started

This project was created with spring boot initilizer for vscode. [link](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-spring-initializr)

## Configuration

Set the Azure Search API Key from the "Keys" tab of azure search. It MUST be an admin key because the app needs to query the index structure.

```bash
export AZURE_SEARCH_API_KEY=000000000000000000000000
```

Set the azureSearch.serviceName and azureSearch.apiVersion properties in the src/main/application.properties file.

```
azureSearch.serviceName=walgreens-azure-search
azureSearch.apiVersion=2020-06-30
azureSearch.apiKey=${AZURE_SEARCH_API_KEY}
```

## Running

Build the application

```bash
mvn package
```

Run the application

```bash
./mvnw spring-boot:run
```