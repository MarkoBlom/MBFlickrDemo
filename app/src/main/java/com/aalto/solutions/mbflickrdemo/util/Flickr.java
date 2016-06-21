package com.aalto.solutions.mbflickrdemo.util;

/**
 * Created by marko on 6/18/2016.
 */
public class Flickr extends FlickrBase
{
    private Photos photos;
    private PhotoSets photoSets;

    //==============================

    public Flickr(String api_key, String format)
    {
        super(api_key, format);

        photoSets = new PhotoSets(api_key, format);
        photos = new Photos(api_key, format);
    }

    public Photos getPhotos() { return photos; }

    public void setPhotos(Photos photos) { this.photos = photos; }


    public PhotoSets getPhotoSets() {return photoSets; }

    public void setPhotoSets(PhotoSets photoSets) {this.photoSets = photoSets; }


}
