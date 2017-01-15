package com.thecookiezen.bussiness.cluster.boundary;

public interface ClusterFetcher {

    String getName();
    void stop();
    ContainerFetcher getNode(long nodeId);
}
