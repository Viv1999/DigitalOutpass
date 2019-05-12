package com.example.pratik.digitaloutpass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

        Glide.with(mctx)
                .load(mystudent.getImageUrl())
                .into(viewHolder.profPic);
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

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}
