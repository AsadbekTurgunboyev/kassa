package com.example.kassa;

import static com.example.kassa.ShowActivity.showModelList;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QarzActivity extends AppCompatActivity {
    AutoCompleteTextView ism;
    EditText familya, telefon,izoh;
    MaterialButton btn_qarz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qarz);
        //get current date time with Date()
        initViews();
        String[] cities =  { "Paries,France", "PA,United States","Parana,Brazil",
                "Padua,Italy", "Pasadena,CA,United States"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item,cities);
        ism.setThreshold(1);
        ism.setAdapter(adapter);

    }

    private void initViews() {
        ism = (AutoCompleteTextView) findViewById(R.id.editText);
        familya = findViewById(R.id.editText2);
        telefon = findViewById(R.id.editText3);

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
    private void set(){
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
}