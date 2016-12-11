package com.thecookiezen.bussiness.application.entity;

import com.thecookiezen.infrastructure.persistence.Identifiable;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class Application implements Identifiable, Serializable {

    private long id;

    private String name;

    private final Instant created = Instant.now();

    private String dockerImage;

    private String tag;

    private List<Env> environments = new ArrayList<>();

    private List<PortMapping> portMappings = new ArrayList<>();

}
