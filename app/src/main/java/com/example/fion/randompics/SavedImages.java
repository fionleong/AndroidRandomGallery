package com.example.fion.randompics;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class SavedImages extends MainActivity{

    private Firebase firebase;
    private ArrayList<String> url = new ArrayList<>();
    private ListView list;

    ArrayList<ImageButton> button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = new ArrayList<>();
        addButtons(button);
        list = (ListView) findViewById(R.id.list);
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://randompics-6fd86.firebaseio.com/");

        this.retrieveImage();
    }

    public void retrieveImage()
    {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getUpdates(DataSnapshot ds)
    {
        for (DataSnapshot data : ds.getChildren())
        {
            URL link = new URL();
            link.setURL(data.getValue(URL.class).getURL());

            url.add(link.getURL());
        }

        if (url.size() > 0)
        {
//            final ArrayAdapter arrayAdapter = new ArrayAdapter(SavedImages.this, android.R.layout.simple_list_item_1, url);
//            list.setAdapter(arrayAdapter);
//            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
//                    Toast.makeText(SavedImages.this, "Opening URL in Browser...", Toast.LENGTH_SHORT).show();
//
//                    Object o = list.getItemAtPosition(position);
//                    String url = o.toString();
//
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(browserIntent);
//                }
//            });

            for (int i = 0; i < url.size(); i++) {
                String link = url.get(i);
                //Log.i("url", " = " + url);
                button.get(i).setTag(link);
                //Log.i("button tag", " = " + button.get(i).getTag());
                Ion.with(button.get(i)).load(link);
            }
        }
        else
        {
            Toast.makeText(SavedImages.this, "No Saved Images", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_empty, menu);
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

}
