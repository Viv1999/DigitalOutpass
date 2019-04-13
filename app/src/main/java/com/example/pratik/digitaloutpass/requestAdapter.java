package com.example.pratik.digitaloutpass;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class requestAdapter extends RecyclerView.Adapter<requestAdapter.requestViewHolder> {

    private ArrayList<Outpass> requests ;
    public requestAdapter(ArrayList<Outpass> requests) {
        this.requests= requests ;
    }

    @NonNull
    @Override
    public requestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater= LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.request_items, viewGroup,false);
        return  new requestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull requestViewHolder requestViewHolder, int i) {

        Outpass currentItem = requests.get(i);

        requestViewHolder.name.setText(currentItem.getPersonName());
        requestViewHolder.id.setText(currentItem.getHostel());
        requestViewHolder.from.setText(currentItem.getFrom());
        requestViewHolder.to.setText(currentItem.getTo());
        requestViewHolder.ReturnDate.setText(currentItem.getReturnDate().toString());
        requestViewHolder.LeavingDate.setText(currentItem.getLeaveDate().toString());
    }

    @Override
    public int getItemCount() {
        return requests.size() ;
    }

    public  class requestViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,id,from,to,ReturnDate,LeavingDate;


        public requestViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.StudentImage);
            name=itemView.findViewById(R.id.textViewName);
            id=itemView.findViewById(R.id.textViewOutpassId);
            from=itemView.findViewById(R.id.textViewFrom);
            to=itemView.findViewById(R.id.textViewTo);
            ReturnDate=itemView.findViewById(R.id.textViewReturnDate);
            LeavingDate =itemView.findViewById(R.id.textViewLeavingDate);
        }
    }
}
