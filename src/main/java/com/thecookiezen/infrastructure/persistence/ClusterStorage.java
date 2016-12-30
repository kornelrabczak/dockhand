package com.thecookiezen.infrastructure.persistence;

import com.thecookiezen.bussiness.cluster.entity.Cluster;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ClusterStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Long, Cluster> store = new ConcurrentHashMap<>();

    public void add(Cluster element) {
        this.store.put(element.getId(), element);
    }

    public Optional<Cluster> getById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Collection<Cluster> getAll() {
        return this.store.values();
    }

    public void remove(long clusterId) {
        store.remove(clusterId);
    }
}
