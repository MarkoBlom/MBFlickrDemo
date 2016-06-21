package com.aalto.solutions.mbflickrdemo.util;

/**
 * Created by marko
 */
public class FlickrUrls
{
    public static final String flickr_photosets_getPhotos = "https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&format=%s&api_key=%s&photoset_id=%s";
    public static final String flickr_photos_getSizes = "https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&format=%s&api_key=%s&photo_id=%s";
    public static final String flickr_photos_search = "https://www.flickr.com/services/rest/?method=flickr.photos.search&format=%s&api_key=%s&lat=%s&lon=%s&radius=10&radius_units=mi&per_page=20";
}
