package com.android.quotediary.models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    public static Dairy.ServerDairy getToday(){
        Calendar calendar = Calendar.getInstance();
        return new Dairy.ServerDairy("",calendar.get(Calendar.YEAR),calendar.get(Calendar.DAY_OF_YEAR));
    }

    //

    public static class Year {
        int Year=0;
        List<Dairy.ServerDairy> DairyS;
        public boolean selected;

        public Year(int year, List<Dairy.ServerDairy> dairyS) {
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

        public List<Dairy.ServerDairy> getDairyS() {
            return DairyS;
        }

        public void setDairyS(List<Dairy.ServerDairy> dairyS) {
            DairyS = dairyS;
        }
    }



    public static class ServerDairy{
        @SerializedName("_id")
        @Expose
        String id;
        @SerializedName("content")
        @Expose
        String content;
        @SerializedName("year")
        @Expose
        int year;
        @SerializedName("day")
        @Expose
        int day;
        public boolean isToday;
        public boolean unsaved = false;

        public static boolean isToday(Dairy.ServerDairy dairy){
            Calendar calendar = Calendar.getInstance();
            return dairy.day==calendar.get(Calendar.DAY_OF_YEAR) && dairy.year==calendar.get(Calendar.YEAR);
        }

        public ServerDairy(String id, String content, int year, int day) {
            this.id = id;
            this.content = content;
            this.year = year;
            this.day = day;
            isToday = ServerDairy.isToday(this);
        }

        public ServerDairy(String content, int year, int day) {
            this.content = content;
            this.year = year;
            this.day = day;
            isToday = ServerDairy.isToday(this);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }



}
