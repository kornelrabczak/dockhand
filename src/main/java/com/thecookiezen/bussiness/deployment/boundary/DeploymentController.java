package com.thecookiezen.bussiness.deployment.boundary;

import com.thecookiezen.bussiness.cluster.boundary.ClusterFetcher;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import com.thecookiezen.bussiness.deployment.entity.DeploymentUnit;
import com.thecookiezen.bussiness.jobs.entity.Job;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

public class DeploymentController {

    private final Iterator<ContainerFetcher> roundRobinNodes;

    private final Map<Long, DeploymentUnit> runningDeployments = new ConcurrentHashMap<>();

    private final Collection<Job> pendingJobs = new PriorityQueue<>();

    public DeploymentController(ClusterFetcher clusterInstance) {
        this.roundRobinNodes = clusterInstance.roundRobinHosts();
    }

    public void deploy(Job job) {
        final ContainerFetcher node = roundRobinNodes.next();
        node.deploy(job);
    }
}
