package com.thecookiezen.infrastructure.persistence;

import com.thecookiezen.bussiness.cluster.control.ClusterRepository;
import com.thecookiezen.bussiness.cluster.entity.Cluster;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import pl.setblack.airomem.core.Persistent;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@Component
@CommonsLog
public class ClusterPrevaylerRepository implements ClusterRepository {

    private final Path storeFolder = Paths.get("clustersStore");

    private Persistent<ClusterStorage> storage;

    @PostConstruct
    void initController() {
        storage =  Persistent.loadOptional(storeFolder, ClusterStorage::new);
    }

    @Override
    public void save(final Cluster cluster) {
        if (cluster.getId() == 0) {
            cluster.setId(new Random().nextLong());
        } else {
            Optional<Cluster> byId = getById(cluster.getId());
            if (byId.isPresent()) {
                cluster.setHosts(byId.get().getHosts());
            }
        }

        storage.execute(new SaveStorageCommand(cluster));
    }

    @Override
    public Collection<Cluster> getAll() {
        return storage.query(ClusterStorage::getAll);
    }

    @Override
    public Optional<Cluster> getById(long id) {
        return storage.query(view -> view.getById(id));
    }

    @Override
    public void remove(long clusterId) {
        storage.execute(new RemoveClusterCommand(clusterId));
    }
}
