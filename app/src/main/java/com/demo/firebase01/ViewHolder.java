package com.demo.firebase01;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public ViewHolder(View itemView) {
        super(itemView);
        mView=itemView;
    }

    //set details to recycler view row
    public void setDetails(Context ctx, String title, String description, String image){

        //views
        TextView mtvTitle=mView.findViewById(R.id.rtvTitle);
        TextView mtvDescription=mView.findViewById(R.id.rtvDescription);
        ImageView mivImage=mView.findViewById(R.id.rivImage);

        //set data to views
        mtvTitle.setText(title);
        mtvDescription.setText(description);
        Picasso.get().load(image).into(mivImage);

    }
}
