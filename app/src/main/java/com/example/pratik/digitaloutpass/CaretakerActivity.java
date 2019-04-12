package com.example.pratik.digitaloutpass;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class CaretakerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MyStrudentsFragment.OnFragmentInteractionListener{


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.activity_caretaker_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_caretaker);
        navigationView.setNavigationItemSelectedListener(this);

        PendingOutpasses pendingOutpasses = PendingOutpasses.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_caretaker_linear, pendingOutpasses).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_caretaker, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.pendingOutpassesItem:
                PendingOutpasses pendingOutpasses = PendingOutpasses.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_caretaker_linear, pendingOutpasses).commit();
                break;
            case R.id.myStudentsItem:

                MyStrudentsFragment myStrudentsFragment = MyStrudentsFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_caretaker_linear, myStrudentsFragment)
                        .commit();
                //load fragment here
                break;
            case R.id.logout_warden_nav:
                mAuth.signOut();
                finish();
                startActivity(new Intent(CaretakerActivity.this, SplashScreen.class).putExtra("CLASS_NAME", 3));
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_caretaker_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
