package com.thecookiezen.infrastructure.persistence;

import com.thecookiezen.bussiness.cluster.control.ClusterRepository;
import com.thecookiezen.bussiness.cluster.entity.Cluster;
import org.springframework.stereotype.Component;
import pl.setblack.airomem.core.PersistenceController;
import pl.setblack.airomem.core.builders.PersistenceFactory;
import pl.setblack.airomem.data.DataRoot;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@Component
public class ClusterPrevaylerRepository implements ClusterRepository {

    private PersistenceController<DataRoot<Storage<Cluster>, Storage<Cluster>>, Storage<Cluster>> controller;

    @PostConstruct
    void initController() {
        final PersistenceFactory factory = new PersistenceFactory();
        controller = factory.initOptional("clusters", () -> new DataRoot<>(new Storage<Cluster>()));
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

        controller.execute(view -> view.getDataObject().add(cluster));
    }

    @Override
    public Collection<Cluster> getAll() {
        return controller.query(Storage::getAll);
    }

    @Override
    public Optional<Cluster> getById(long id) {
        return controller.query(view -> view.getById(id));
    }

    @Override
    public void remove(long clusterId) {
        controller.execute(view -> view.getDataObject().remove(clusterId));
    }
}
