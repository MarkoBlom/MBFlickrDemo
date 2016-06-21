package com.aalto.solutions.mbflickrdemo.model.json;

import com.aalto.solutions.mbflickrdemo.model.Sizes;

/**
 * Created by marko
 */
public class PhotosJSON extends FlickrBaseItemJSON
{
    private Sizes sizes;

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

}