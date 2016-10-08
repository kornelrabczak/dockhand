package com.thecookiezen.bussiness.search.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class ImageDetail {

    private String name;

    private String namespace;

    private String description;

    @JsonProperty("last_updated")
    private Instant updated;
}
