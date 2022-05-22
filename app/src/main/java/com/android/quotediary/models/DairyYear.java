package com.android.quotediary.models;

import java.util.ArrayList;
import java.util.List;

public class DairyYear {
    String year="";
    List<Dairy> daries =new ArrayList<>();

    public DairyYear() {
    }

    public DairyYear(String year, List<Dairy> daries) {
        this.year = year;
        this.daries = daries;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Dairy> getDaries() {
        return daries;
    }

    public void setDaries(List<Dairy> daries) {
        this.daries = daries;
    }
}
