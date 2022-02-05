package com.example.datapipe.service;

import java.util.List;

import com.example.datapipe.model.Category;
import com.example.datapipe.model.Document;

public interface IContentService {

    List<Category> listCategories();

    List<Document> listDocuments();
}
