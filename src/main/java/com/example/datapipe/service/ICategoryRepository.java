package com.example.datapipe.service;

import org.springframework.data.repository.CrudRepository;

import com.example.datapipe.model.Category;

public interface ICategoryRepository extends CrudRepository<Category, String> {

}
