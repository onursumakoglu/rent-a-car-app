package com.example.rentacarapp;

public class Araba {

    private String marka;
    private String model;
    private int modelYili;
    private int kilometre;
    private String vitesTuru;
    private String plaka;

    public Araba(String marka, String model, int modelYili, int kilometre, String vitesTuru, String plaka) {
        this.marka = marka;
        this.model = model;
        this.modelYili = modelYili;
        this.kilometre = kilometre;
        this.vitesTuru = vitesTuru;
        this.plaka = plaka;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getModelYili() {
        return modelYili;
    }

    public void setModelYili(int modelYili) {
        this.modelYili = modelYili;
    }

    public int getKilometre() {
        return kilometre;
    }

    public void setKilometre(int kilometre) {
        this.kilometre = kilometre;
    }

    public String getVitesTuru() {
        return vitesTuru;
    }

    public void setVitesTuru(String vitesTuru) {
        this.vitesTuru = vitesTuru;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }
}
