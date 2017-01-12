package com.thecookiezen.bussiness.deployment.control;

import lombok.Value;

@Value
public class DeploymentCommand {
    private final String jobId;
}
