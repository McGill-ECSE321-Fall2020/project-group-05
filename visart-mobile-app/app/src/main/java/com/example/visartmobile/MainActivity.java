package com.example.visartmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        ArrayList<String> cardNames = new ArrayList<>();
        cardNames.add("Horse");
        cardNames.add("Cow");
        cardNames.add("Camel");
        cardNames.add("Sheep");
        cardNames.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.cards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CardViewAdapter(this, cardNames);
        adapter.setClickListener((CardViewAdapter.ItemClickListener) this);
        recyclerView.setAdapter(adapter);
    }


    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}