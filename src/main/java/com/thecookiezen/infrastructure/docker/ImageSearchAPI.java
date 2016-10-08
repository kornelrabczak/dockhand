package com.thecookiezen.infrastructure.docker;

import com.thecookiezen.bussiness.search.entity.ImageDetail;
import com.thecookiezen.bussiness.search.entity.ImageSearchResult;
import com.thecookiezen.bussiness.search.entity.TagsResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/v2")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
interface ImageSearchAPI {

    @GET
    @Path("/search/repositories")
    ImageSearchResult search(@QueryParam("query") String name, @QueryParam("page") @DefaultValue("1") int page);

    @GET
    @Path("/repositories/{repositoryName}/{imageName}")
    ImageDetail detail(@PathParam("repositoryName") String repositoryName, @PathParam("imageName") String imageName);

    @GET
    @Path("/repositories/{repositoryName}/{imageName}/tags")
    TagsResult tags(@PathParam("repositoryName") String repositoryName, @PathParam("imageName") String imageName,
                    @QueryParam("page") @DefaultValue("1") int page,
                    @QueryParam("page_size") @DefaultValue("250") int pageSize);
}
