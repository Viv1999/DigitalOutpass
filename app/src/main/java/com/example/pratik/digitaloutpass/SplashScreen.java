package com.example.pratik.digitaloutpass;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity implements LoginStudentFragment.OnFragmentInteractionListener,
        SignupStudentFragment.OnFragmentInteractionListener,
        MyOutpassesFragment.OnFragmentInteractionListener,
        VerificationFragment.OnFragmentInteractionListener{
    private FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FirebaseUser curUser;
    TextView tvLabel;
    private static int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent intent = getIntent();
        int classId = intent.getIntExtra("CLASS_NAME", -1);

        tvLabel = findViewById(R.id.tvLabel);
        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();
        if(curUser!=null) {
            if(curUser.isEmailVerified()) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
            else{
                tvLabel.setVisibility(View.GONE);
                final FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.SSConstraintLayout, VerificationFragment.newInstance()).commit();

            }
        }
        else {
            final LoginStudentFragment loginStudentFragment = LoginStudentFragment.newInstance();
            final FragmentManager fragmentManager = getSupportFragmentManager();
            if(intent==null || classId==1){
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

