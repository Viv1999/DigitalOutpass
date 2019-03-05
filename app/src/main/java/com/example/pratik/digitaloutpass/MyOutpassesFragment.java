package com.example.pratik.digitaloutpass;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyOutpassesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyOutpassesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOutpassesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView rView;
    DatabaseReference database;
    ArrayList<Outpass> outpasses;
    TextView tvNoOutpasses;
    DatabaseReference outpassesRef;
    FirebaseUser curUser;

    public MyOutpassesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyOutpassesFragment.
     */

    public static MyOutpassesFragment newInstance() {
        MyOutpassesFragment fragment = new MyOutpassesFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance().getReference();
        outpassesRef = FirebaseDatabase.getInstance().getReference("outpasses");
        outpasses = new ArrayList<>();
        curUser = FirebaseAuth.getInstance().getCurrentUser();
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_outpasses, container, false);
        rView = v.findViewById(R.id.rvMyOutpasses);
        tvNoOutpasses = v.findViewById(R.id.tvNoOutpasses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rView.setLayoutManager(layoutManager);
        final OutpassesRViewAdapter adapter = new OutpassesRViewAdapter(getContext(), outpasses);
        rView.setAdapter(adapter);
        if(outpasses.isEmpty()){
            tvNoOutpasses.setVisibility(View.VISIBLE);
            rView.setVisibility(View.GONE);
        }
        else{
            tvNoOutpasses.setVisibility(View.GONE);
            rView.setVisibility(View.VISIBLE);
        }
        outpassesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                outpasses.clear();
                for(DataSnapshot outpassesSnapshot : dataSnapshot.getChildren()){
                    Outpass curOutpass = outpassesSnapshot.getValue(Outpass.class);
                    Log.d("datasnapshot", curOutpass.toString());
                    if(curOutpass.personName.equals(curUser.getUid())){
                        Log.d("Outpass", "onDataChange: outpass matched" + curOutpass.toString());
                        outpasses.add(curOutpass);
                    }
                }

                if(outpasses.isEmpty()){
                    tvNoOutpasses.setVisibility(View.VISIBLE);
                    rView.setVisibility(View.GONE);
                }
                else{
                    tvNoOutpasses.setVisibility(View.GONE);
                    rView.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //outpasses.add(new Outpass());

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

    class OutpassesRViewAdapter extends RecyclerView.Adapter<OutpassesRViewAdapter.OutpassesVHolder>{
        Context context;
        ArrayList<Outpass> outpasses;

        @NonNull
        @Override
        public OutpassesVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_outpass, viewGroup, false);
            OutpassesVHolder holder = new OutpassesVHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull OutpassesVHolder v, int i) {
            Outpass curOutpass = outpasses.get(i);
            v.tvFrom.setText(curOutpass.getFrom());
            v.tvTo.setText(curOutpass.getTo());
            v.tvLeaveDate.setText(curOutpass.getLeaveDate().getTime()+"");
            v.tvReturnDate.setText(curOutpass.getReturnDate().getTime()+"");

        }

        @Override
        public int getItemCount() {
            return  outpasses.size();
        }

        OutpassesRViewAdapter(Context ctx, ArrayList<Outpass> outpasses){
            context = ctx;
            this.outpasses = outpasses;
        }
        class OutpassesVHolder extends RecyclerView.ViewHolder{
            TextView tvTo;
            TextView tvFrom;
            TextView tvLeaveDate;
            TextView tvReturnDate;
            public OutpassesVHolder(@NonNull View itemView) {
                super(itemView);
                tvFrom = itemView.findViewById(R.id.tvFromCardOutpass);
                tvTo = itemView.findViewById(R.id.tvToCardOutpass);
                tvLeaveDate = itemView.findViewById(R.id.tvLeaveDateCardOutpass);
                tvReturnDate = itemView.findViewById(R.id.tvRetDateCardOutpass);
            }
        }

    }
}
