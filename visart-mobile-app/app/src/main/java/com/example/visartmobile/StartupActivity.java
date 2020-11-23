package com.example.visartmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visartmobile.util.ArtListing;
import com.example.visartmobile.util.HttpUtils;
import com.example.visartmobile.util.UserAuth;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StartupActivity extends AppCompatActivity {

    CardViewAdapter adapter;
    RecyclerView cards;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        mHandler = new Handler(Looper.getMainLooper());

    }

    public void goToMainListings() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void goToLoginPage() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void goToCheckout() {
        Intent checkoutIntent = new Intent(this, CheckoutActivity.class);
        startActivity(checkoutIntent);
    }

    public void onLoginNavClick(View view) {
        goToLoginPage();
    }

    public void onMainNavClick(View view) {
        goToMainListings();
    }

    public void onCheckoutNavClick(View view) { goToCheckout(); }

    public void onTestMethod(View view) {
        Toast.makeText(StartupActivity.this, "I am a test method!", Toast.LENGTH_LONG).show();
    }
}