package com.aalto.solutions.mbflickrdemo.util;

import com.aalto.solutions.mbflickrdemo.model.Size;
import com.aalto.solutions.mbflickrdemo.model.json.PhotosJSON;
import com.hintdesk.core.utils.JSONHttpClient;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by marko
 */
public class Photos extends FlickrBase
{
    public Photos(String api_key, String format) {
        super(api_key, format);
    }

    public List<Size> getSizes(String photo_id)
    {
        JSONHttpClient jsonHttpClient = new JSONHttpClient();
        String url = String.format(FlickrUrls.flickr_photos_getSizes, format, api_key, photo_id);
        PhotosJSON photosJson = jsonHttpClient.Get(url, new ArrayList<NameValuePair>(), PhotosJSON.class);
        return photosJson.getSizes().getSize();

    }


}
