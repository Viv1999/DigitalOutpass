package com.example.pratik.digitaloutpass;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LoginStudentFragment.OnFragmentInteractionListener,
        SignupStudentFragment.OnFragmentInteractionListener,
        MyOutpassesFragment.OnFragmentInteractionListener,
        EditProfileFragment.OnFragmentInteractionListener,
        View.OnClickListener{
    public static final String CUR_OUTPASS_ID_KEY = "CUR_OUTPASS_ID";
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FirebaseUser curUser;
    private static int SPLASH_TIME_OUT = 400;
    FloatingActionButton fab;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference outpassesRef = FirebaseDatabase.getInstance().getReference("outpasses");
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference curOutpassIdRef = FirebaseDatabase.getInstance().getReference("curOutpassId");
    SharedPreferences sharedPreferences;
    DatabaseReference myOutpassesRef;
    ArrayList<String> myOutpasses;
    private String hostel;
    DatabaseReference curUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        sharedPreferences = this.getSharedPreferences("com.example.pratik.digitaloutpass", MODE_PRIVATE);
        Outpass.curId = sharedPreferences.getInt(CUR_OUTPASS_ID_KEY, 0);
        myOutpasses = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();
        curUserRef = usersRef.child(curUser.getUid());
        myOutpassesRef = usersRef.child(curUser.getUid()).child("myOutpasses");

        curUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hostel = dataSnapshot.getValue(Student.class).getHostel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myOutpassesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myOutpasses.clear();
                for (DataSnapshot myOutpass: dataSnapshot.getChildren()){
                    myOutpasses.add(myOutpass.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        curOutpassIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sharedPreferences.edit().putInt(CUR_OUTPASS_ID_KEY, dataSnapshot.getValue(Integer.class));
                Outpass.curId = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //if(curUser==null){
       // }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseUser curUser = mAuth.getCurrentUser();
        fab  = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        if (curUser == null) {
            fab.hide();
            SignupStudentFragment signupStudentFragment = SignupStudentFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.content_main_relative, signupStudentFragment).commit();
        }
        else{
            MyOutpassesFragment outpassesFragment = MyOutpassesFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.content_main_relative, outpassesFragment).commit();
        }

        //setContentView(R.layout.nav_header_main);






        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView tvName = (TextView)headerView.findViewById(R.id.tvName);
        final TextView tvEmail = (TextView)headerView.findViewById(R.id.tvEmail);
        curUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tvName.setText(dataSnapshot.child("name").getValue(String.class));
                tvEmail.setText(dataSnapshot.child("email").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //LoginStudentFragment fragment = LoginStudentFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                mAuth.signOut();
                Toast.makeText(this, "User sigout out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SplashScreen.class).putExtra("CLASS_NAME", 1));
                finish();
                return true;
            case R.id.action_settings:
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all_outpasses) {
            MyOutpassesFragment outpassesFragment = MyOutpassesFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.content_main_relative, outpassesFragment).commit();

        }

        else if(id == R.id.nav_edit_stu){

            EditProfileFragment editProfileFragment = EditProfileFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.content_main_relative,editProfileFragment).commit();
            //open edit profile fragment here
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void switchFragment() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_main_relative);
        if(fragment instanceof LoginStudentFragment){
            SignupStudentFragment signupStudentFragment = SignupStudentFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.content_main_relative, signupStudentFragment).commit();
        }
        else if(fragment instanceof SignupStudentFragment){
            LoginStudentFragment loginStudentFragment = LoginStudentFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.content_main_relative, loginStudentFragment).commit();
        }
    }

    public void gotoVerify() {

        VerificationFragment verificationFragment = VerificationFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.content_main_relative, verificationFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab:
                createNewOutpass();
//                startActivity(new Intent(MainActivity.this, WardenActivity.class));
                break;
        }
    }

    private void createNewOutpass() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.new_outpass,null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(v);
        dialogBuilder.setTitle("Create new outpass");
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        TextView tvFrom;
        TextView tvLeaveDate;
        TextView tvReturnDate;
        TextView tvTo;
        tvFrom = v.findViewById(R.id.tvFromCardOutpass);
        tvTo = v.findViewById(R.id.tvToCardOutpass);
        tvLeaveDate = v.findViewById(R.id.tvLeaveDateCardOutpass);
        tvReturnDate = v.findViewById(R.id.tvRetDateCardOutpass);
        final EditText etTo = v.findViewById(R.id.etToCardOutpass);
        final EditText etFrom = v.findViewById(R.id.etFromCardOutpass);
        final EditText etLeaveDate = v.findViewById(R.id.etLeaveDateCardOutpass);
        final EditText etReturnDate = v.findViewById(R.id.etReturnDateCardOutpass);
        etReturnDate.setKeyListener(null);
        etLeaveDate.setKeyListener(null);
        etLeaveDate.requestFocus();
        final Calendar leaveDateCal = Calendar.getInstance();
        final Calendar returnDateCal = Calendar.getInstance();
        Button bCreateOutpass = v.findViewById(R.id.bCreateOutpass);
        bCreateOutpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = etTo.getText().toString().trim();
                String from  = etFrom.getText().toString().trim();
                Date leaveDate = leaveDateCal.getTime();
                Date returnDate = returnDateCal.getTime();
                Outpass outpass = new Outpass(curUser.getUid(), to, from, leaveDate, returnDate, hostel);
                myOutpasses.add(outpass.getId()+"");
                myOutpassesRef.setValue(myOutpasses);
                outpassesRef.child(outpass.getId()+"").setValue(outpass);
                curOutpassIdRef.setValue(new Integer(Outpass.curId));
                dialog.dismiss();
            }
        });
        etLeaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = DatePickerFragment.newInstance(etLeaveDate, leaveDateCal);
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });
        etReturnDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(etReturnDate, returnDateCal);
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

    }

}
