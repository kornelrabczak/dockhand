package com.thecookiezen.bussiness.jobs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Env implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String value;
}
