package com.thecookiezen.bussiness.cluster.entity;

import lombok.Value;

@Value
public class HostInfo {
    private final String name;
    private final String hostname;
    private final String osName;
    private final String osVersion;
    private final int containers;
    private final int containersStopped;
    private final int containersPaused;
    private final int containersRunning;
    private final long memTotal;
    private final String kernelVersion;
}
