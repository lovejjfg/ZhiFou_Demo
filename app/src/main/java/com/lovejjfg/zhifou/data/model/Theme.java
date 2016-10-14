package com.lovejjfg.zhifou.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张俊 on 2016/2/21.
 */
public class Theme implements Parcelable {
    @Expose
    private String color;
    @Expose
    private String thumbnail;
    @Expose
    private String image;
    @Expose
    private String background;
    @Expose
    private String description;
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private List<Story> stories;
    @Expose
    private List<Editor> editors;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.color);
        dest.writeString(this.thumbnail);
        dest.writeString(this.image);
        dest.writeString(this.background);
        dest.writeString(this.description);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.stories);
        dest.writeList(this.editors);
    }

    public Theme() {
    }

    protected Theme(Parcel in) {
        this.color = in.readString();
        this.thumbnail = in.readString();
        this.image = in.readString();
        this.background = in.readString();
        this.description = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.stories = in.createTypedArrayList(Story.CREATOR);
        this.editors = new ArrayList<Editor>();
        in.readList(this.editors, Editor.class.getClassLoader());
    }

    public static final Parcelable.Creator<Theme> CREATOR = new Parcelable.Creator<Theme>() {
        @Override
        public Theme createFromParcel(Parcel source) {
            return new Theme(source);
        }

        @Override
        public Theme[] newArray(int size) {
            return new Theme[size];
        }
    };
}
