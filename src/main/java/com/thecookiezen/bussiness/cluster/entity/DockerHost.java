package com.thecookiezen.bussiness.cluster.entity;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.time.Instant;

@Data
public class DockerHost implements Serializable {
    private static final long serialVersionUID = 1L;

    @NonNull
    private String name;

    @NonNull
    private String dockerDaemonUrl;

    private Instant created = Instant.now();
}
