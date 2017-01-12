package com.thecookiezen.bussiness.deployment.control;

public enum TaskState {
    PULLING_IMAGE, CREATING, STARTING, RUNNING, EXITED, STOPPING, STOPPED, FAILED
}
