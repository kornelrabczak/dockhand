package com.thecookiezen.bussiness.deployment.boundary;

import com.thecookiezen.bussiness.deployment.entity.DeploymentUnit;

import java.util.Collection;

public interface DeploymentUnitRepository {
    DeploymentUnit save(DeploymentUnit deploymentUnit);

    Collection<DeploymentUnit> getAll();
}
