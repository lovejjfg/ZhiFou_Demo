package com.lovejjfg.zhifou_demo.data.model;

import com.google.gson.annotations.Expose;

/**
 * Created by lovejjfg on 2016/2/21.
 */
public class StartImage {
    @Expose
    private String text;
    @Expose
    private String img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isValueEquals(StartImage startImage) {
        return this.text.equals(startImage.getText()) && this.img.equals(startImage.getImg());
    }
}
