package com.thecookiezen.infrastructure.docker;

import com.github.dockerjava.api.model.Statistics;
import lombok.Value;

import java.util.Map;

@Value
class StatisticsLite {

    private Object rx_bytes;
    private Object tx_bytes;
    private Object memoryUsage;
    private Object cpuTotalUsage;

    public StatisticsLite(Statistics statistics) {
        Map<String, Object> eth0 = (Map<String, Object>) statistics.getNetworks().get("eth0");
        rx_bytes = eth0.get("rx_bytes");
        tx_bytes = eth0.get("tx_bytes");
        memoryUsage = statistics.getMemoryStats().get("usage");
        Map<String, Object> cpu_usage = (Map<String, Object>) statistics.getCpuStats().get("cpu_usage");
        cpuTotalUsage = cpu_usage.get("total_usage");
    }
}
