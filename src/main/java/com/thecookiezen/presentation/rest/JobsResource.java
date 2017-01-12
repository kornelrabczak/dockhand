package com.thecookiezen.presentation.rest;

import com.thecookiezen.bussiness.jobs.control.JobsRepository;
import com.thecookiezen.bussiness.jobs.entity.Job;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@CommonsLog
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JobsResource {

    JobsRepository jobsRepository;

    @RequestMapping(value = "/api/jobs", method = RequestMethod.GET)
    public Collection<Job> getAll() {
        return jobsRepository.getAll();
    }

    @RequestMapping(value = "/api/jobs/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable long id) {
        jobsRepository.remove(id);
    }

    @RequestMapping(value = "/api/jobs/{id}", method = RequestMethod.GET)
    public ResponseEntity<Job> get(@PathVariable long id) {
        Optional<Job> byId = jobsRepository.getById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/api/jobs", method = RequestMethod.POST)
    public void save(@RequestBody Job job) {
        jobsRepository.save(job);
    }

}
