package com.aalto.solutions.mbflickrdemo.model.json;

import com.aalto.solutions.mbflickrdemo.model.PhotoSet;

/**
 * Created by marko
 */
public class PhotoSetsJSON extends FlickrBaseItemJSON
{
    // ORIG private PhotoSet photoset;
    private PhotoSet photos;

    public PhotoSet getPhotoset() {
        return photos;
    }

    public void setPhotoset(PhotoSet photos) {
        this.photos = photos;
    }

}