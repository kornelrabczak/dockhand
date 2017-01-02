package com.thecookiezen.infrastructure.persistence;

import com.thecookiezen.bussiness.cluster.entity.Cluster;
import lombok.AllArgsConstructor;
import pl.setblack.airomem.core.VoidCommand;

@AllArgsConstructor
class SaveStorageCommand implements VoidCommand<Storage<Cluster>> {

    final Cluster cluster;

    @Override
    public void executeVoid(Storage<Cluster> storage) {
        storage.add(cluster);
    }
}
