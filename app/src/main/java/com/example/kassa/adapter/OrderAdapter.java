package com.example.kassa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kassa.R;
import com.example.kassa.ShowActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<String> list;
    Context context;

    public OrderAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ShowActivity.class);
            intent.putExtra("key",list.get(holder.getAdapterPosition()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        getOrder_items(list.get(position),holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView order_items, summa;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_items = itemView.findViewById(R.id.salesman_id);
            summa = itemView.findViewById(R.id.summa);

        }
    }
    private void getOrder_items(String key, ViewHolder holder){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("orders");

        reference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 int umumiy = 0;
                 int buyurtma_soni = 0;
                 for (DataSnapshot ds : snapshot.getChildren()){
                     umumiy += Integer.parseInt(String.valueOf(ds.child("sotilgan_narx").getValue()));
                     buyurtma_soni += Integer.parseInt(String.valueOf(ds.child("olingan_miqdor").getValue()));
                 }
                    holder.order_items.setText(String.valueOf(buyurtma_soni));
                 holder.summa.setText(currencyFormatter(String.valueOf(umumiy)));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String currencyFormatter(String num) {
        double m = Double.parseDouble(num);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(m);
    }
}
