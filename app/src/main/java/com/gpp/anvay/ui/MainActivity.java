package com.gpp.anvay.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.gpp.anvay.R;
import com.gpp.anvay.ui.fragment.AlertsFragment;
import com.gpp.anvay.ui.fragment.DirectoryFragment;
import com.gpp.anvay.ui.fragment.HomeFragment;
import com.gpp.anvay.ui.fragment.ProfileFragment;
import com.gpp.anvay.ui.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private final Fragment homeFragment = new HomeFragment();
    private final DirectoryFragment directoryFragment = new DirectoryFragment();
    private final SearchFragment searchFragment = new SearchFragment();
    private final AlertsFragment alertsFragment = new AlertsFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();

    private Fragment activeFragment = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavigation);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.fragmentContainer, profileFragment, "5").hide(profileFragment)
                .add(R.id.fragmentContainer, alertsFragment, "4").hide(alertsFragment)
                .add(R.id.fragmentContainer, searchFragment, "3").hide(searchFragment)
                .add(R.id.fragmentContainer, directoryFragment, "2").hide(directoryFragment)
                .add(R.id.fragmentContainer, homeFragment, "1")
                .commit();

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    switchFragment(homeFragment);
                    return true;
                } else if (itemId == R.id.nav_directory) {
                    switchFragment(directoryFragment);
                    return true;
                } else if (itemId == R.id.nav_search) {
                    switchFragment(searchFragment);
                    return true;
                } else if (itemId == R.id.nav_alerts) {
                    switchFragment(alertsFragment);
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    switchFragment(profileFragment);
                    return true;
                }
                return false;
            }
        });
    }

    private void switchFragment(Fragment target) {
        if (target != activeFragment) {
            getSupportFragmentManager().beginTransaction()
                    .hide(activeFragment)
                    .show(target)
                    .commit();
            activeFragment = target;
        }
    }

    public void navigateToTab(int navItemId, Bundle args) {
        if (navItemId == R.id.nav_directory && args != null) {
            if (args.containsKey("selected_building_id")) {
                directoryFragment.setSelectedBuilding(args.getString("selected_building_id"));
            }
            if (args.containsKey("selected_category")) {
                directoryFragment.setCategoryFilter(args.getString("selected_category"));
            }
        } else if (navItemId == R.id.nav_search && args != null) {
            if (args.containsKey("search_building_id")) {
                searchFragment.setActiveBuildingFilter(args.getString("search_building_id"));
            }
            if (args.containsKey("search_query")) {
                searchFragment.setSearchQuery(args.getString("search_query"));
            }
        }
        bottomNav.setSelectedItemId(navItemId);
    }
}
