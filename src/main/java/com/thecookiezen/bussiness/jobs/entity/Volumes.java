package com.thecookiezen.bussiness.jobs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Volumes implements Serializable {
    private static final long serialVersionUID = 1L;

    private final boolean readOnly;
    private final String hostPath;
    private final String containerPath;
}
