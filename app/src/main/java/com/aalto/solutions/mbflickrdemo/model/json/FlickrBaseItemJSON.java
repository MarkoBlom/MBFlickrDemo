package com.aalto.solutions.mbflickrdemo.model.json;

import java.io.Serializable;

/**
 * Created by marko
 */

// Root level
public class FlickrBaseItemJSON implements Serializable {

    private String stat;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}