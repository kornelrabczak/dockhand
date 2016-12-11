package com.thecookiezen.infrastructure.persistence;

import com.thecookiezen.bussiness.application.boundary.ApplicationRepository;
import com.thecookiezen.bussiness.application.entity.Application;
import org.springframework.stereotype.Component;
import pl.setblack.airomem.core.PersistenceController;
import pl.setblack.airomem.core.builders.PersistenceFactory;
import pl.setblack.airomem.data.DataRoot;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@Component
public class ApplicationPrevaylerRepository implements ApplicationRepository {
    private PersistenceController<DataRoot<Storage<Application>, Storage<Application>>, Storage<Application>> controller;

    @PostConstruct
    void initController() {
        final PersistenceFactory factory = new PersistenceFactory();
        controller = factory.initOptional("application", () -> new DataRoot<>(new Storage<Application>()));
    }

    @Override
    public void save(final Application application) {
        if (application.getId() == 0) {
            application.setId(new Random().nextLong());
        }
        controller.execute(view -> view.getDataObject().add(application));
    }

    @Override
    public Collection<Application> getAll() {
        return controller.query(Storage::getAll);
    }

    @Override
    public Optional<Application> getById(long id) {
        return controller.query(view -> view.getById(id));
    }

    @Override
    public void remove(long applicationId) {
        controller.execute(view -> view.getDataObject().remove(applicationId));
    }
}
