package com.thecookiezen.infrastructure.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Storage<T extends Identifiable> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Long, T> store = new ConcurrentHashMap<>();

    public void add(T element) {
        this.store.put(element.getId(), element);
    }

    public Optional<T> getById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Collection<T> getAll() {
        return this.store.values();
    }

    public void remove(long elementId) {
        store.remove(elementId);
    }
}
