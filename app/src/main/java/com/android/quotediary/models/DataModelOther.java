package com.android.quotediary.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModelOther {
    @SerializedName("Nothing")
    @Expose
    String Testing;

    public static class Wallpaper{

        @SerializedName("url")
        @Expose
        String url;
        public Wallpaper(String url,int height,int Width) {
            this.url = url;
        }

        public Wallpaper(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class FontStyle{
        String id = "font0001";
        String name = "venkey";

        public FontStyle(){};
        public FontStyle(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class finalQuote{
        String quote="";
        String StyleID="";
        float TextSize=32;

        public finalQuote(){

        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public String getStyleID() {
            return StyleID;
        }

        public void setStyleID(String styleID) {
            StyleID = styleID;
        }

        public float getTextSize() {
            return TextSize;
        }

        public void setTextSize(float textSize) {
            TextSize = textSize;
        }
    }

    class Search{

    }

}
