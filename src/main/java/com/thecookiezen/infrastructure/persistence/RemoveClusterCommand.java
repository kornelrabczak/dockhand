package com.thecookiezen.infrastructure.persistence;

import com.thecookiezen.bussiness.cluster.entity.Cluster;
import lombok.AllArgsConstructor;
import pl.setblack.airomem.core.VoidCommand;

@AllArgsConstructor
class RemoveClusterCommand implements VoidCommand<Storage<Cluster>> {

    final long clusterId;

    @Override
    public void executeVoid(Storage<Cluster> storage) {
        storage.remove(clusterId);
    }
}
