package com.thecookiezen.bussiness.cluster.boundary;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Statistics;

public interface StatisticsProvider {
    void getStats(String containerId, ResultCallback<Statistics> resultCallback);
}
