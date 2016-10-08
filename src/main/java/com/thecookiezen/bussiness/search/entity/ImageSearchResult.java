package com.thecookiezen.bussiness.search.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImageSearchResult {

    @JsonProperty("results")
    private List<SimpleImage> images = new ArrayList<>();
}
