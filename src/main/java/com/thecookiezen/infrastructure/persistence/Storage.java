package com.thecookiezen.infrastructure.persistence;

import pl.setblack.airomem.core.WriteChecker;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Storage<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Long, T> store = new ConcurrentHashMap<>();

    public void add(T element) {
        assert WriteChecker.hasPrevalanceContext();
        this.store.put(new Random().nextLong(), element);
    }

    public T getById(long id) {
        return store.get(id);
    }

    public Collection<T> getAll() {
        return this.store.values();
    }
}
