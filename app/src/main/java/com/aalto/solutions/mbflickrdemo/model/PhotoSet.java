package com.aalto.solutions.mbflickrdemo.model;

import com.aalto.solutions.mbflickrdemo.model.PhotoModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by marko
 */
public class PhotoSet implements Serializable
{
    /* Photoset data
    private String id;
    private String primary;
    private String owner;
    private List<PhotoModel> photo;
    private String page;
    private String per_page;
    private String pages;
    private String total;
    private String title;
    private String ownername;
    private String perpage;
    */

    // photos
    private int page;
    private int pages;
    private int perpage;
    private String total;
    private List<PhotoModel> photo;

    public List<PhotoModel> getPhoto() { return photo; }

    public void setPhoto(List<PhotoModel> photo) { this.photo = photo; }
}
