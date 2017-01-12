package com.thecookiezen.bussiness.jobs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MetaEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private String key;
    private String value;
}
