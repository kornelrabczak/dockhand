package com.thecookiezen.bussiness.deployment.boundary;

import com.thecookiezen.bussiness.cluster.boundary.ClusterFetcher;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import com.thecookiezen.bussiness.deployment.entity.DeploymentUnit;
import com.thecookiezen.bussiness.jobs.entity.Job;
import lombok.extern.apachecommons.CommonsLog;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@CommonsLog
public class DeploymentController {

    private DeploymentUnitRepository deploymentUnitRepository;

    private final Iterator<ContainerFetcher> roundRobinNodes;

    private final Map<Long, DeploymentUnit> runningDeployments = new ConcurrentHashMap<>();

    private final Subject<Job, Job> pendingJobs = PublishSubject.<Job>create().toSerialized();

    public DeploymentController(ClusterFetcher clusterInstance) {
        this.roundRobinNodes = clusterInstance.roundRobinHosts();

        pendingJobs
                .observeOn(Schedulers.computation())
                .map(job -> deploymentUnitRepository.save(new DeploymentUnit(new Random().nextLong(), job)))
                .forEach(this::deploy);
    }

    public void scheduleJobDeployment(Job job) {
        pendingJobs.onNext(job);
    }

    private void deploy(DeploymentUnit deploymentUnit) {
        roundRobinNodes.next().deploy(deploymentUnit.getJob())
                .doOnError(log::error)
                .doOnCompleted(() -> runningDeployments.putIfAbsent(deploymentUnit.getId(), deploymentUnit));
    }

    public void stop() {
        pendingJobs.onCompleted();
    }
}
