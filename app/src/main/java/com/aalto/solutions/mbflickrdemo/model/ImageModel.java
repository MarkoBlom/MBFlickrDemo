package com.aalto.solutions.mbflickrdemo.model;

import android.graphics.Bitmap;

/**
 * Created by marko
 */
public class ImageModel
{
    private String name;

    private String mediumUrl;

    private Bitmap thumbnailBitmap;

    private Bitmap mediumBitmap;

    //=============================================

    public ImageModel(String name, Bitmap bitmap, Bitmap mediumBitmap)
    {
        if(name!=null)
            this.name = name;
        else
            this.name = "no name";

        this.thumbnailBitmap = bitmap;
        this.mediumBitmap = mediumBitmap;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Bitmap getThumbnailBitmap()
    {
        return thumbnailBitmap;
    }

    public void setThumbnailBitmap(Bitmap thumbnailBitmap)
    {
        this.thumbnailBitmap = thumbnailBitmap;
    }

    public Bitmap getMediumBitmap()
    {
        return mediumBitmap;
    }

    public void setMediumBitmap(Bitmap mediumBitmap)
    {
        this.mediumBitmap = mediumBitmap;
    }

}
