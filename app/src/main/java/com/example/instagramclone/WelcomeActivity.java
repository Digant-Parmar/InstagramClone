package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnLogOut = findViewById(R.id.btnLogOut);

        TextView txtWelcome = findViewById(R.id.txtWelcome);

        txtWelcome.setText("Welcome "+ParseUser.getCurrentUser().get("username"));
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.getCurrentUser().logOut();
                finish();
            }
        });
    }
}