package com.example.fion.randompics;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ImageButton;
import android.widget.Toast;

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

        //*** setOnQueryTextListener ***
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                searchURL.setSearchInput(query);
                if (query.length() > 1)
                {
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
                    if (urlArray != null)
                    {
                        urlArray.clear();
                    }
                    searchImages();
//                    Toast.makeText(getBaseContext(), newText, Toast.LENGTH_SHORT).show();
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


    public void addButtons(ArrayList<ImageButton> button) {
        button.add((ImageButton) findViewById(R.id.imageButton1));
        button.add((ImageButton) findViewById(R.id.imageButton2));
        button.add((ImageButton) findViewById(R.id.imageButton3));
        button.add((ImageButton) findViewById(R.id.imageButton4));
        button.add((ImageButton) findViewById(R.id.imageButton5));
        button.add((ImageButton) findViewById(R.id.imageButton6));
        button.add((ImageButton) findViewById(R.id.imageButton7));
        button.add((ImageButton) findViewById(R.id.imageButton8));
        button.add((ImageButton) findViewById(R.id.imageButton9));
        button.add((ImageButton) findViewById(R.id.imageButton10));
    }

    public void searchImages() {
        //Log.i("SearchInput", " = " + searchURL.getSearchInput());
        String apiLink = "https://api.unsplash.com/search/photos/?client_id=e8f9972b25cad1b3d651c384bd0" +
                "40972090e2ed33e8f6e13c55134cafd882427&page=1&query=" + searchURL.getSearchInput() + "";
        //Log.i("apiLink", " = " + apiLink);
        Ion.with(this)
                .load(apiLink)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        // do stuff with the result or error
                        //Log.i("String result", " = " + result);
                        readJSON(result);
                    }
                });
    }

    public void readJSON(String result) {
        try {
            JSONObject json = new JSONObject(result); // first {
            JSONArray firstObj = json.getJSONArray("results");
            if (firstObj != null) {
                //Log.i("First json obj", " = " + firstObj);
                for (int i = 0; i < firstObj.length(); i++) {
                    JSONObject obj = firstObj.getJSONObject(i);  // first {
                    JSONObject innerObj = obj.getJSONObject("urls");
                    String regPic = innerObj.getString("regular");
                    //Log.i("URL: ", " = " + regPic);
                    urlArray.add(regPic);
                }
            }

            for (int i = 0; i < urlArray.size(); i++) {
                if (i < 10)
                {
                    String url = urlArray.get(i);
                    //Log.i("url", " = " + url);
                    button.get(i).setTag(url);
                    //Log.i("button tag", " = " + button.get(i).getTag());
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

