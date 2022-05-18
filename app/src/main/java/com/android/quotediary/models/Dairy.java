package com.android.quotediary.models;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Dairy {
    public boolean isToday = false;
    String Content = "";
    int Year=0;
    int Day =0;
    public boolean unsaved;
    /**
     *
     * @param content
     * @param year
     * @param day
     */
    public Dairy(String content, int year, int day) {
        Content = content;
        Year = year;
        Day = day;
        unsaved = false;
        isToday = isToday(this);
    }



    public Dairy() {
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }
    //
//    public static My_Date getToday(){
//        Calendar calendar = Calendar.getInstance();
//        return new My_Date(calendar.get(Calendar.YEAR),calendar.get(Calendar.DAY_OF_YEAR));
//    }
    public static boolean isToday(Dairy dairy){
        Calendar calendar = Calendar.getInstance();
        return dairy.Day==calendar.get(Calendar.DAY_OF_YEAR) && dairy.Year==calendar.get(Calendar.YEAR);
    }
    public static Dairy getToday(){
        Calendar calendar = Calendar.getInstance();
        return new Dairy("",calendar.get(Calendar.YEAR),calendar.get(Calendar.DAY_OF_YEAR));
    }

    //

    public static class Year {
        int Year=0;
        List<Dairy> DairyS;
        public boolean selected;

        public Year(int year, List<Dairy> dairyS) {
            Year = year;
            DairyS = dairyS;
        }

        public Year() {
        }
        public Year(int year){
            this.Year = year;
        }

        public int getYear() {
            return Year;
        }

        public void setYear(int year) {
            Year = year;
        }

        public List<Dairy> getDairyS() {
            return DairyS;
        }

        public void setDairyS(List<Dairy> dairyS) {
            DairyS = dairyS;
        }
    }




}
