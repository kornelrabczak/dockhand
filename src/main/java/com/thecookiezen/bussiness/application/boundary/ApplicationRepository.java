package com.thecookiezen.bussiness.application.boundary;

import com.thecookiezen.bussiness.application.entity.Application;

import java.util.Collection;
import java.util.Optional;

public interface ApplicationRepository {
    void save(Application application);

    Collection<Application> getAll();

    Optional<Application> getById(long id);

    void remove(long applicationId);
}
