package com.thecookiezen.bussiness.cluster.boundary;

import com.thecookiezen.bussiness.cluster.control.ClusterInstance;
import com.thecookiezen.bussiness.cluster.control.ClusterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Component
@Log4j
//@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClustersManager {

//    private ClusterRepository clusterRepository;
//
//    private Map<Long, ClusterInstance> instances = new ConcurrentHashMap<>();
//
//    @PostConstruct
//    private void onInit() {
//        clusterRepository.getAll().forEach(cluster -> instances.put(cluster.getId(), new ClusterInstance()));
//    }
}
