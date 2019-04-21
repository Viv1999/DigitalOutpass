package com.example.pratik.digitaloutpass;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class PendingOutpasses extends Fragment {
    String curHostel;
    FirebaseAuth mAuth;
    FirebaseUser curUser;
    DatabaseReference outpassesRef = FirebaseDatabase.getInstance().getReference("outpasses");
    ArrayList<Outpass> outpasses;
    DatabaseReference curUserRef;
    RecyclerView rView;
    TextView tvNoOutpasses;
    public static PendingOutpasses newInstance(){
        PendingOutpasses pendingOutpasses = new PendingOutpasses();
        return pendingOutpasses;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();
        outpasses = new ArrayList<>();
        curUserRef = FirebaseDatabase.getInstance().getReference("users").child(curUser.getUid());



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pending_outpasses, container, false);

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
        curUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                curHostel = dataSnapshot.getValue(Warden.class).getHostel();
                outpassesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        outpasses.clear();
                        for(DataSnapshot outpassSnapshot : dataSnapshot.getChildren()){
                            Outpass curOutpass = outpassSnapshot.getValue(Outpass.class);
                            if(curOutpass.hostel.equals(curHostel)){
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }


    class OutpassesRViewAdapter extends RecyclerView.Adapter<OutpassesRViewAdapter.OutpassesVHolder>{
        Context context;
        ArrayList<Outpass> outpasses;

        @NonNull
        @Override
        public OutpassesRViewAdapter.OutpassesVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_outpass, viewGroup, false);
            OutpassesRViewAdapter.OutpassesVHolder holder = new OutpassesRViewAdapter.OutpassesVHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final OutpassesRViewAdapter.OutpassesVHolder v, int i) {
            Outpass curOutpass = outpasses.get(i);
            v.tvFrom.setText(curOutpass.getFrom());
            v.tvTo.setText(curOutpass.getTo());
            Calendar leaveCal = Calendar.getInstance();
            leaveCal.setTime(curOutpass.getLeaveDate());
            Calendar returnCal = Calendar.getInstance();
            returnCal.setTime(curOutpass.getReturnDate());
            v.tvLeaveDate.setText(leaveCal.get(Calendar.DAY_OF_MONTH)+ "/" + (leaveCal.get(Calendar.MONTH))+"/" + leaveCal.get(Calendar.YEAR));
            v.tvReturnDate.setText(returnCal.get(Calendar.DAY_OF_MONTH)+ "/" + (returnCal.get(Calendar.MONTH))+"/" + returnCal.get(Calendar.YEAR));
            v.tvCardId.setText("Outpass id: "+ curOutpass.getId());
            if(curOutpass.isVerified()){
                v.tvStatus.setText("Verified");
                v.tvStatus.setTextColor(Color.GREEN);
            }
            else{
                v.tvStatus.setTextColor(Color.RED);
                v.tvStatus.setText("Not verified");
            }
            DatabaseReference personName = FirebaseDatabase.getInstance().getReference("users").child(curOutpass.getPersonName()).child("name");
            personName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    v.tvStudentName.setText(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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
            TextView tvStudentName;
            TextView tvCardId;
            TextView tvStatus;
            public OutpassesVHolder(@NonNull View itemView) {
                super(itemView);
                tvFrom = itemView.findViewById(R.id.tvFromCardOutpass);
                tvTo = itemView.findViewById(R.id.tvToCardOutpass);
                tvLeaveDate = itemView.findViewById(R.id.tvLeaveDateCardOutpass);
                tvReturnDate = itemView.findViewById(R.id.tvRetDateCardOutpass);
                tvStudentName = itemView.findViewById(R.id.tvStudentName);
                tvCardId = itemView.findViewById(R.id.cardId);
                tvStatus = itemView.findViewById(R.id.tvStatus);
            }
        }

    }
}
