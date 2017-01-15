package com.thecookiezen.bussiness.deployment.entity;

import com.thecookiezen.bussiness.deployment.control.DeploymentState;
import com.thecookiezen.bussiness.deployment.control.TaskState;
import com.thecookiezen.bussiness.jobs.entity.Job;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.thecookiezen.bussiness.deployment.control.DeploymentState.INITIALIZED;

@Data
@RequiredArgsConstructor
public class DeploymentUnit {

    private final long id;

    private final Job job;

    private DeploymentState state = INITIALIZED;

    private final Map<String, TaskState> taskStateMap = new ConcurrentHashMap<>();

    private final int replicaFactor = 1;
}
