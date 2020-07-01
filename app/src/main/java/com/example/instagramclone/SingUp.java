package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SingUp extends AppCompatActivity {

    private EditText edtUserLogin, edtPassLogin, edtUserSignup, edtPassSignup;
    private Button btnLogin,btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtPassLogin = findViewById(R.id.edtPassLogin);
        edtPassSignup = findViewById(R.id.edtPassSignup);
        edtUserLogin = findViewById(R.id.edtUserLogin);
        edtUserSignup = findViewById(R.id.edtUserSignup);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignup);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ParseUser parseUser = new ParseUser();
                parseUser.setUsername(edtUserSignup.getText().toString());
                parseUser.setPassword(edtPassSignup.getText().toString());

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(SingUp.this,parseUser.get("username")+" is signed up successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                        }else{
                            FancyToast.makeText(SingUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                        }
                    }
                });

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(edtUserLogin.getText().toString(), edtPassLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user !=null && e==null){
                            FancyToast.makeText(SingUp.this,user.get("username")+" is Logged in successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                            Intent intent = new Intent(SingUp.this,WelcomeActivity.class);
                            startActivity(intent);

                        }else {
                            FancyToast.makeText(SingUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                        }
                    }
                });

            }
        });

    }


}