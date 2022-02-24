package com.example.kassa;

public class QarzModel {
    String name,number,note,key;
    int umumiy_qarz;


    public QarzModel() {
    }

    public QarzModel(String name, String number, String note,String key, int umumiy_qarz) {
        this.name = name;

        this.number = number;
        this.note = note;
        this.key = key;
        this.umumiy_qarz = umumiy_qarz;
    }

    public String getKey() {
        return key;
    }

    public int getUmumiy_qarz() {
        return umumiy_qarz;
    }

    public String getName() {
        return name;
    }



    public String getNumber() {
        return number;
    }

    public String getNote() {
        return note;
    }
}
