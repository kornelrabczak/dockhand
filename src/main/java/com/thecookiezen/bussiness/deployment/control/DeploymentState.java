package com.thecookiezen.bussiness.deployment.control;

public enum DeploymentState {
    INITIALIZED, IN_QUEUE, DEPLOY, PREPARE_DEPLOY, IN_PROGRESS, RUNNING, FAILED, ROLLBACKING, ROLLBACKED, UNDEPLOYED
}
