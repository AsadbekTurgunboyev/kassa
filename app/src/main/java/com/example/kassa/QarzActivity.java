package com.example.kassa;

import static com.example.kassa.ShowActivity.showModelList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class QarzActivity extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView ism;
    EditText familya, telefon, izoh;
    MaterialButton btn_qarz;
    FirebaseDatabase database;
    DatabaseReference reference;
    List<QarzModel> qarzList;
    List<String> nameQarz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qarz);
        //get current date time with Date()
        initViews();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("qarzlar");
        btn_qarz.setOnClickListener(this);
//        String[] cities =  { "Paries,France", "PA,United States","Parana,Brazil",
//                "Padua,Italy", "Pasadena,CA,United States"};
//
    }

    private void initViews() {
        ism = (AutoCompleteTextView) findViewById(R.id.editText);
        familya = findViewById(R.id.editText2);
        telefon = findViewById(R.id.editText3);
        btn_qarz = findViewById(R.id.btn_qarz);
        izoh = findViewById(R.id.note);

    }

    //    private String umt() {
//        int um = 0;
//        for (int i =0 ; i< showModelList.size(); i++){
//           um += Integer.parseInt(showModelList.get(i).getSotilgan_narx());
//        }
//        return String.valueOf(um);
//    }
    private String getName(String str, int i) {
        String name;
        int a = 3;
        if (i + 1 >= 10) {
            a = 2;
        }


        if (str.length() < 14) {
            int length = 13 - str.length();
            name = (i + 1) + ". " + str + new String(new char[length + a]).replace("\0", "\t");
        } else {

            int length = 13 - str.substring(12).length();
            str = str.substring(0, 12) + "\n" + str.substring(12);
            name = (i + 1) + ". " + str + new String(new char[length + a + 3]).replace("\0", "\t");
        }

        return name;
    }

    private String getQty(String str) {
        int space = 4 - str.length();
        return new String(new char[space]).replace("\0", "\t") +
                str + new String(new char[5]).replace("\0", "\t");
    }

    private String getRate(String str) {
        int space = 8 - str.length();
        return str + new String(new char[space]).replace("\0", "\t");
    }

    private void set() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") DateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Date time = new Date();
        String Date = dateFormat.format(date);
        String Time = timeFormat.format(time);
        String Header =
                "****Super Market****       \n"
                        + "Kun: " + Date +
                        "\nVaqt: " + Time + "\n"
                        + "---------------------------------\n"
                        + "| " + "Mahsulot nomi\t\t\t\tMiqdor\t\t\tBaho\t\t\t\tNarx\n"
                        + "____________________________________\n";

        String amt =
                "\n \n \nUmumiy = " + "umt()" + "\n"
                        + "Tax =" + "salom" + "\n"
                        + "*********************************\n"
                        + "Xaridingiz uchun rahmat. \n";

        StringBuilder bill = new StringBuilder(Header);
        int i = 0;
        do {

            String name = getName(showModelList.get(0).getProduct_name(), i);

            String qty = getQty(String.valueOf(showModelList.get(0).getOlingan_miqdor()));
            String rate = getRate("16000");
            String amount = "" + showModelList.get(0).getSotilgan_narx();

            String items =
                    name + qty + rate + amount + "\n";

            bill.append(items);
            i++;

        } while (i < 10);

        bill.append(amt);
//        textView.setText(bill.toString());

    }

    @Override
    public void onClick(View view) {
        String name = ism.getText().toString();
        String fam = familya.getText().toString();
        String number = telefon.getText().toString();
        String note = izoh.getText().toString();
        if (name.isEmpty() || fam.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Bo'sh joylarni to'ldiring", Toast.LENGTH_SHORT).show();
        } else {
            String key = String.valueOf(reference.push().getKey());
            QarzModel model = new QarzModel(name + " " + fam, number, note, key, 0);
            reference.child(Objects.requireNonNull(key)).setValue(model).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(QarzActivity.this, "Qarzdor ro'yhatdan o'tkazildi!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        qarzList = new ArrayList<>();
        nameQarz = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                qarzList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    QarzModel model = ds.getValue(QarzModel.class);
                    qarzList.add(model);
                }
                nameQarz.clear();
                for (int i = 0; i < qarzList.size(); i++) {
                    nameQarz.add(qarzList.get(i).getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_item, nameQarz);
                ism.setThreshold(1);
                ism.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String selection = (String) adapterView.getItemAtPosition(position);
                        int pos = -1;

                        for (int i = 0; i < nameQarz.size(); i++) {
                            if (nameQarz.get(i).equals(selection)) {
                                pos = i;
                                break;
                            }
                        }
                        getData( qarzList.get(pos));

                    }
                });
                ism.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getData(QarzModel model) {
        String[] a = model.getName().split(" ");
        String nam = String.valueOf(a[0]);
        String fam = String.valueOf(a[1]);
        ism.setText(nam);
        familya.setText(fam);
        telefon.setText(model.getNumber());
        izoh.setText(model.getNote());
    }
}