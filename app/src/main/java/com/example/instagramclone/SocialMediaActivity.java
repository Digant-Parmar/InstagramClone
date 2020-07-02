package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class SocialMediaActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdaptor tabAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        setTitle("Social Media App!!");

        toolBar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolBar);

        viewPager = findViewById(R.id.viewPager);
        tabAdaptor = new TabAdaptor(getSupportFragmentManager(),1);
        viewPager.setAdapter(tabAdaptor);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager,false);

    }
}