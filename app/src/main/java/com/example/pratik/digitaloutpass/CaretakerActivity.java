package com.example.pratik.digitaloutpass;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.pratik.digitaloutpass.SplashScreen.CLASS_NAME;

public class CaretakerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MyStudentsFragment.OnFragmentInteractionListener,
        OutpassRequestFragment.OnFragmentInteractionListener,
        AllOutpassesFragment.OnFragmentInteractionListener,
        EditProfileFragment.OnFragmentInteractionListener {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    FirebaseAuth mAuth;

    DatabaseReference usersRef;
    DatabaseReference curUserRef;
    FirebaseUser currentUser;

    ImageView dpCaretakerNavHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        curUserRef = usersRef.child(currentUser.getUid());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.activity_caretaker_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_caretaker);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        final TextView tvName = (TextView) headerView.findViewById(R.id.tvNameWardenCaretakerNavHeader);
        final TextView tvEmail = (TextView) headerView.findViewById(R.id.tvEmailWardenCaretakerNavHeader);
        dpCaretakerNavHeader = (ImageView) headerView.findViewById(R.id.dpWardenCaretakerNavHeader);

        curUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tvName.setText(dataSnapshot.child("name").getValue(String.class));
                tvEmail.setText(dataSnapshot.child("email").getValue(String.class));
                if (dataSnapshot.child("imageUrl") != null && dataSnapshot.child("imageUrl").getValue(String.class) != null) {

                    Glide.with(CaretakerActivity.this)
                            .load(dataSnapshot.child("imageUrl").getValue(String.class).toString())
                            .into(dpCaretakerNavHeader);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        OutpassRequestFragment outpassRequestFragment = OutpassRequestFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_caretaker_linear, outpassRequestFragment).commit();
//        HostelOutpassesFragment pendingOutpasses = HostelOutpassesFragment.newInstance();
//        getSupportFragmentManager().beginTransaction().replace(R.id.content_caretaker_linear, pendingOutpasses).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_caretaker, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.pendingOutpasses:
                OutpassRequestFragment outpassRequestFragment = OutpassRequestFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_caretaker_linear, outpassRequestFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
            case R.id.myStudentsItem:

                MyStudentsFragment myStrudentsFragment = MyStudentsFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_caretaker_linear, myStrudentsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                //load fragment here
                break;
            case R.id.navEditCaretaker:
                EditProfileFragment editProfileFragment = EditProfileFragment.newInstance(FirebaseAuth.getInstance().getCurrentUser());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_caretaker_linear, editProfileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                break;
            case R.id.logout_warden_nav:
                mAuth.signOut();
                finish();
                startActivity(new Intent(CaretakerActivity.this, SplashScreen.class).putExtra("CLASS_NAME", 3));
                break;

            case R.id.allOutpassesItem:
                AllOutpassesFragment allOutpassesFragment = AllOutpassesFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_caretaker_linear, allOutpassesFragment).commit();
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_caretaker_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_caretaker:
                mAuth.signOut();
                startActivity(new Intent(this, SplashScreen.class).putExtra(CLASS_NAME, 3));
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
