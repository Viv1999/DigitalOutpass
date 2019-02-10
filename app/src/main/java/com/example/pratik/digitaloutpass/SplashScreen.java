package com.example.pratik.digitaloutpass;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity implements LoginStudentFragment.OnFragmentInteractionListener,
        SignupStudentFragment.OnFragmentInteractionListener,
        MyOutpassesFragment.OnFragmentInteractionListener {
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FirebaseUser curUser;
    private static int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();
        if(curUser!=null) {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        }
        else {
            final LoginStudentFragment loginStudentFragment = LoginStudentFragment.newInstance();
            final FragmentManager fragmentManager = getSupportFragmentManager();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(R.id.SSConstraintLayout, loginStudentFragment).commit();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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

