package com.example.visartmobile;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visartmobile.util.HttpUtils;
import com.example.visartmobile.util.UserAuth;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CheckoutActivity extends AppCompatActivity {
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    }

    public void clickedAddress(View view) {
        EditText et=(EditText)findViewById(R.id.addressField);
        et.setVisibility(View.VISIBLE);

    }

    public void clickedPickUp(View view) {
        EditText et=(EditText)findViewById(R.id.addressField);
        et.setVisibility(View.INVISIBLE);
    }

    public void clickedPurchase(View view) {
        String[][] data =
                {
                    {"aIsDelivered", "false"},
                        {"pieceLocation", "AtGallery"},
                        {"aTargetAddress", "TBD"},
                        {"aDeliveryTracker", "TBD"},
                        {"artPieceId", vm.artlisting.artPieces[0].idCode}
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