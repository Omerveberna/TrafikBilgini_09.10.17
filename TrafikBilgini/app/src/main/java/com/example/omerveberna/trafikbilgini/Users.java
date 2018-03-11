package com.example.omerveberna.trafikbilgini;

/**
 * Created by Omerveberna on 5.08.2017.
 */

public class Users {

    private String ad;
    private String soyad;
    private int score;


    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Users(String ad, String soyad, int score) {
        this.ad = ad;
        this.soyad = soyad;
        this.score = score;
    }



    public Users() {
    }




}
