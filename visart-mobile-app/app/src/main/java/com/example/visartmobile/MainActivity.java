package com.example.visartmobile;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private boolean isLoggedIn = true;
    CardViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (isLoggedIn == false) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        // data to populate the RecyclerView with
        ArrayList<ArtListing> cardTitles = new ArrayList<ArtListing>();
        for(int i = 0; i< 10; i++){
           ArtListing listing = new ArtListing();
           listing.setTitle(i+"");
           cardTitles.add(listing);
        }

        System.out.println("list size: " + cardTitles.size());
        RecyclerView cards = (RecyclerView) findViewById(R.id.cards);

        CardViewAdapter adapter = new CardViewAdapter(cardTitles);
        System.out.println("I am here");
        cards.setAdapter(adapter);
        cards.setLayoutManager(new LinearLayoutManager(this));


    }


//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//    }
}