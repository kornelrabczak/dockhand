package com.thecookiezen.bussiness.cluster.boundary;

import com.thecookiezen.bussiness.jobs.entity.Job;

import java.util.Iterator;

public interface ClusterFetcher {

    String getName();
    void stop();
    ContainerFetcher getNode(long nodeId);
    Iterator<ContainerFetcher> roundRobinHosts();
    void deploy(Job job);
}
