package com.example.pratik.digitaloutpass;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity implements LoginStudentFragment.OnFragmentInteractionListener,
        SignupStudentFragment.OnFragmentInteractionListener,
        MyOutpassesFragment.OnFragmentInteractionListener,
        VerificationFragment.OnFragmentInteractionListener{
    public static  String CHANNEL_ID = "FIREBASE_NOTIFICATION";
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FirebaseUser curUser;
    TextView tvLabel;
    private static int SPLASH_TIME_OUT = 4000;
    DatabaseReference curUserRef;
    public static String CLASS_NAME = "CLASS_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(this);
        Intent intent = getIntent();
        int classId = intent.getIntExtra(CLASS_NAME, -1);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
        }
        tvLabel = findViewById(R.id.tvLabel);
        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();
        if(curUser!=null) {
            curUserRef = FirebaseDatabase.getInstance().getReference("users").child(curUser.getUid());
            curUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String role = dataSnapshot.getValue(User.class).getRole();
                    if(curUser.isEmailVerified()) {
                        if(role.equals(User.STUDENT)) {
                            startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        }
                        else if(role.equals(User.WARDEN)){
                            startActivity(new Intent(SplashScreen.this, WardenActivity.class));
                        }
                        else{
                            startActivity(new Intent(SplashScreen.this, CaretakerActivity.class));
                        }
                        finish();
                    }
                    else{
                        tvLabel.setVisibility(View.GONE);
                        final FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.SSConstraintLayout, VerificationFragment.newInstance()).commit();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else {
            final LoginStudentFragment loginStudentFragment = LoginStudentFragment.newInstance();
            final FragmentManager fragmentManager = getSupportFragmentManager();
            if(classId!=-1){
                tvLabel.setVisibility(View.GONE);
                fragmentManager.beginTransaction().replace(R.id.SSConstraintLayout, loginStudentFragment).commit();
            }
            else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvLabel.setVisibility(View.GONE);
                        fragmentManager.beginTransaction().replace(R.id.SSConstraintLayout, loginStudentFragment).commit();
                    }
                }, SPLASH_TIME_OUT);
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    // the onSave Instance iis needded
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
        super.onSaveInstanceState(outState);
    }
    @Override
    public void switchFragment() {
        Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.SSConstraintLayout);
        if(curFragment instanceof LoginStudentFragment){
            Fragment fragment = SignupStudentFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.SSConstraintLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (curFragment instanceof SignupStudentFragment){
            Fragment fragment = LoginStudentFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.SSConstraintLayout, fragment).addToBackStack(null).commit();
        }
    }
}

