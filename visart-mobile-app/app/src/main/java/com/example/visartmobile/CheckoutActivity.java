package com.example.visartmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.visartmobile.util.ArtListing;
import com.example.visartmobile.util.HttpUtils;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CheckoutActivity extends AppCompatActivity {
    private Handler mHandler;
    private String listingId;
    private String artPieceId;
    private String listingPrice;
    private String listingArtist;
    private String typedAddress;
    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() ==  null){
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // prevents user from going back to previous activity
                    startActivity(loginIntent);
                    finish();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);

        Bundle extras = getIntent().getExtras();
        listingId = extras.getString("idCodeListing");

        mHandler = new Handler(Looper.getMainLooper());

    }

    public void clickedAddress(View view) {
        EditText et=(EditText)findViewById(R.id.addressField);
        et.setVisibility(View.VISIBLE);
        typedAddress = ((EditText) findViewById(R.id.addressField)).getText().toString();

    }

    public void clickedPickUp(View view) {
        EditText et=(EditText)findViewById(R.id.addressField);
        et.setVisibility(View.INVISIBLE);
        typedAddress = "TBD";
    }

    public void clickedPurchase(View view) {
        getPurchaseListingInfo();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String[][] dataAO =
                {
                    {"aIsDelivered", "false"},
                        {"pieceLocation", "AtGallery"},
                        {"aTargetAddress", typedAddress}, //do address
                        {"aDeliveryTracker", "TBD"},
                        {"artPieceId", artPieceId}
                };
        try {
            HttpUtils.postForm("/artorder/create/", dataAO, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    showToastFromThread("Could not create your order!");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                        JSONObject jsonOrder = new JSONObject(response.body().string());
                        String orderId = jsonOrder.getString("idCode");
                        String[][] dataTicket = {
                                {"aIsPaymentConfirmed", "false"},
                                {"aPaymentAmount", listingPrice.toString()},
                                {"aOrder", orderId},
                                {"aCustomer", userId},
                                {"aArtist", listingArtist}
                         };



                        } catch (Exception e) {
                            showToastFromThread("Could not create a ticket");
                        }

                    } else {
                        showToastFromThread("Could not purchase item!");
                    }
                }
            });
        } catch (Exception ex) {

        }
    }
    public void getPurchaseListingInfo() {
        try {
            HttpUtils.get("artlisting/get/" + listingId, new Callback() {
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
                            artPieceId = listing.getArtPieceId();
                            listingPrice = (String.valueOf(listing.getPrice()));
                            listingArtist = listing.getArtistId();

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

    public void showToastFromThread(String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}