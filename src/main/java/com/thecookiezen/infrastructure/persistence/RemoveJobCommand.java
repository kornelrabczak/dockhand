package com.thecookiezen.infrastructure.persistence;

import com.thecookiezen.bussiness.jobs.entity.Job;
import lombok.AllArgsConstructor;
import pl.setblack.airomem.core.VoidCommand;

@AllArgsConstructor
class RemoveJobCommand implements VoidCommand<Storage<Job>> {

    final long jobId;

    @Override
    public void executeVoid(Storage<Job> jobStorage) {
        jobStorage.remove(jobId);
    }
}
