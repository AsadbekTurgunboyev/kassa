package com.example.kassa.bottomFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.kassa.DialogInterface;
import com.example.kassa.R;
import com.example.kassa.firebase.FirebaseData;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class BottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    public MaterialCardView btnNaqd, btnPlastik, btnNasiya;
    public MaterialButton btnTolash;
    public RadioButton rdb1, rdb2, rdb3;
    public boolean checked = false;
    public int tolovTuri = 0;
    public Context context;
    public DialogInterface anInterface;
    public FirebaseData firebaseData;

    public BottomSheet(Context applicationContext, DialogInterface anInterface) {

        this.context = applicationContext;
        this.anInterface = anInterface;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_payment, container, false);
        initViews(v);
        firebaseData = new FirebaseData(context);
        btnNaqd.setOnClickListener(this);
        btnPlastik.setOnClickListener(this);
        btnNasiya.setOnClickListener(this);
        btnTolash.setOnClickListener(this);


        return v;
    }

    private void initViews(View v) {
        btnNaqd = v.findViewById(R.id.btnNaqd);
        btnNasiya = v.findViewById(R.id.btnNasiya);
        btnPlastik = v.findViewById(R.id.btnPlastik);
        rdb1 = v.findViewById(R.id.rdb1);
        rdb2 = v.findViewById(R.id.rdb2);
        rdb3 = v.findViewById(R.id.rdb3);
        btnTolash = v.findViewById(R.id.btnTolash);


    }

    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNaqd:
                if (checked) {
                    clearStroke();
                }
                btnNaqd.setStrokeColor(ContextCompat.getColor(context, R.color.stroke));
                rdb1.setChecked(true);
                checked = true;
                tolovTuri = 1;

                break;
            case R.id.btnNasiya:
                if (checked) {
                    clearStroke();
                }
                btnNasiya.setStrokeColor(ContextCompat.getColor(context, R.color.stroke));
                rdb3.setChecked(true);
                checked = true;
                tolovTuri = 3;

                break;
            case R.id.btnPlastik:
                if (checked) {
                    clearStroke();
                }
                btnPlastik.setStrokeColor(ContextCompat.getColor(context, R.color.stroke));
                rdb2.setChecked(true);
                checked = true;
                tolovTuri = 2;

                break;
            case R.id.btnTolash:
                if (tolovTuri > 0) {
                    dismiss();
                    anInterface.isShow(true, tolovTuri);
                } else {
                    Toast.makeText(context, "To'lov turini tanlang!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void clearStroke() {
        rdb2.setChecked(false);
        rdb1.setChecked(false);
        rdb3.setChecked(false);
        btnPlastik.setStrokeColor(ContextCompat.getColor(context, R.color.inActivestroke));
        btnNasiya.setStrokeColor(ContextCompat.getColor(context, R.color.inActivestroke));
        btnNaqd.setStrokeColor(ContextCompat.getColor(context, R.color.inActivestroke));

    }
}
