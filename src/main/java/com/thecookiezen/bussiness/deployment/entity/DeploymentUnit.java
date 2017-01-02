package com.thecookiezen.bussiness.deployment.entity;

import com.thecookiezen.bussiness.cluster.entity.Cluster;
import lombok.Data;

@Data
public class DeploymentUnit {

    private final String user;

    private final Job job;

    private final Cluster cluster;

    private final DeploymentStatus status;

    private final int replicaFactor = 1;
}
