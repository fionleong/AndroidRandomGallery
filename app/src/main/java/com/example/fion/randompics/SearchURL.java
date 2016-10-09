package com.example.fion.randompics;

import java.util.ArrayList;

/**
 * Created by Fion on 10/8/2016.
 */

public class SearchURL {
    private ArrayList<String> URL;
    private String searchInput;

    public SearchURL() {

    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public String getURL(int i) {
        return URL.get(i);
    }

    public void addURL(String link) {
        URL.add(link);
    }
}
