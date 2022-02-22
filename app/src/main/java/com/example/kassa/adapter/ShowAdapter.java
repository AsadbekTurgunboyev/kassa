package com.example.kassa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kassa.R;
import com.example.kassa.ShowModel;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyView> {
        Context context;
        List<ShowModel> list;

    public ShowAdapter(Context context, List<ShowModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.show_item,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        holder.imageView.setImageResource(R.drawable.mage_24);
        holder.textView.setText(list.get(position).getProduct_name());
        holder.textView2.setText(MessageFormat.format("{0}x", list.get(position).getOlingan_miqdor()));
        holder.txtPrice.setText(list.get(position).getSotilgan_narx());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyView extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,textView2,txtPrice;
        public MyView(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }


}
