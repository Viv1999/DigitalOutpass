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

public class MyStudentsAdapter extends RecyclerView.Adapter<MyStudentsAdapter.MyStudentsViewHolder> {

    private Context mctx;
    private List<MyStudent> myStudentList;

    public MyStudentsAdapter(Context mctx, List<MyStudent> myStudentList) {
        this.mctx = mctx;
        this.myStudentList = myStudentList;
    }

    @NonNull
    @Override
    public MyStudentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.list_layout_my_students, null);
        return new MyStudentsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyStudentsViewHolder viewHolder, int i) {

        MyStudent mystudent = myStudentList.get(i);

        viewHolder.nameS.setText(mystudent.getName());
        viewHolder.branch.setText(mystudent.getBranch());
        viewHolder.enroll.setText(mystudent.getEnroll()+"");
        viewHolder.phone.setText(mystudent.getPhone()+"");

        //MyStudentsViewHolder.profPic.setImageDrawable(mctx.getResources().getDrawable(mystudent.getProfPic(),null));

    }

    @Override
    public int getItemCount() {
        return myStudentList.size();
    }

    public class MyStudentsViewHolder extends RecyclerView.ViewHolder{

        ImageView profPic;
        TextView nameS,enroll,branch,phone;

        public MyStudentsViewHolder(@NonNull View itemView) {
            super(itemView);
            profPic = (ImageView)itemView.findViewById(R.id.profPicCardmy);
            nameS = itemView.findViewById(R.id.nameCardmy);
            enroll = itemView.findViewById(R.id.enrollCardmy);
            branch = itemView.findViewById(R.id.branchCardmy);
            phone = itemView.findViewById(R.id.phoneCardmy);
        }
    }
}
