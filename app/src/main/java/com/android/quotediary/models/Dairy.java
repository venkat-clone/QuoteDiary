package com.android.quotediary.models;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

public class Dairy {
    String content = "Today is sooo";
    Date date = new Date();
    My_Date my_date = new My_Date();

    public My_Date getMy_date() {
        return my_date;
    }

    public void setMy_date(My_Date my_date) {
        this.my_date = my_date;
    }

    public Dairy(My_Date my_date) {
        this.my_date = my_date;
    }
    public Dairy() {
    }

    public static class My_Date{
        int year = 2020;
        int day_year = 120;
        int minni_day = 1212212;

        public My_Date() {
        }
        public My_Date(int year,int day_year){
            this.year = year;
            this.day_year = day_year;
        }
        public static My_Date getToday(){
            Calendar calendar = Calendar.getInstance();
            return new My_Date(calendar.get(Calendar.YEAR),calendar.get(Calendar.DAY_OF_YEAR));
        }
        public int getMinni_day() {
            return minni_day;
        }

        public void setMinni_day(int minni_day) {
            this.minni_day = minni_day;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getDay_year() {
            return day_year;
        }

        public void setDay_year(int day_year) {
            this.day_year = day_year;
        }
    }

    public Dairy(String content, Date date) {
        this.content = content;
        this.date = date;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
