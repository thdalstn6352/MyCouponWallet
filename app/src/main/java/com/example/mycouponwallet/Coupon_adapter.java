package com.example.mycouponwallet;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Coupon_adapter extends RecyclerView.Adapter<Coupon_adapter.CouponViewHolder> {

    private ArrayList<Coupon_data> arrayList;
    private Context context;


    public Coupon_adapter(ArrayList<Coupon_data> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_design, parent, false);
        CouponViewHolder holder = new CouponViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Coupon_adapter.CouponViewHolder holder, int position) {
        //holder.brand_img.setImageResource(arrayList.get(position).getBrand());
        holder.coupon_content.setText(arrayList.get(position).getInformation());
        holder.date.setText(arrayList.get(position).getDate());

    }

    @Override
    public int getItemCount() {

        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CouponViewHolder extends RecyclerView.ViewHolder {
        //ImageView brand_img;
        TextView coupon_content;
        TextView date;


        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.brand_img = itemView.findViewById(R.id.coupon_image);
            this.coupon_content = itemView.findViewById(R.id.coupon_content);
            this.date = itemView.findViewById(R.id.coupon_date);
        }
    }
}

