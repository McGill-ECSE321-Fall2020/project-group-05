package com.example.visartmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String PLACEHOLDER_PROFILE_PIC = "https://firebasestorage.googleapis.com/v0/b/visartapplication.appspot.com/o/profile-placeholder.png?alt=media&token=8c962ff5-4177-4317-81ff-d6d54eaaf2f7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginButtonClicked(View view) {
        EditText emailBox = findViewById(R.id.loginEmail);
        String randomNumbers = (System.nanoTime() + "");
        randomNumbers = randomNumbers.substring(randomNumbers.length() - 5);
        String[][] data = {
                {"emailAddress", randomNumbers + "bob@email.com"},
                {"displayname", randomNumbers + "TheRy"},
                {"username", randomNumbers + "TheRy"},
                {"password", "password"},
                {"profilePicLink", PLACEHOLDER_PROFILE_PIC},
                {"profileDescription", "I describe myself here."},
        };
        HttpUtils.postForm("users/create", data, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(LoginActivity.this, "Email or Password Incorrect ", Toast.LENGTH_LONG).show();
                System.err.println(e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JSONObject json = null;
                try {
                    String resp = response.body().string();
                    json = new JSONObject(resp);

                    System.out.println(json.toString());
                    final String name = json.getString("username");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Login Successful, Welcome " + name, Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}