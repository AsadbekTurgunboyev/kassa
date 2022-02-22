package com.example.kassa.firebase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kassa.ShowModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FirebaseData {
    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    String[] oylar = {"Yanvar","Fevral","Mart","Aprel","May","Iyun","Iyul","Avgust","Sentabr","Oktabr","Noyabr","Dekabr"};

    public FirebaseData(Context context) {
        this.context = context;
    }

    public void deleteMiqdor(List<ShowModel> list, String key, int tolov_turi) {
        for (ShowModel model : list) {
            dailySales(model, tolov_turi);
            reference = database.getReference("products").child(model.getCat_name()).child("list").child(model.getKey());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int mavjud_qiymat = Integer.parseInt(String.valueOf(snapshot.child("mavjud_qiymat").getValue()));

                    if (mavjud_qiymat > model.getOlingan_miqdor()) {
                        mavjud_qiymat = mavjud_qiymat - model.getOlingan_miqdor();
                        snapshot.getRef().child("mavjud_qiymat").setValue(mavjud_qiymat);
                        deleteItem(key);
                    } else {
                        Toast.makeText(context, "Bunday miqdorda mahsulot yo'q!", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }

    public void dailySales(ShowModel model, int tolov_turi) {
        reference = database.getReference("sales");
        String dateFormat = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        reference.child(oylar[Integer.parseInt(dateFormat)-1]).child(currentDate).child(String.valueOf(tolov_turi)).child(model.getCat_name()).child(model.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int olingan_miqdor = Integer.parseInt(String.valueOf(snapshot.child("olingan_miqdor").getValue()));
                    int sotilgan_narx = Integer.parseInt(String.valueOf(snapshot.child("sotilgan_narx").getValue()));
                    olingan_miqdor += model.getOlingan_miqdor();
                    sotilgan_narx += Integer.parseInt(model.getSotilgan_narx());
                    snapshot.getRef().child("olingan_miqdor").setValue(olingan_miqdor);
                    snapshot.getRef().child("sotilgan_narx").setValue(sotilgan_narx);

                } else {
                    snapshot.getRef().setValue(model);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteItem(String key) {
        reference = database.getReference("orders");
        reference.child(key).removeValue();
    }
}
