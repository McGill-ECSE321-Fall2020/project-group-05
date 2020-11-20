package com.example.visartmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visartmobile.util.HttpUtils;

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
        EditText passBox = findViewById(R.id.loginPass);
//        String randomNumbers = (System.nanoTime() + "");
//        randomNumbers = randomNumbers.substring(randomNumbers.length() - 5);
        String[][] data = {
                {"emailAddress", emailBox.getText().toString()},
                {"password", passBox.getText().toString()}
        };
        HttpUtils.postForm("users/email_login", data, new LoginCallback());
    }

    class LoginCallback implements Callback {

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            System.err.println(e.toString());
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String userID = null;
            try {
                userID = new JSONObject(response.body().string()).getString("userId");
                HttpUtils.get("users/get/" + userID, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        System.err.println(e.toString());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try {
                            JSONObject json = new JSONObject(response.body().string());
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
            } catch (JSONException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_LONG).show();
                    }
                });
                e.printStackTrace();
            }
        }
    }
}
