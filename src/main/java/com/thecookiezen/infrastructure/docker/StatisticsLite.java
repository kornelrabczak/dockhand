package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.model.Statistics;
import lombok.Value;

import java.util.Map;

@Value
class StatisticsLite {

    private int rx_bytes;
    private int tx_bytes;
    private int memoryUsage;
    private int cpuTotalUsage;

    public StatisticsLite(Statistics statistics) {
        Map<String, Integer> eth0 = (Map<String, Integer>) statistics.getNetworks().get("eth0");
        rx_bytes = eth0.get("rx_bytes");
        tx_bytes = eth0.get("tx_bytes");
        memoryUsage = (int) statistics.getMemoryStats().get("usage");
        Map<String, Integer> cpu_usage = (Map<String, Integer>) statistics.getCpuStats().get("cpu_usage");
        cpuTotalUsage = cpu_usage.get("total_usage");
    }
}