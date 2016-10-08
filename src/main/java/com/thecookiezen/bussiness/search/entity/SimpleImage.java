package com.thecookiezen.bussiness.search.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SimpleImage {
    @JsonProperty("repo_name")
    private String repositoryName;
}
