package com.thecookiezen.bussiness.deployment.control;

import lombok.Value;

import java.io.Serializable;

@Value
public class ProgressDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
}
