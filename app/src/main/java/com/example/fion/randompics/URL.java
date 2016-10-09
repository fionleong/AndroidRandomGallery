package com.example.fion.randompics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Fion on 10/7/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class URL {
    private String URL;

    public URL() {

    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
