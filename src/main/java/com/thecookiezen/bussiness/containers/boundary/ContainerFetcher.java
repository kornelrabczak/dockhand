package com.thecookiezen.bussiness.containers.boundary;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;

import java.util.Collection;

public interface ContainerFetcher {
    Collection<Container> list();
    Info getInfo();
}
