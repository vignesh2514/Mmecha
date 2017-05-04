package com.motomecha.app.Global_classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vigneshs on 06/04/17.
 */

public class BrandSelect_list implements Parcelable{
    String id;
    String brand;
    String brand_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand_image() {
        return brand_image;
    }

    public void setBrand_image(String brand_image) {
        this.brand_image = brand_image;
    }

    public static Creator<BrandSelect_list> getCREATOR() {
        return CREATOR;
    }

    protected BrandSelect_list(Parcel in) {
        id = in.readString();
        brand = in.readString();
        brand_image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(brand);
        dest.writeString(brand_image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BrandSelect_list> CREATOR = new Creator<BrandSelect_list>() {
        @Override
        public BrandSelect_list createFromParcel(Parcel in) {
            return new BrandSelect_list(in);
        }

        @Override
        public BrandSelect_list[] newArray(int size) {
            return new BrandSelect_list[size];
        }
    };
}
