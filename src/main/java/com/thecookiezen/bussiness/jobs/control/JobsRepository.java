package com.thecookiezen.bussiness.jobs.control;

import com.thecookiezen.bussiness.jobs.entity.Job;

import java.util.Collection;
import java.util.Optional;

public interface JobsRepository {
    void save(Job job);

    Collection<Job> getAll();

    Optional<Job> getById(long id);

    void remove(long jobId);
}
