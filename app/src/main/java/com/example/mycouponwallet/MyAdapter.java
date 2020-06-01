package com.example.mycouponwallet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable {

    Context c;
    ArrayList<Model> models, filterList;
    CustomFilter filter;


    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
        this.filterList = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        holder.mTitle.setText(models.get(position).getTitle());
        holder.mDes.setText(models.get(position).getDescription());
        holder.mImageView.setImageResource(models.get(position).getImg());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                String gTitle = models.get(position).getTitle();
                String gDec = models.get(position).getDescription();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.mImageView.getDrawable();

                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] bytes = stream.toByteArray();

                Intent intent = new Intent(c, AnotherActivity.class);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDesc", gDec);
                intent.putExtra("iImage", bytes);
                c.startActivity(intent);
            }
        });

    //    holder.setItemClickListener(new ItemClickListener() {
    //        @Override
    //        public void onItemClickListener(View v, int position) {
    //            if(models.get(position).getTitle().equals("ediya")){

    //            }
    //            if(models.get(position).getTitle().equals("starbucks")){

    //            }
    //            if(models.get(position).getTitle().equals("bbackdabang")){

    //            }
    //            if(models.get(position).getTitle().equals("blue_bottle")){

    //            }
    //        }
    //    });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public Filter getFilter() {

        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }

        return filter;
    }
}
