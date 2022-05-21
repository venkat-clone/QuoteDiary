package com.android.quotediary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    Fragment homeFragment = new HomeFragment(),
            WallpaperFragment = new WallpaperFragment(),
            QuotesFragment = new QuotesFragment();
    Fragment active = homeFragment;
    FragmentManager fm = getSupportFragmentManager();
    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

//
        navView = findViewById(R.id.nav_view);
//        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_activity_main, homeFragment).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_activity_main, WallpaperFragment).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_activity_main, QuotesFragment).commit();

        navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    setFragment(homeFragment,"1",0);
//                    getSupportFragmentManager().beginTransaction().replace(fm.getBackStackEntryAt(1).getId(), homeFragment).commit();
//                       if(homeFragment==null)
//                           homeFragment = new HomeFragment();
//
//                       if(fragment!=null) fm.beginTransaction().detach(fragment);
//                       else fm.beginTransaction().replace(R.id.nav_host_fragment_activity_main,homeFragment).commit();
//                       fragment = homeFragment;
                    return true;
//                break;
                case R.id.navigation_wallpaper:
                    setFragment(WallpaperFragment,"2",1);
//                    getSupportFragmentManager().beginTransaction().replace(fm.getBackStackEntryAt(1).getId(), WallpaperFragment).commit();
//                        if(WallpaperFragment==null) WallpaperFragment = new WallpaperFragment();
//                        if(fragment!=null) fm.beginTransaction().detach(fragment);
//                        else fm.beginTransaction().replace(R.id.nav_host_fragment_activity_main,WallpaperFragment).commit();
//                        fragment = homeFragment;
                    return true;

//                break;
                case R.id.navigation_quotes:
                    setFragment(QuotesFragment,"3",2);
//                    getSupportFragmentManager().beginTransaction().replace(fm.getBackStackEntryAt(1).getId(), QuotesFragment).commit();
//                        if(QuotesFragment==null) QuotesFragment = new QuotesFragment();
//                        if(fragment!=null) fm.beginTransaction().detach(fragment);
//                        else fm.beginTransaction().replace(R.id.nav_host_fragment_activity_main,QuotesFragment).commit();
//                        fragment = homeFragment;
                    return true;
//                    break;
            }
//                fm.beginTransaction().replace(R.id.nav_host_fragment_activity_main,fragment).commit();
            return false;
        });
//        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_wallpaper, R.id.navigation_quotes)
//                .build();

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.navigateUp(navController,navController.);
//        navController.navigate(R.id.navigation_wallpaper);
//        NavigationUI.setupWithNavController(binding.navView, navController);
        setFragment(homeFragment,"1",0);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},120);
        }
    }







    public void setFragment(Fragment fragment, String tag, int position) {
        if (fragment.isAdded()) {
            fm.beginTransaction().hide(active).show(fragment).commit();
        } else {
            fm.beginTransaction().add(R.id.nav_host_fragment_activity_main, fragment, tag).commit();
        }
        navView.getMenu().getItem(position).setChecked(true);
        active = fragment;
    }





}