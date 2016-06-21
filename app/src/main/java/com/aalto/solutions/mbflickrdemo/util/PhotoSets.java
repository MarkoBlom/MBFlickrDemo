package com.aalto.solutions.mbflickrdemo.util;

import com.aalto.solutions.mbflickrdemo.model.PhotoModel;
import com.aalto.solutions.mbflickrdemo.model.json.PhotoSetsJSON;
import com.hintdesk.core.utils.JSONHttpClient;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marko
 */
public class PhotoSets extends FlickrBase
{
    public PhotoSets(String api_key, String format)
    {
        super(api_key, format);
    }

    /* For Photoset
    public List<PhotoModel> getPhotos(String photoset_id)
    {
        JSONHttpClient jsonHttpClient = new JSONHttpClient();
        String url = String.format(FlickrUrls.flickr_photosets_getPhotos, format, api_key, photoset_id);
        PhotoSetsJSON photoSetJson = jsonHttpClient.Get(url, new ArrayList<NameValuePair>(), PhotoSetsJSON.class);

        return photoSetJson.getPhotoset().getPhoto();
    }
    */

    // For search
    public List<PhotoModel> getPhotos(String aLat, String aLon)
    {
        JSONHttpClient jsonHttpClient = new JSONHttpClient();
        String url = String.format(FlickrUrls.flickr_photos_search, format, api_key, aLat, aLon);
        PhotoSetsJSON photoSetJson = jsonHttpClient.Get(url, new ArrayList<NameValuePair>(), PhotoSetsJSON.class);

        return photoSetJson.getPhotoset().getPhoto();
    }

}
