package com.motomecha.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vignesh2514 on 4/11/2017.
 */

public class Myvehicle_list implements Parcelable{
    String register_number;
    String model_brand;

    public String getRegister_number() {
        return register_number;
    }

    public void setRegister_number(String register_number) {
        this.register_number = register_number;
    }

    public String getModel_brand() {
        return model_brand;
    }

    public void setModel_brand(String model_brand) {
        this.model_brand = model_brand;
    }

    public static Creator<Myvehicle_list> getCREATOR() {
        return CREATOR;
    }

    protected Myvehicle_list(Parcel in) {
        register_number = in.readString();
        model_brand = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(register_number);
        dest.writeString(model_brand);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Myvehicle_list> CREATOR = new Creator<Myvehicle_list>() {
        @Override
        public Myvehicle_list createFromParcel(Parcel in) {
            return new Myvehicle_list(in);
        }

        @Override
        public Myvehicle_list[] newArray(int size) {
            return new Myvehicle_list[size];
        }
    };
}
