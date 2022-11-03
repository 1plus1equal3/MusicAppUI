package com.example.musicappui.Fragment.HomeFragment;

public class Candy {
    private String candyBrand;
    private String[] candyShellUrl;

    public Candy(String candyBrand, String[] candyShellUrl) {
        this.candyBrand = candyBrand;
        this.candyShellUrl = candyShellUrl;
    }

    public String getCandyBrand() {
        return candyBrand;
    }

    public void setCandyBrand(String candyBrand) {
        this.candyBrand = candyBrand;
    }

    public String[] getCandyShellUrl() {
        return candyShellUrl;
    }

    public void setCandyShellUrl(String[] candyShellUrl) {
        this.candyShellUrl = candyShellUrl;
    }
}
