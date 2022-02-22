package com.example.kassa;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kassa.adapter.ShowAdapter;
import com.example.kassa.bottomFragment.BottomSheet;
import com.example.kassa.firebase.FirebaseData;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface {
    public FirebaseDatabase database;
    public DatabaseReference reference;
    public static List<ShowModel> showModelList;
    public RecyclerView showRec;
    public TextView txtTotal;
//    OnSwipeTouchListener listener;
    BottomSheet bottomSheet;
    Dialog dialog;


    ConstraintLayout constraintLayout;
    public String key;
    public MaterialButton btnTolov;
    public double umumiy = 0;
    public FirebaseData firebaseData;
    float x1, x2, y1, y2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initViews();
        initVars();
//        constraintLayout.setOnTouchListener(listener);
//        showRec.setOnTouchListener(listener);

        btnTolov.setOnClickListener(this);
    }

    private void initViews() {
        showRec = findViewById(R.id.showRec);
        txtTotal = findViewById(R.id.total);
        constraintLayout = findViewById(R.id.constr);
        btnTolov = findViewById(R.id.btnTolov);
    }

    private void initVars() {
        database = FirebaseDatabase.getInstance();
        key = getIntent().getStringExtra("key");
        reference = database.getReference("orders").child(key);
        firebaseData = new FirebaseData(getApplicationContext());
//        listener = new OnSwipeTouchListener(getApplicationContext()) {
//            @Override
//            public void onSwipeRight() {
//                super.onSwipeRight();
//                Intent intent = new Intent(ShowActivity.this, MainActivity.class);
//                startActivity(intent);
//                ShowActivity.this.finish();
//            }
//        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();


    }

    private void getData() {
        showModelList = new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showModelList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    showModelList.add(ds.getValue(ShowModel.class));
                }
                ShowAdapter adapter = new ShowAdapter(getApplicationContext(), showModelList);
                showRec.setAdapter(adapter);
                for (int i = 0; i < showModelList.size(); i++) {
                    umumiy += Double.parseDouble(showModelList.get(i).getSotilgan_narx());

                }
                txtTotal.setText(currencyFormatter(String.valueOf(umumiy)));

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ShowActivity.this,QarzActivity.class);
        startActivity(intent);
//        bottomSheet = new BottomSheet(getApplicationContext(), this);
//        bottomSheet.show(getSupportFragmentManager(), "Tag");
    }

    @Override
    public void isShow(boolean isShow, int tolov) {
        if (isShow) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_item);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            firebaseData.deleteMiqdor(showModelList, key, tolov);
            new Handler().postDelayed(() -> {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getApplicationContext().startActivity(intent);
                this.finish();
            }, 2000);
        }
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                x1 = event.getX();
//                y1 = event.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                x2 = event.getX();
//                y2 = event.getY();
//                if(x1 < x2){
//                    Intent intent = new Intent(ShowActivity.this,MainActivity.class);
//                    startActivity(intent);
//                }
//        }
//        return false;
//    }

//    @Override
//    public void onBackPressed() {
//
//        super.onBackPressed();
//        Intent intent = new Intent(Show
//        Activity.this,MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//
//    }
public String currencyFormatter(String num) {
    double m = Double.parseDouble(num);
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    return formatter.format(m);
}
}