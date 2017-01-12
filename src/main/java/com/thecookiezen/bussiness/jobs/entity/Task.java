package com.thecookiezen.bussiness.jobs.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
public class Task implements Serializable {

    private final static long serialVersionUID = 1L;

    private String name;

    private String image;

    private String imageVersion;

    private final Instant created = Instant.now();

    private final Instant started = Instant.now();

    private Set<Env> envs = new HashSet<>();

    private Set<Volumes> volumes = new HashSet<>();

    private Set<PortMapping> ports = new HashSet<>();

}
