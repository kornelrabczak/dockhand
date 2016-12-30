package com.thecookiezen.infrastructure.persistence;

import lombok.AllArgsConstructor;
import pl.setblack.airomem.core.VoidCommand;

@AllArgsConstructor
class RemoveClusterCommand implements VoidCommand<ClusterStorage> {

    final long clusterId;

    @Override
    public void executeVoid(ClusterStorage storage) {
        storage.remove(clusterId);
    }
}
