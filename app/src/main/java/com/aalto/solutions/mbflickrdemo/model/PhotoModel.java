package com.aalto.solutions.mbflickrdemo.model;

/**
 * Created by marko
 * Photo data itself
 */
public class PhotoModel
{
    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    //private String isprimary; PHOTOSET only
    private int ispublic;
    private int isfriend;
    private int isfamily;

    //=========================

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
