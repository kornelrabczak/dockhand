package com.thecookiezen.bussiness.cluster.boundary;

import java.util.Iterator;

public interface ClusterFetcher {

    String getName();
    void stop();
    ContainerFetcher getNode(long nodeId);
    Iterator<ContainerFetcher> roundRobinHosts();
}
