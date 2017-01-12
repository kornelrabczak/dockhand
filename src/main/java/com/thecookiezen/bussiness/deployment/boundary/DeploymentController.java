package com.thecookiezen.bussiness.deployment.boundary;

import com.google.common.collect.Iterables;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import com.thecookiezen.bussiness.cluster.control.ClusterInstance;
import com.thecookiezen.bussiness.cluster.entity.Cluster;
import com.thecookiezen.bussiness.deployment.entity.DeploymentUnit;
import com.thecookiezen.bussiness.jobs.entity.Job;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

public class DeploymentController {

    private final Cluster cluster;

    private final Iterator<ContainerFetcher> roundRobinNodes;

    private final Map<Long, DeploymentUnit> runningDeployments = new ConcurrentHashMap<>();

    private final Collection<Job> pendingJobs = new PriorityQueue<>();

    public DeploymentController(ClusterInstance clusterInstance) {
        this.cluster = clusterInstance.getCluster();
        this.roundRobinNodes = Iterables.cycle(clusterInstance.getNodes().values()).iterator();
    }

    public void deploy(Job job) {
        final ContainerFetcher node = roundRobinNodes.next();
        node.deploy(job);
    }
}
