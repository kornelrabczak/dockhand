package com.thecookiezen.infrastructure.docker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.thecookiezen.bussiness.search.boundary.ImageSearch;
import com.thecookiezen.bussiness.search.entity.ImageDetail;
import com.thecookiezen.bussiness.search.entity.SimpleImage;
import com.thecookiezen.bussiness.search.entity.Tag;
import lombok.extern.log4j.Log4j;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.List;

@Log4j
@Component
public class DockerHubImageSearch implements ImageSearch {

    private static final String DOCKER_HUB_HOST = "https://hub.docker.com";

    @Autowired
    private ObjectMapper objectMapper;

    private ImageSearchAPI imageSearchAPI;

    @PostConstruct
    public void init() {
        final ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(ClientProperties.READ_TIMEOUT, 1000);
        clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 500);

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10);
        connectionManager.setDefaultMaxPerRoute(10);
        clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);

        Client client = ClientBuilder.newClient(clientConfig);
        client.register(new JacksonJsonProvider(objectMapper));

        WebTarget webTarget = client.target(DOCKER_HUB_HOST);

        imageSearchAPI = WebResourceFactory.newResource(ImageSearchAPI.class, webTarget);
    }

    @Override
    public List<SimpleImage> findImages(String name) {
        return imageSearchAPI.search(name, 1).getImages();
    }

    @Override
    public List<Tag> findTags(String repositoryName, String imageName) {
        return imageSearchAPI.tags(repositoryName, imageName, 1, 250).getResults();
    }

    @Override
    public ImageDetail getDetails(String repositoryName, String imageName) {
        return imageSearchAPI.detail(repositoryName, imageName);
    }
}
