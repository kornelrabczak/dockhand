package com.thecookiezen.bussiness.jobs.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.thecookiezen.infrastructure.persistence.Identifiable;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
public class Job implements Identifiable, Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    private String name;

    private final Instant created = Instant.now();

    private final Set<MetaEntry> metadata = new HashSet<>();

    private final Set<Task> tasks = new HashSet<>();
}
