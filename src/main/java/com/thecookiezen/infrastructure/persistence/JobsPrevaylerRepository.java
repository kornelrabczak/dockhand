package com.thecookiezen.infrastructure.persistence;

import com.thecookiezen.bussiness.jobs.control.JobsRepository;
import com.thecookiezen.bussiness.jobs.entity.Job;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import pl.setblack.airomem.core.Persistent;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;

@CommonsLog
@Component
public class JobsPrevaylerRepository implements JobsRepository {

    private final Path storeFolder = Paths.get("jobsStore");

    private Persistent<Storage<Job>> storage;

    @PostConstruct
    void initController() {
        storage = Persistent.loadOptional(storeFolder, Storage::new);
    }

    @Override
    public void save(final Job job) {
        storage.execute(new SaveJobCommand(job));
    }

    @Override
    public Collection<Job> getAll() {
        return storage.query(Storage::getAll);
    }

    @Override
    public Optional<Job> getById(long id) {
        return storage.query(view -> view.getById(id));
    }

    @Override
    public void remove(long jobId) {
        storage.execute(new RemoveJobCommand(jobId));
    }
}
