package com.example.visartmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.visartmobile.util.ArtListing;
import com.example.visartmobile.util.HttpUtils;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    CardViewAdapter adapter;
    RecyclerView cards;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler(Looper.getMainLooper());
        cards = (RecyclerView) findViewById(R.id.cards);

        //setup an auth listener to prevent non logged in users to view art
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() ==  null){
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // prevents user from going back to previous activity
                    startActivity(loginIntent);
                    finish();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);

        //Make call to the API
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
                            populateCardView(listings); //update the UI with the response

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

    /**
     * This method enables the RecyclerView to be updated with the list values from a CallBack (seperate thread)
     *
     * @param ArrayList<ArtListing> listings is the listings to be displayed on the Recycler View
     * **/
    public void populateCardView(ArrayList<ArtListing> listings){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //init the listing with the json data
                ArrayList<ArtListing> allListings = listings;
                adapter = new CardViewAdapter(allListings);
                //setup the onclick listener for every card element
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

    /**
     * This method enables a Toast message to be shown from a callback thread
     *
     * @param String message is the message to be displayed vis Toast
     * **/
    public void showToastFromThread(String message){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast= Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    /**
     * Logs the user out when the log out button is pressed
     *
     * **/
    public void logoutButtonClicked(View view) {
        this.mAuth.signOut();
    }
}