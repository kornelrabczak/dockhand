package com.thecookiezen.bussiness.deployment.entity;

import com.thecookiezen.bussiness.deployment.control.DeploymentState;
import com.thecookiezen.bussiness.deployment.control.TaskState;
import com.thecookiezen.bussiness.jobs.entity.Job;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class DeploymentUnit {

    private final String user;

    private final Job job;

    private final DeploymentState state;

    private final Map<String, TaskState> taskStateMap = new ConcurrentHashMap<>();

    private final int replicaFactor = 1;
}
