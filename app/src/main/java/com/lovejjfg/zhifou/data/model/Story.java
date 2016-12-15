package com.lovejjfg.zhifou.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lovejjfg on 2015/2/27.
 */
public class Story implements Parcelable {
    @Expose
    @SerializedName("images")
    private List<String> images;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("ga_prefix")
    private String gaPrefix;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("multipic")
    private String multiPic;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("share_url")
    private String shareUrl;
    @Expose
    @SerializedName("body")
    private String body;
    @Expose
    @SerializedName("image_source")
    private String imageSource;
    @Expose
    @SerializedName("js")
    private List<String> jsList;
    @Expose
    @SerializedName("css")
    private List<String> cssList;
    @Expose
    @SerializedName("recommenders")
    private List<Editor> recommenders;
    @Expose
    @SerializedName("theme")
    private  Theme theme;

    private String thumbnail;
    private String url;
    private String sectionThumbnail;
    private String sectionId;
    private String sectionName;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGaPrefix() {
        return gaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMultiPic() {
        return multiPic;
    }

    public void setMultiPic(String multiPic) {
        this.multiPic = multiPic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getSectionThumbnail() {
        return sectionThumbnail;
    }

    public void setSectionThumbnail(String sectionThumbnail) {
        this.sectionThumbnail = sectionThumbnail;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public List<String> getJsList() {
        return jsList;
    }

    public void setJsList(List<String> jsList) {
        this.jsList = jsList;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<String> getCssList() {
        return cssList;
    }

    public void setCssList(List<String> cssList) {
        this.cssList = cssList;
    }

    public List<Editor> getRecommenders() {
        return recommenders;
    }

    public void setRecommenders(List<Editor> recommenders) {
        this.recommenders = recommenders;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.images);
        dest.writeString(this.type);
        dest.writeString(this.id);
        dest.writeString(this.gaPrefix);
        dest.writeString(this.title);
        dest.writeString(this.multiPic);
        dest.writeString(this.image);
        dest.writeString(this.shareUrl);
        dest.writeString(this.body);
        dest.writeString(this.imageSource);
        dest.writeStringList(this.jsList);
        dest.writeStringList(this.cssList);
        dest.writeList(this.recommenders);
        dest.writeParcelable(this.theme, flags);
        dest.writeString(this.thumbnail);
        dest.writeString(this.url);
        dest.writeString(this.sectionThumbnail);
        dest.writeString(this.sectionId);
        dest.writeString(this.sectionName);
    }

    public Story() {
    }

    protected Story(Parcel in) {
        this.images = in.createStringArrayList();
        this.type = in.readString();
        this.id = in.readString();
        this.gaPrefix = in.readString();
        this.title = in.readString();
        this.multiPic = in.readString();
        this.image = in.readString();
        this.shareUrl = in.readString();
        this.body = in.readString();
        this.imageSource = in.readString();
        this.jsList = in.createStringArrayList();
        this.cssList = in.createStringArrayList();
        this.recommenders = new ArrayList<Editor>();
        in.readList(this.recommenders, Editor.class.getClassLoader());
        this.theme = in.readParcelable(Theme.class.getClassLoader());
        this.thumbnail = in.readString();
        this.url = in.readString();
        this.sectionThumbnail = in.readString();
        this.sectionId = in.readString();
        this.sectionName = in.readString();
    }

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel source) {
            return new Story(source);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}
