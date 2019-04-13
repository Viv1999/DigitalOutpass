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
import java.util.Calendar;

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
        requestViewHolder.id.setText(currentItem.getId()+"");
        requestViewHolder.from.setText(currentItem.getFrom());
        requestViewHolder.to.setText(currentItem.getTo());
//        requestViewHolder.ReturnDate.setText(currentItem.getReturnDate().toString());
//        requestViewHolder.LeavingDate.setText(currentItem.getLeaveDate().toString());
        Calendar leaveCal = Calendar.getInstance();
        leaveCal.setTime(currentItem.getLeaveDate());
        Calendar returnCal = Calendar.getInstance();
        returnCal.setTime(currentItem.getReturnDate());
        requestViewHolder.leavingDate.setText(leaveCal.get(Calendar.DAY_OF_MONTH)+ "/" + (leaveCal.get(Calendar.MONTH))+"/" + leaveCal.get(Calendar.YEAR));
        requestViewHolder.returnDate.setText(returnCal.get(Calendar.DAY_OF_MONTH)+ "/" + (returnCal.get(Calendar.MONTH))+"/" + returnCal.get(Calendar.YEAR));

    }

    @Override
    public int getItemCount() {
        return requests.size() ;
    }

    public  class requestViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,id,from,to,returnDate,leavingDate;


        public requestViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.StudentImage);
            name=itemView.findViewById(R.id.textViewName);
            id=itemView.findViewById(R.id.textViewOutpassId);
            from=itemView.findViewById(R.id.textViewFrom);
            to=itemView.findViewById(R.id.textViewTo);
            returnDate=itemView.findViewById(R.id.textViewReturnDate);
            leavingDate =itemView.findViewById(R.id.textViewLeavingDate);
        }
    }
}
