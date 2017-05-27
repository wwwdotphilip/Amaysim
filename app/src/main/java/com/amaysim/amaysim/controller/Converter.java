package com.amaysim.amaysim.controller;

import java.text.NumberFormat;
import java.util.Locale;

public class Converter {

    public static String dollar(String value){
        int val;
        if (value.equals("null")){
            val = 0;
        } else {
            val = Integer.parseInt(value);
        }
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
        return n.format(val / 100.0);
    }

    public static String mbToGb(String size) {
        int value;
        if (size.equals("null")){
            size = "0";
        }
        value = Integer.parseInt(size);
        float val = (float) value / 1024;
        return String.format(Locale.getDefault(),"%.2f", val) + " GB";
    }
}
