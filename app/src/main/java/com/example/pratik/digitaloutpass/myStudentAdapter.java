package com.example.pratik.digitaloutpass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class myStudentAdapter extends RecyclerView.Adapter<myStudentAdapter.myStudentViewHolder> {

    private Context mctx;
    private List<myStudent> myStudentList;

    public myStudentAdapter(Context mctx, List<myStudent> myStudentList) {
        this.mctx = mctx;
        this.myStudentList = myStudentList;
    }

    @NonNull
    @Override
    public myStudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.list_layout_my_students, null);
        return new myStudentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myStudentViewHolder myStudentViewHolder, int i) {

        myStudent mystudent = myStudentList.get(i);

        myStudentViewHolder.nameS.setText(mystudent.getName());
        myStudentViewHolder.branch.setText(mystudent.getBranch());
        myStudentViewHolder.enroll.setText(mystudent.getEnroll());
        myStudentViewHolder.phone.setText(mystudent.getPhone()+"");

        myStudentViewHolder.profPic.setImageDrawable(mctx.getResources().getDrawable(mystudent.getProfPic(),null));

    }

    @Override
    public int getItemCount() {
        return myStudentList.size();
    }

    public class myStudentViewHolder extends RecyclerView.ViewHolder{

        ImageView profPic;
        TextView nameS,enroll,branch,phone;

        public myStudentViewHolder(@NonNull View itemView) {
            super(itemView);
            profPic = (ImageView)itemView.findViewById(R.id.profPicCardmy);
            nameS = itemView.findViewById(R.id.nameCardmy);
            enroll = itemView.findViewById(R.id.enrollCardmy);
            branch = itemView.findViewById(R.id.branchCardmy);
            phone = itemView.findViewById(R.id.phoneCardmy);
        }
    }
}
