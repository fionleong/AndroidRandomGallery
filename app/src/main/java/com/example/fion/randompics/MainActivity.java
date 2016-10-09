package com.example.fion.randompics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> urlArray;
    ArrayList<ImageButton> button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = new ArrayList<>();
        urlArray = new ArrayList<>();
        addButtons(button);

        randomPics();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            searchImages();
            return true;
        }

        if (id == R.id.savedImages)
        {
            savedImages();
            return true;
        }

        if (id == R.id.refresh)
        {
            refresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // GENERATE ANOTHER 10 RANDOM IMAGES
    public void refresh() {
        this.recreate();
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

    public void randomPics() {
        Ion.with(this)
                .load("https://api.unsplash.com/photos/random/?client_id=e8f9972b25cad1b3d651c384bd0" +
                        "40972090e2ed33e8f6e13c55134cafd882427&count=10")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        readJSON(result);
                    }
                });
    }

    public void readJSON(String result) {
        try {
            JSONArray json = new JSONArray(result); // first [
            if (json != null) {
                for (int i = 0; i < json.length(); i++) {
                    JSONObject obj = json.getJSONObject(i);  // first {
                    JSONObject innerObj = obj.getJSONObject("urls");
                    String regPic = innerObj.getString("regular");
                    urlArray.add(regPic);
                }
            }

            // DISPLAYING THE RANDOM IMAGES
            for (int i = 0; i < urlArray.size(); i++) {
                String url = urlArray.get(i);
                //Log.i("url", " = " + url);
                button.get(i).setTag(url);
                //Log.i("button tag", " = " + button.get(i).getTag());
                Ion.with(button.get(i)).load(url);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // GO TO SHOWDETAILS INTENT
    public void showDetail(View view) {
        // PROCEED IF IMAGE HAS BEEN SET
        if (view.getTag() != null)
        {
            Intent intent = new Intent(MainActivity.this, ImageDetails.class);
            if (intent != null)
            {
                intent.putExtra("imageButton", view.getTag().toString());
                startActivity(intent);
            }
        }
    }

    // GO TO SAVEDIMAGES INTENT
    public void savedImages()
    {
        Intent intent = new Intent(MainActivity.this, SavedImages.class);
        startActivity(intent);
    }

    // GO TO SEARCHACTIVITY INTENT
    public void searchImages()
    {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }
}