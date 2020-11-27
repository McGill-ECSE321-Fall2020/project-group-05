package com.example.visartmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OrderSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
    }

    /**
     * This method redirects the user to the main listing page.
     */
    public void browseClicked() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    /**
     * This method is called when the user clicks the button.
     * Calls the browseClicked() method to redirect user. 
     * @param view
     */
    public void onBrowseClick(View view) {
        browseClicked();
    }
}