package com.example.visartmobile;

import android.os.Bundle;
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
        UserAuth.loginGetUser(emailBox.getText().toString(), passBox.getText().toString(), true, (user) -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "Login Successful, welcome: " + user.getDisplayname(), Toast.LENGTH_LONG).show();
                }
            });
        }, (failureType) -> {
            System.out.println(failureType.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
