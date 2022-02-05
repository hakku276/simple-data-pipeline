# README

This is a simple project that loads Categories and Documents from a Content Management system and stores them into a structured database. Main Application Entrypoint: [PipelineRunner](https://github.com/hakku276/simple-data-pipeline/blob/master/src/main/java/com/example/datapipe/PipelineRunner.java)

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
* Open REST Endpoints that return JSON data for Category and Document as described [here](https://developer.goacoustic.com/acoustic-content/reference/introduction#authoring-content)

## General Idea
* Preload the categories into the mysql database
* List all the documents available in the server
* Filter and Flatten the documents using [JSLT](https://github.com/schibsted/jslt)
  * Note: For time reasons the complete document was not flattened, but can be possibly flattened using jslt [example here](https://github.com/schibsted/jslt/blob/master/examples/flatten.jslt)
* Save the relationship between category and document in the database for visualization
