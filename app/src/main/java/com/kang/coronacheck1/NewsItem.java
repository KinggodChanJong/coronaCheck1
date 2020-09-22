package com.kang.coronacheck1;

import android.graphics.drawable.Drawable;

public class NewsItem {
    String title, contents, image;

    public NewsItem() {
        this.title = title;
        this.contents = contents;
        this.image = image;
    }
    public NewsItem(String title, String contents, String image){
        this.title = title;
        this.contents = contents;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
