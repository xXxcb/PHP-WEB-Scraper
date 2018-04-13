package com.example.xander.fxconverter.api;

/**
 * Created by Xander on 5/4/2018.
 */

public class fxRates {
    private String cur;
    private double amnt;

    public fxRates(String cur, Double amnt) {
        this.cur = cur;
        this.amnt = amnt;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public double getAmnt() {
        return amnt;
    }

    public void setAmnt(float amnt) {
        this.amnt = amnt;
    }
}
