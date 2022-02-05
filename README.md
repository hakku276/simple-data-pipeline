# README

This is a simple project that loads Categories and Documents from a Content Management system and stores them into a structured database.

## Configurable Parameters
The following parameters can be configured depending upon the system / environment:
```
APP_DATA-SOURCE_CATEGORY-URL=<URL to retrieve the categories>
APP_DATA-SOURCE_DOCUMENT-URL=<URL to retrieve the documents>
SPRING_DATASOURCE_URL=jdbc://mysql://localhost:3306/db
SPRING_DATASOURCE_PASSWORD=password
SPRING_DATASOURCE_USERNAME=username
```

## Requirements
* MySQL Server
* Open REST Endpoints that return JSON data for Category and Document as described [https://developer.goacoustic.com/acoustic-content/reference/introduction#authoring-content](here)

## General Idea
* Preload the categories into the mysql database
* List all the documents available in the server
* Filter and Flatten the documents using [https://github.com/schibsted/jslt](JSLT)
  * Note: For time reasons the complete document was not flattened, but can be possibly flattened using jslt [https://github.com/schibsted/jslt/blob/master/examples/flatten.jslt](example here)
* Save the relationship between category and document in the database for visualization