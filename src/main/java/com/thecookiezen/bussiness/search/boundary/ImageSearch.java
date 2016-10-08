package com.thecookiezen.bussiness.search.boundary;

import com.thecookiezen.bussiness.search.entity.ImageDetail;
import com.thecookiezen.bussiness.search.entity.SimpleImage;
import com.thecookiezen.bussiness.search.entity.Tag;

import java.util.List;

public interface ImageSearch {
    List<SimpleImage> findImages(String name);

    List<Tag> findTags(String repositoryName, String imageName);

    ImageDetail getDetails(String repositoryName, String imageName);
}
