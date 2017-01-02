package com.thecookiezen.bussiness.deployment.entity;

import lombok.Data;

import java.time.Instant;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Data
public class Task {

    private String image;

    private String imageVersion;

    private final Instant created = Instant.now();

    private final Instant started = Instant.now();

    private Map<String, String> env = emptyMap();

    private Map<String, String> volumes = emptyMap();

    private Map<String, PortMapping> ports = emptyMap();

}
