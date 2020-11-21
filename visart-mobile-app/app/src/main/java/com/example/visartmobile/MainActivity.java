package com.example.visartmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.visartmobile.util.ArtListing;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private boolean isLoggedIn = true;
    CardViewAdapter adapter;
    RecyclerView cards;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler(Looper.getMainLooper());

        if (isLoggedIn == false) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        // data to populate the RecyclerView with
        ArrayList<ArtListing> cardTitles = new ArrayList<ArtListing>();
        for(int i = 0; i< 10; i++){
           ArtListing listing = new ArtListing();
           listing.setTitle(i+"");
           listing.setDescription(i + "");
           listing.setArtistDisplayname(i + "");
           String imageUri = "https://firebasestorage.googleapis.com/v0/b/visartapplication.appspot.com/o/postedImages%2Fd0872f7b-9147-4bd6-b8e5-64db18aa9e8d.png?alt=media&token=86e847fe-084e-4b06-910d-5a6d8bea6e6e";
           listing.setPostImages(imageUri);
           cardTitles.add(listing);
        }

        cards = (RecyclerView) findViewById(R.id.cards);

        populateCardView(cardTitles);



    }

    public void goToListingPage(int position){
        Toast.makeText(this, position+ " was clicked!", Toast.LENGTH_SHORT).show();
    };

    public void populateCardView(ArrayList<ArtListing> cardTitles){
        adapter = new CardViewAdapter(cardTitles);
        adapter.setOnItemClickListener(new CardViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                goToListingPage(position);
            }
        });
        cards.setAdapter(adapter);
        cards.setLayoutManager(new LinearLayoutManager(this));
    }


//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//    }
}