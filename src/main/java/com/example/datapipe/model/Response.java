package com.example.datapipe.model;

import java.util.List;

import lombok.Data;

@Data
public class Response<T> {

    private int numFound;

    private List<T> documents;
}
