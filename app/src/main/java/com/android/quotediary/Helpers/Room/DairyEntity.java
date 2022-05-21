package com.android.quotediary.Helpers.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Dairy"
)
public class DairyEntity {
    /**
     *
     */
    @NonNull
    @ColumnInfo(name = "day_in_year")
    private int day;


    @ColumnInfo(name="id_")
    @NonNull
    @PrimaryKey
    private String id_;

    @NonNull
    @ColumnInfo(name = "year")
    private int year;

    @NonNull
    @ColumnInfo(name = "content")
    private String content;

    public DairyEntity() {
    }

    /**
     *
     * @param day
     * @param year
     * @param content
     */
    public DairyEntity(int day, int year, @NonNull String content) {
        this.day = day;
        this.year = year;
        this.content = content;
    }

    public DairyEntity(int day, @NonNull String id_, int year, @NonNull String content) {
        this.day = day;
        this.id_ = id_;
        this.year = year;
        this.content = content;
    }

    @Entity(tableName = "Year")
    public static class Year {
        @PrimaryKey()
        private int year;

        public Year(int year) {
            this.year = year;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }






    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }
    public String  getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }




}
