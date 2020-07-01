package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtLoginPass,txtLoginEmail;
    private Button btnLoginLog,btnLoginSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginPass = findViewById(R.id.txtLoginPass);
//        txtLoginPass.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN) {
//
//                    onClick(btnLoginSignup);
//
//                }
//                return false;
//            }
//        });

        setTitle("LOGIN");
        btnLoginSignup = findViewById(R.id.btnSignupLog);
        btnLoginLog = findViewById(R.id.btnLoginLog);
        btnLoginLog.setOnClickListener(this);
        btnLoginSignup.setOnClickListener(this);

        if(ParseUser.getCurrentUser()!=null) {
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnLoginLog:
                ParseUser.logInInBackground(txtLoginEmail.getText().toString(), txtLoginPass.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e ==null){
                            FancyToast.makeText(LoginActivity.this,user.getUsername()+" is Logged in",
                                    Toast.LENGTH_SHORT,
                                    FancyToast.SUCCESS,
                                    true).show();
                            transitionToSocialMediaActivity();
                        }
                    }
                });

                break;
            case R.id.btnSignupLog:
                Intent intent = new Intent(LoginActivity.this,SingUp.class);
                startActivity(intent);
                break;

        }
    }
    private void transitionToSocialMediaActivity(){

             Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
             startActivity(intent);


}
}