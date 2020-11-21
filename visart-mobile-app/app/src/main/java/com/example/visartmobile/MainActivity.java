package com.example.visartmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.visartmobile.util.ArtListing;
import com.example.visartmobile.util.HttpUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
        cards = (RecyclerView) findViewById(R.id.cards);

        if (isLoggedIn == false) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        try{
            HttpUtils.get("artlisting/get_all", new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    showToastFromThread("Oops, Not Connected To Database!");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        System.out.println("Call sucessful");

                        try {
                            JSONArray arr = new JSONArray(response.body().string());
                            ArrayList<ArtListing> listings = ArtListing.parseJSONArray(arr);
                            populateCardView(listings);

                        } catch (Exception e) {
                            System.out.println("Error creating JSON object: " + e.getMessage());
                        }

                    } else{
                        System.out.println("error occured");
                    }
                }
            });

        } catch (Exception ex){
            System.out.println("error occured trying to call");
        }



    }

    public void goToListingPage(ArtListing listing){
        Intent intent = new Intent(this, ListingActivity.class);
        intent.putExtra("listingId", listing.getIdCode());
        startActivity(intent);
    };

    public void populateCardView(ArrayList<ArtListing> listings){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("Got to the printer");
                //init the listing with the json data
                adapter = new CardViewAdapter(listings);
                adapter.setOnItemClickListener(new CardViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        goToListingPage(listings.get(position));
                    }
                });
                cards.setAdapter(adapter);
                cards.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });
    }

    public void showToastFromThread(String message){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast= Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}