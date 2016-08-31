package com.thecookiezen.infrastructure.persistence;

import lombok.extern.log4j.Log4j;
import pl.setblack.airomem.core.WriteChecker;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j
public class Storage<T extends Identifiable> implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<T> store = new CopyOnWriteArrayList<>();

    public void add(T element) {
        assert WriteChecker.hasPrevalanceContext();
        this.store.add(element);
    }

    public T getById(long id) {
        return store.stream().filter(e -> e.getId() == id).findFirst().get();
    }

    public Collection<T> getAll() {
        return this.store;
    }

    public void remove(long clusterId) {
        T byId = getById(clusterId);
        store.remove(byId);
    }
}
