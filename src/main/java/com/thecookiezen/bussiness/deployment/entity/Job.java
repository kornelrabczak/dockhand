package com.thecookiezen.bussiness.deployment.entity;

import com.thecookiezen.infrastructure.persistence.Identifiable;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

@Data
public class Job implements Identifiable, Serializable {

    private long id;

    private String name;

    private final Instant created = Instant.now();

    private final Instant started = Instant.now();

    private Map<String, String> metadata = emptyMap();

    private final List<Task> tasks = emptyList();
}
