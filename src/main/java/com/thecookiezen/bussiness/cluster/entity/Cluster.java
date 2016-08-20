package com.thecookiezen.bussiness.cluster.entity;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class Cluster implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    private long id;

    @NonNull
    private String name;

    private Duration healthCheckInterval = Duration.ofSeconds(15);

    private Collection<DockerHost> hosts = new ArrayList<>();
}
