package com.thecookiezen.bussiness.cluster.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
public class DockerHost implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;

    @NonNull
    private String name;

    @NonNull
    private String dockerDaemonUrl;

    private Instant created = Instant.now();
}
