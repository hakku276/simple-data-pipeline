package com.example.datapipe.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.example.datapipe.AppConfig;
import com.example.datapipe.model.Category;
import com.example.datapipe.model.Document;
import com.example.datapipe.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schibsted.spt.data.jslt.Expression;
import com.schibsted.spt.data.jslt.Parser;

import lombok.extern.slf4j.Slf4j;

/**
 * And implementation of the {@linkplain IContentService} that loads data from
 * the Acoustic CMS systems.
 *
 * @author aanal
 *
 */
@Slf4j
@Service
public class AcousticContentService implements IContentService {

    private static final String TRANSFORMER_FILE = "/transformer.txt";

    private final RestTemplate template;
    private final AppConfig config;
    private final ObjectMapper objectMapper;
    private final Expression transformer;

    public AcousticContentService(RestTemplate template, AppConfig config, ObjectMapper mapper) {
        this.template = template;
        this.config = config;
        this.objectMapper = mapper;
        try {
            String transformerConfig = IOUtils.resourceToString(TRANSFORMER_FILE, StandardCharsets.UTF_8);
            this.transformer = Parser.compileString(transformerConfig);
        } catch (IOException e) {
            log.error("Could not load transformer configuration", e);
            throw new IllegalStateException("Could not load Transformer configuration");
        }
    }

    @Override
    public List<Category> listCategories() {
        log.info("Requesting categories: url: {}", config.getCategoryUrl());

        String response = template.exchange(config.getCategoryUrl(), HttpMethod.GET, null, String.class).getBody();
        if (!StringUtils.hasText(response)) {
            return new LinkedList<>();
        }

        try {
            Response<Category> parsedResponse = objectMapper.readValue(response, new TypeReference<Response<Category>>() {});
            return parsedResponse.getDocuments();
        } catch (JsonProcessingException e) {
            log.error("Could not parse category list", e);
            throw new IllegalStateException("Could not parse categories from source", e);
        }
    }

    @Override
    public List<Document> listDocuments() {
        log.info("Requesting documents: url: {}", config.getDocumentUrl());

        String response = template.exchange(config.getDocumentUrl(), HttpMethod.GET, null, String.class).getBody();
        if (!StringUtils.hasText(response)) {
            return new LinkedList<>();
        }

        // transform the recipe into a required format

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode documentsNode = rootNode.get("documents");
            JsonNode transformedNode = transformer.apply(documentsNode);
            JavaType listOfRecipesType = objectMapper.getTypeFactory().constructCollectionType(List.class, Document.class);
            List<Document> converted = objectMapper.treeToValue(transformedNode, listOfRecipesType);
            return converted.stream().filter(p -> p != null).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            log.error("Could not parse documents list", e);
            throw new IllegalStateException("Could not parse documents from source", e);
        }
    }

}
