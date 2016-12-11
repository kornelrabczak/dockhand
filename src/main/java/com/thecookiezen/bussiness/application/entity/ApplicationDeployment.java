package com.thecookiezen.bussiness.application.entity;

import com.thecookiezen.bussiness.cluster.entity.Cluster;
import lombok.Data;

@Data
public class ApplicationDeployment {

    private final Application application;

    private final Cluster cluster;

    private final int replicaFactor = 1;

}
