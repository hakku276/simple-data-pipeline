package com.example.datapipe.service;

import org.springframework.data.repository.CrudRepository;

import com.example.datapipe.model.Document;

public interface IRecipeRepository extends CrudRepository<Document, String> {

}
