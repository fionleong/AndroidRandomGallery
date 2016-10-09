package com.example.fion.randompics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class ImageDetails extends AppCompatActivity {
    ArrayList<String> savedUrl;
    private Firebase firebase;
    private URL link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgdetails);

        Intent intent = getIntent();
        String url = intent.getStringExtra("imageButton");

        // SETTING IMAGE VIEW WITH THE IMAGE FROM THE BUTTON
        ImageView image = (ImageView) findViewById(R.id.image);
        Ion.with(image).load(url);

        savedUrl = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SETTING UP FIREBASE DB
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://randompics-6fd86.firebaseio.com/");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_imgdetails, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            saveImage();
            return true;
        }

        if (id == android.R.id.home)
        {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveImage() {

        link = new URL();

        // GETTING THE URL FROM THE BUTTON
        Intent intent = getIntent();
        String url = intent.getStringExtra("imageButton");

        link.setURL(url);

        // SAVING THE URL TO DB
        firebase.child("URL").push().setValue(link);

        Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();
    }
}