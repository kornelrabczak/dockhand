package com.thecookiezen.bussiness.cluster.control;

import com.thecookiezen.bussiness.cluster.entity.Cluster;

import java.util.Collection;
import java.util.Optional;

public interface ClusterRepository {
    void save(Cluster cluster);

    Collection<Cluster> getAll();

    Optional<Cluster> getById(long id);

    void remove(long clusterId);
}
