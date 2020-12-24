package com.example.rentacarapp;

import android.content.Intent;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Users {

    private FirebaseFirestore firebaseFirestore;

    String isim;
    String soyisim;
    String tc;
    String tel;
    String dogumTarihi;
    String sehir;
    String email;

    public Users(String isim, String soyisim, String tc, String tel, String dogumTarihi, String sehir, String email) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.tc = tc;
        this.tel = tel;
        this.dogumTarihi = dogumTarihi;
        this.sehir = sehir;
        this.email = email;

        HashMap<String, String> userData = new HashMap<>();
        userData.put("isim",isim);
        userData.put("soyisim", soyisim);
        userData.put("tc", tc);
        userData.put("tel", tel);
        userData.put("dogum_tarihi", dogumTarihi);
        userData.put("sehir", sehir);
        userData.put("email", email);

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Users").document(email).set(userData);

    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(String dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getSehir() {
        return sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
