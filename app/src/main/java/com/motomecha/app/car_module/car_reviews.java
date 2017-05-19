package com.motomecha.app.car_module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vignesh2514 on 5/19/2017.
 */

public class car_reviews implements Parcelable{
    String name;
    String review_message;
    String review_domain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview_message() {
        return review_message;
    }

    public void setReview_message(String review_message) {
        this.review_message = review_message;
    }

    public String getReview_domain() {
        return review_domain;
    }

    public void setReview_domain(String review_domain) {
        this.review_domain = review_domain;
    }

    public static Creator<car_reviews> getCREATOR() {
        return CREATOR;
    }

    protected car_reviews(Parcel in) {
        name = in.readString();
        review_message = in.readString();
        review_domain = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(review_message);
        dest.writeString(review_domain);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<car_reviews> CREATOR = new Creator<car_reviews>() {
        @Override
        public car_reviews createFromParcel(Parcel in) {
            return new car_reviews(in);
        }

        @Override
        public car_reviews[] newArray(int size) {
            return new car_reviews[size];
        }
    };
}
