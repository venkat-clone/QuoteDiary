package com.android.quotediary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.quotediary.databinding.ActivityMainBinding;
import com.android.quotediary.ui.home.HomeFragment;
import com.android.quotediary.ui.quotes.QuotesFragment;
import com.android.quotediary.ui.wallpaper.WallpaperFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    Fragment homeFragment,WallpaperFragment,QuotesFragment;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager fm = getSupportFragmentManager();

        BottomNavigationView navView = findViewById(R.id.nav_view);
//
//        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.navigation_home:
//                       if(homeFragment==null)
//                           homeFragment = new HomeFragment();
//
//                       if(fragment!=null) fm.beginTransaction().detach(fragment);
//                       else fm.beginTransaction().replace(R.id.nav_host_fragment_activity_main,homeFragment).commit();
//                       fragment = homeFragment;
//                        break;
//                    case R.id.navigation_wallpaper:
//                        if(WallpaperFragment==null) WallpaperFragment = new WallpaperFragment();
//                        if(fragment!=null) fm.beginTransaction().detach(fragment);
//                        else fm.beginTransaction().replace(R.id.nav_host_fragment_activity_main,WallpaperFragment).commit();
//                        fragment = homeFragment;
//                        break;
//                    case R.id.navigation_quotes:
//                        if(QuotesFragment==null) QuotesFragment = new QuotesFragment();
//                        if(fragment!=null) fm.beginTransaction().detach(fragment);
//                        else fm.beginTransaction().replace(R.id.nav_host_fragment_activity_main,QuotesFragment).commit();
//                        fragment = homeFragment;
//                        break;
//                }
////                fm.beginTransaction().replace(R.id.nav_host_fragment_activity_main,fragment).commit();
//                return false;
//            }
//        });
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_wallpaper, R.id.navigation_quotes)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.navigateUp(navController,navController.);
//        navController.navigate(R.id.navigation_wallpaper);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}