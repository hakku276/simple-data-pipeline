package com.example.datapipe.service;

import java.util.List;

import com.example.datapipe.model.Category;
import com.example.datapipe.model.Document;

/**
 * A Content Service model which allows listing raw sources that needs to be
 * transformed and structured
 *
 * @author aanal
 *
 */
public interface IContentService {

    /**
     * List the categories available in the server
     *
     * @return the list of categories
     */
    List<Category> listCategories();

    /**
     * List the documents available in the server
     *
     * @return the list of documents
     */
    List<Document> listDocuments();
}
