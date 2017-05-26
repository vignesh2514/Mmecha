package com.motomecha.app.Global_classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vignesh2514 on 4/10/2017.
 */

public class ModelSelect_list implements Parcelable {
    String model;
    String brand;
    String model_image;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel_image() {
        return model_image;
    }

    public void setModel_image(String model_image) {
        this.model_image = model_image;
    }

    public static Creator<ModelSelect_list> getCREATOR() {
        return CREATOR;
    }

    protected ModelSelect_list(Parcel in) {
        model = in.readString();
        brand = in.readString();
        model_image = in.readString();
    }

    public static final Creator<ModelSelect_list> CREATOR = new Creator<ModelSelect_list>() {
        @Override
        public ModelSelect_list createFromParcel(Parcel in) {
            return new ModelSelect_list(in);
        }

        @Override
        public ModelSelect_list[] newArray(int size) {
            return new ModelSelect_list[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(model);
        dest.writeString(brand);
        dest.writeString(model_image);
    }
}
