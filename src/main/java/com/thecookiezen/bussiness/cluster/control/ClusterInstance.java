package com.thecookiezen.bussiness.cluster.control;

import com.github.dockerjava.api.DockerClient;
import com.thecookiezen.bussiness.cluster.entity.Cluster;

import java.util.Map;

public class ClusterInstance {

    private Cluster cluster;

    private Map<Long, DockerClient> dockerClients;

}
