package com.shashank.platform.loginui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shashank.platform.loginui.Fragment.Customerlist;
import com.shashank.platform.loginui.Fragment.Home;
import com.shashank.platform.loginui.Fragment.Profile;
import com.shashank.platform.loginui.R;

public class Bottommenu extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ActionBar toolbar;
    LinearLayout lin1,lin2;
    GridView simpleGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottommenu);
        toolbar = getSupportActionBar();

        //loading the default fragment
        loadFragment(new Home());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        toolbar.setTitle("Upload");
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_shop:
                toolbar.setTitle("Customer");
                fragment = new Customerlist();
                break;

            case R.id.navigation_gifts:
                fragment = new Home();
                toolbar.setTitle("Upload");
                break;

            case R.id.navigation_profile:
                fragment = new Profile();
                toolbar.setTitle("Profile");
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}