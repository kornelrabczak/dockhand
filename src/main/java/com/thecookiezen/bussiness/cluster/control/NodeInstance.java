package com.thecookiezen.bussiness.cluster.control;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.thecookiezen.bussiness.cluster.boundary.ContainerFetcher;
import lombok.Data;
import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Cancellable;

import java.util.Collection;

@Data
public class NodeInstance implements ContainerFetcher {

    private final long id;

    private final String name;

    private final DockerClient dockerClient;

    @Override
    public Collection<Container> getContainers() {
        return dockerClient.listContainersCmd().withShowAll(true).exec();
    }

    @Override
    public Info getInfo() {
        return dockerClient.infoCmd().exec();
    }

    public InspectContainerResponse getContainer(String containerId) {
        return dockerClient.inspectContainerCmd(containerId).exec();
    }

    @Override
    public StatsCmd statsCmd(String containerId) {
        return dockerClient.statsCmd(containerId);
    }

    @Override
    public boolean isContainerRunning(String containerId) {
        try {
            InspectContainerResponse exec = dockerClient.inspectContainerCmd(containerId).exec();
            if (exec == null) {
                return false;
            }
            return exec.getState().getRunning();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Observable<String> logs(String containerId) {
        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(containerId)
                .withFollowStream(true)
                .withTailAll()
                .withStdErr(true)
                .withStdOut(true);

        return Observable.fromEmitter(stringEmitter -> {
            final ResultCallback logContainerResultCallback = new LogContainerResultCallback() {
                @Override
                public void onNext(Frame item) {
                    stringEmitter.onNext(new String(item.getPayload()));
                }
            };
            stringEmitter.setCancellation(logContainerResultCallback::onComplete);
            logContainerCmd.exec(logContainerResultCallback);
        }, Emitter.BackpressureMode.BUFFER);
    }
}
