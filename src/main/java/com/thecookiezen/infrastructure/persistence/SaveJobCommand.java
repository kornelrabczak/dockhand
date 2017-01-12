package com.thecookiezen.infrastructure.persistence;

import com.thecookiezen.bussiness.jobs.entity.Job;
import lombok.AllArgsConstructor;
import pl.setblack.airomem.core.VoidCommand;

import java.util.Random;

@AllArgsConstructor
public class SaveJobCommand implements VoidCommand<Storage<Job>> {

    final Job job;

    @Override
    public void executeVoid(Storage<Job> jobStorage) {
        if (job.getId() == 0)
            job.setId(new Random().nextLong());

        jobStorage.add(job);
    }
}
