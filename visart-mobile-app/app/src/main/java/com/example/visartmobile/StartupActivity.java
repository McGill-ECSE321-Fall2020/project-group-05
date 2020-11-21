package com.example.visartmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visartmobile.util.ArtListing;
import com.example.visartmobile.util.HttpUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void onLoginNavClick(View view) {
        goToLoginPage();
    }

    public void onMainNavClick(View view) {
        goToMainListings();
    }

    public void onGetListings(View view) {
        HttpUtils.get("artlisting/get_all", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONArray arr = new JSONArray(response.body().string());
                    ArrayList<ArtListing> list = ArtListing.parseJSONArray(arr);

//                    JSONObject obj = new JSONObject(response.body().string());
//                    ArtListing al = ArtListing.parseJSON(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}