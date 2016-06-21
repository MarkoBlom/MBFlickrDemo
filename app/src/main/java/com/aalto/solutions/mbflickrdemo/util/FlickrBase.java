package com.aalto.solutions.mbflickrdemo.util;

/**
 * Created by marko
 */
public class FlickrBase
{
    protected String api_key;
    protected String format;

    public FlickrBase()
    {
        api_key = null;
        format = null;
    }

    public FlickrBase(String api_key, String format)
    {
        this.api_key = api_key;
        this.format = format;
    }

}
