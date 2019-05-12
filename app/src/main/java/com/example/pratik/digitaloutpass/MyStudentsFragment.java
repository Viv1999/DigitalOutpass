package com.example.pratik.digitaloutpass;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyStudentsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyStudentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyStudentsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    MyStudentsAdapter mystudentAdapter;

    List<MyStudent> myStudentList;
    DatabaseReference userDatabase;
    FirebaseAuth mAuth;
    DatabaseReference hostelref;
    DatabaseReference wardenref;
    String hostel;
    ArrayList<String> hostelStudents;


    public MyStudentsFragment() {
        // Required empty public constructor
    }

        public static MyStudentsFragment newInstance() {
        MyStudentsFragment fragment = new MyStudentsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myStudentList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");
        hostelref = FirebaseDatabase.getInstance().getReference("hostels");
        wardenref = FirebaseDatabase.getInstance().getReference("users/"+mAuth.getCurrentUser().getUid());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_strudents, container, false);
        // Inflate the layout for this fragment

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final FirebaseUser user = mAuth.getCurrentUser();



        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                hostel = dataSnapshot.child(user.getUid()).child("hostel").getValue(String.class);
                Query query = userDatabase.orderByChild("hostel").equalTo(hostel);
                //Query query1 = query.orderByChild("role").equalTo("STUDENT");

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){


                            String imgUrl = dataSnapshot1.child("imageUrl").getValue(String.class);
                            Student student = dataSnapshot1.getValue(Student.class);
                            if(!student.role.equals("STUDENT") ){
                                continue;
                            }
                            MyStudent mystudent = new MyStudent(student.getName(),student.enrollNo,student.branch,student.phoneNo,imgUrl);
                            myStudentList.add(mystudent);

                            mystudentAdapter = new MyStudentsAdapter(getActivity(),myStudentList);
                            recyclerView.setAdapter(mystudentAdapter);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








//        wardenref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                hostel = dataSnapshot.child("hostel").getValue(String.class);
//                hostelref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        hostelStudents = dataSnapshot.child("hostel").getValue(ArrayList.class);
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
