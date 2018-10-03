package com.example.dsm2018.pickup.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.dsm2018.pickup.R;
import com.example.dsm2018.pickup.adapter.VIewPagerAdapter;
import com.example.dsm2018.pickup.dialog.DeleteAccountDialog;
import com.example.dsm2018.pickup.dialog.LogoutDialog;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    VIewPagerAdapter viewPagerAdapter;
    DrawerLayout drawerLayout;
    View drawerView;
    Button openDrawerButton, searchButton;
    LinearLayout userInformation, accountDelete, logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);
        openDrawerButton = (Button)findViewById(R.id.openDrawerButton);
        userInformation = (LinearLayout)findViewById(R.id.userInformation);
        accountDelete = (LinearLayout)findViewById(R.id.accountDelete);
        logOut = (LinearLayout)findViewById(R.id.logOut);
        searchButton = (Button)findViewById(R.id.searchButton);

        tabLayout.addTab(tabLayout.newTab().setText("파티생성"));
        tabLayout.addTab(tabLayout.newTab().setText("파티목록"));
        tabLayout.addTab(tabLayout.newTab().setText("파티로그"));

        viewPagerAdapter = new VIewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        });

        openDrawerButton.setOnClickListener(v -> drawerLayout.openDrawer(drawerView));

        userInformation.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UserInformationActivity.class);
            startActivity(intent);
        });

        accountDelete.setOnClickListener(v -> new DeleteAccountDialog(MainActivity.this).showDialog());

        logOut.setOnClickListener(v -> new LogoutDialog(MainActivity.this).showDialog());
    }
}
