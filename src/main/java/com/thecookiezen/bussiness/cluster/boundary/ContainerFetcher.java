package com.thecookiezen.bussiness.cluster.boundary;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.thecookiezen.bussiness.cluster.entity.HostInfo;
import com.thecookiezen.bussiness.cluster.entity.StatisticsLite;
import com.thecookiezen.bussiness.deployment.control.ProgressDetail;
import com.thecookiezen.bussiness.deployment.entity.DeploymentUnit;
import com.thecookiezen.bussiness.jobs.entity.Job;
import rx.Observable;

import java.util.Collection;

public interface ContainerFetcher {
    Collection<Container> getContainers();

    HostInfo getInfo();

    InspectContainerResponse getContainer(String containerId);

    Observable<StatisticsLite> stats(String containerId);

    boolean isContainerRunning(String containerId);

    Observable<String> logs(String containerId);

    void close();

    String getName();

    Observable<ProgressDetail> deploy(Job job);
}
