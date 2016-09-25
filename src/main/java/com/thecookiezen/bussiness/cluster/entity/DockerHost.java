package com.thecookiezen.bussiness.cluster.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.time.Instant;
import java.util.Random;

@Data
public class DockerHost implements Serializable {
    private static final long serialVersionUID = 1L;

    private final long id;

    @NonNull
    private String name;

    @NonNull
    private String dockerDaemonUrl;

    private final Instant created = Instant.now();

    public DockerHost() {
        id = new Random().nextLong();
    }
}
