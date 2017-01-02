package com.thecookiezen.bussiness.cluster.entity;

import com.thecookiezen.infrastructure.persistence.Identifiable;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class Cluster implements Identifiable, Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    @Size(min = 3, max = 10)
    private String dockerApiVersion;

    @Size(min = 5, max = 30)
    private String name;

    private int maxContainers;

    @NotNull
    private Duration healthCheckInterval = Duration.ofSeconds(15);

    private Collection<DockerHost> hosts = new ArrayList<>();
}
