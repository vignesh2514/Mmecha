package com.motomecha.app.Global_classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vignesh2514 on 5/1/2017.
 */

public class ListCustomReviews implements Parcelable {
String cr_title;
    String cr_link;
    String cr_image;

    protected ListCustomReviews(Parcel in) {
        cr_title = in.readString();
        cr_link = in.readString();
        cr_image = in.readString();
    }

    public static final Creator<ListCustomReviews> CREATOR = new Creator<ListCustomReviews>() {
        @Override
        public ListCustomReviews createFromParcel(Parcel in) {
            return new ListCustomReviews(in);
        }

        @Override
        public ListCustomReviews[] newArray(int size) {
            return new ListCustomReviews[size];
        }
    };

    public String getCr_title() {
        return cr_title;
    }

    public void setCr_title(String cr_title) {
        this.cr_title = cr_title;
    }

    public String getCr_link() {
        return cr_link;
    }

    public void setCr_link(String cr_link) {
        this.cr_link = cr_link;
    }

    public String getCr_image() {
        return cr_image;
    }

    public void setCr_image(String cr_image) {
        this.cr_image = cr_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cr_title);
        dest.writeString(cr_link);
        dest.writeString(cr_image);
    }
}
