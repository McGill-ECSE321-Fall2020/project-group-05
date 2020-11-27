package com.example.visartmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.visartmobile.util.HttpUtils;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        TextView userTitle = (TextView) findViewById(R.id.userName3);
        userTitle.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
    }

    /**
     * This method redirects the user to the main listing page.
     */
    public void browseClicked() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // prevents user from going back to previous activity
        startActivity(mainIntent);
    }

    /**
     * This method is called when the user clicks the button.
     * Calls the browseClicked() method to redirect user.
     *
     * @param view
     */
    public void onBrowseClick(View view) {
        browseClicked();
    }

}