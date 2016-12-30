package com.thecookiezen.infrastructure.persistence;

import com.thecookiezen.bussiness.cluster.entity.Cluster;
import lombok.AllArgsConstructor;
import pl.setblack.airomem.core.VoidCommand;

@AllArgsConstructor
class SaveStorageCommand implements VoidCommand<ClusterStorage> {

    final Cluster cluster;

    @Override
    public void executeVoid(ClusterStorage storage) {
        storage.add(cluster);
    }
}
