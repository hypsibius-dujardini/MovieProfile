package com.example.movieprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

/* Activity for Task #3 (Master/Detail Flow) */
public class MasterDetailFlowActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private boolean movieFragmentOpen = false; // Is detail fragment currently displayed?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail_flow);

        // Set up Action Bar and Drawer
        Toolbar actionBar = ActivitySetupUtilities.setUpActionBar(this);
        drawerLayout = ActivitySetupUtilities.setUpDrawer(this, this, actionBar);

        // Add the ListView fragment to the container
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.list_container, new MovieListFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_menu, menu);
        return true;
    }

    @Override
    /* Navigation for ActionBar items */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (onItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    /* Navigation for Drawer items */
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (onItemSelected(item)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    /* Handles navigation to other activities */
    private boolean onItemSelected(@NonNull MenuItem item) {
        final int profileActionId = R.id.profile_action;
        final int movieDetailsActionId = R.id.movie_details_action;
        final int movieListActionId = R.id.movie_list_action;

        switch (item.getItemId()) {
            case profileActionId:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case movieDetailsActionId:
                startActivity(new Intent(this, ViewPagerActivity.class));
                finish();
                return false;
            case movieListActionId:
                // If detail fragment is currently displayed, go back to ListView fragment
                if (movieFragmentOpen) onBackPressed();
                break;
            default: return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        setMovieFragmentOpen(false);
        super.onBackPressed();
    }

    public void setMovieFragmentOpen(boolean open) { movieFragmentOpen = open; }
}