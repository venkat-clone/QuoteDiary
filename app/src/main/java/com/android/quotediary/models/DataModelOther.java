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
        String id = "01";
        String name = "venkey";
        boolean Selected = false;
        public FontStyle(String id ){this.id = id;};
        public FontStyle(String id, String name, boolean selected) {
            this.id = id;
            this.name = name;
            Selected = selected;
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

        public boolean isSelected() {
            return Selected;
        }

        public void setSelected(boolean selected) {
            Selected = selected;
        }
    }


    class Search{

    }

}
