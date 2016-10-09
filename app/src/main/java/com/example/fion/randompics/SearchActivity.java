package com.example.fion.randompics;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.ImageButton;


import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends MainActivity{
    private SearchURL searchURL;
    ArrayList<String> urlArray;
    ArrayList<ImageButton> button;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView = (SearchView) findViewById(R.id.searchBar);
        searchURL = new SearchURL();
        button = new ArrayList<>();
        urlArray = new ArrayList<>();
        addButtons(button);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                searchURL.setSearchInput(query);
                // STARTS SEARCHING IF THERE ARE MORE THAN 2 LETTERS
                if (query.length() > 1)
                {
                    // EMPTYING OUT THE ARRAY
                    if (urlArray != null)
                    {
                        urlArray.clear();
                    }
                    searchImages();
                }
                else
                {
                    return false;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                searchURL.setSearchInput(newText);
                if (newText.length() > 1)
                {
                    // EMPTYING OUT THE ARRAY
                    if (urlArray != null)
                    {
                        urlArray.clear();
                    }
                    searchImages();
                }
                else
                {
                    return false;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
        {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchImages() {
        String apiLink = "https://api.unsplash.com/search/photos/?client_id=e8f9972b25cad1b3d651c384bd0" +
                "40972090e2ed33e8f6e13c55134cafd882427&page=1&query=" + searchURL.getSearchInput() + "";
        Ion.with(this)
                .load(apiLink)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        readSearchJSON(result);
                    }
                });
    }

    public void readSearchJSON(String result) {
        try {
            JSONObject json = new JSONObject(result); // first {
            JSONArray firstObj = json.getJSONArray("results");
            if (firstObj != null) {
                for (int i = 0; i < firstObj.length(); i++) {
                    JSONObject obj = firstObj.getJSONObject(i);  // first {
                    JSONObject innerObj = obj.getJSONObject("urls");
                    String regPic = innerObj.getString("regular");
                    urlArray.add(regPic);
                }
            }

            // DISPLAYING ALL THE IMAGES
            for (int i = 0; i < urlArray.size(); i++) {
                if (i < 10)
                {
                    String url = urlArray.get(i);
                    button.get(i).setTag(url);
                    Ion.with(button.get(i)).load(url);
                }
                else
                {
                    break;
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

