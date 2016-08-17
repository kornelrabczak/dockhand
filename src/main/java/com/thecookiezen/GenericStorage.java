package com.thecookiezen;

import org.springframework.stereotype.Component;
import pl.setblack.airomem.core.PersistenceController;
import pl.setblack.airomem.core.builders.PersistenceFactory;
import pl.setblack.airomem.data.DataRoot;

import javax.annotation.PostConstruct;

@Component
public class GenericStorage {

    private PersistenceController<DataRoot<Object, Object>, Object> controller;

    @PostConstruct
    void initController() {
        final PersistenceFactory factory = new PersistenceFactory();
        controller = factory.initOptional("test", () -> new DataRoot<>(new Object()));
    }

}
