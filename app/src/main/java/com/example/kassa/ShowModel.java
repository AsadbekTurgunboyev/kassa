package com.example.kassa;

public class ShowModel {
    String cat_name, key, product_name, sotilgan_narx;
    int id, olingan_miqdor;

    public ShowModel(String cat_name, String key, String product_name, String sotilgan_narx, int id, int olingan_miqdor) {
        this.cat_name = cat_name;
        this.key = key;
        this.product_name = product_name;
        this.sotilgan_narx = sotilgan_narx;
        this.id = id;
        this.olingan_miqdor = olingan_miqdor;
    }

    public ShowModel() {
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSotilgan_narx() {
        return sotilgan_narx;
    }

    public void setSotilgan_narx(String sotilgan_narx) {
        this.sotilgan_narx = sotilgan_narx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOlingan_miqdor() {
        return olingan_miqdor;
    }

    public void setOlingan_miqdor(int olingan_miqdor) {
        this.olingan_miqdor = olingan_miqdor;
    }
}
