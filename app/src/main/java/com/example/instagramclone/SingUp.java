package com.example.instagramclone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SingUp extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmail, txtPass, txtUser;
    private Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Instagram SignUP");

        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPassword);
        txtPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN) {

                    onClick(btnSignUp);

                }
                return false;
            }
        });
        txtUser = findViewById(R.id.txtUser);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignup);



        if (ParseUser.getCurrentUser() != null) {
           //ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch ((v.getId())) {


            case R.id.btnSignup:
                if(txtEmail.getText().toString().equals("") || txtUser.getText().toString().equals("") || txtPass.getText().toString().equals("")) {

                    FancyToast.makeText(SingUp.this, "Empty values are not allowded", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();



                }else {
                    final ParseUser parseUser = new ParseUser();
                    parseUser.setEmail(txtEmail.getText().toString());
                    parseUser.setUsername(txtUser.getText().toString());
                    parseUser.setPassword(txtPass.getText().toString());

                    //    setProgressDialog();
                    //     FancyToast.makeText(SingUp.this," working", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();


                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SingUp.this, parseUser.getUsername() + " is Signed up", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            } else {
                                FancyToast.makeText(SingUp.this, "There is  an error " + e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        }
                    });
                    transitionToSocialMediaActivity();
                }


                break;
            case R.id.btnLogin:

                Intent intent = new Intent(SingUp.this, LoginActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void transitionToSocialMediaActivity(){

        Intent intent = new Intent(SingUp.this, SocialMediaActivity.class);
        startActivity(intent);


    }

    public void rootLayoutTapped(View view) {
        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch ( Exception e){
            e.printStackTrace();

        }
    }

}