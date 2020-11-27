package com.example.visartmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visartmobile.util.ArtListing;
import com.example.visartmobile.util.HttpUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ListingActivity extends AppCompatActivity {

    private String ID_CODE;
    public TextView cardTitle;
    public ImageView postImage;
    public TextView listingUsername;
    public TextView listingDescription;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        ID_CODE = getIntent().getStringExtra("listingId");

        mHandler = new Handler(Looper.getMainLooper());

        cardTitle = (TextView) findViewById(R.id.card_name2);
        postImage = (ImageView) findViewById(R.id.post_image2);
        listingUsername = (TextView) findViewById(R.id.listingUsername2);
        listingDescription = (TextView) findViewById(R.id.listingDescription2);

        try {
            HttpUtils.get("artlisting/get/" + ID_CODE, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    showToastFromThread("Database failed to connect");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        System.out.println("Call sucessful");

                        try {
                            JSONObject json = new JSONObject(response.body().string());
                            ArtListing listing = ArtListing.parseJSON(json);
                            setListingInfo(listing);

                        } catch (Exception e) {
                            System.out.println("Error creating JSON object: " + e.getMessage());
                        }

                    } else {
                        System.out.println("error occured");
                    }
                }
            });
        } catch (Exception ex) {

        }


    }

    public void setListingInfo(ArtListing listing) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                cardTitle.setText(listing.getTitle() + ", $" + listing.getPrice());
                listingDescription.setText(listing.getDescription());
                listingUsername.setText((listing.getArtistDisplayname()));
                if (listing.getPostImages().length > 0)
                    Picasso.with(getApplicationContext()).load(listing.getPostImages()[0]).fit().into(postImage);

            }
        });
    }

    public void showToastFromThread(String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void buyArt() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (userId == null) {
            showToastFromThread("Please log in or sign up");
            favoriteClicked();
        } else {
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putExtra("idCodeListing", ID_CODE);
            startActivity(intent);
        }
    }


    public void pinArt(View view) {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (userId == null) {
            showToastFromThread("Please log in or sign up");
            favoriteClicked();
        } else {
            String[][] data = {
                    {"listingIdCode", ID_CODE}
            };

            try {
                HttpUtils.postForm("customers/add_favorite_listing/" + userId, data, new Callback() {

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        showToastFromThread("Could not add to favorites!");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            showToastFromThread("Successfully added into favorites");

                            try {

                            } catch (Exception e) {

                            }

                        } else {
                            showToastFromThread("Could not add into favorites");
                        }
                    }
                });
            } catch (Exception ex) {

            }

        }
    }

    public void favoriteClicked() {
        Intent mainIntent = new Intent(this, LoginActivity.class);
        startActivity(mainIntent);
    }
}




