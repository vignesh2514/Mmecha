package com.motomecha.app.Global_classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vignesh2514 on 5/2/2017.
 */
public class carmechantlist implements Parcelable {
    String display_name;
    String latitu;
    String longitu;
    String address;
    String serve_type;
    String serve_brand;
    String price;
    String merch_mobile;
    String timings;
    String likes;
String area_location;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getLatitu() {
        return latitu;
    }

    public void setLatitu(String latitu) {
        this.latitu = latitu;
    }

    public String getLongitu() {
        return longitu;
    }

    public void setLongitu(String longitu) {
        this.longitu = longitu;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServe_type() {
        return serve_type;
    }

    public void setServe_type(String serve_type) {
        this.serve_type = serve_type;
    }

    public String getServe_brand() {
        return serve_brand;
    }

    public void setServe_brand(String serve_brand) {
        this.serve_brand = serve_brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMerch_mobile() {
        return merch_mobile;
    }

    public void setMerch_mobile(String merch_mobile) {
        this.merch_mobile = merch_mobile;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getArea_location() {
        return area_location;
    }

    public void setArea_location(String area_location) {
        this.area_location = area_location;
    }

    public static Creator<carmechantlist> getCREATOR() {
        return CREATOR;
    }

    protected carmechantlist(Parcel in) {
        display_name = in.readString();
        latitu = in.readString();
        longitu = in.readString();
        address = in.readString();
        serve_type = in.readString();
        serve_brand = in.readString();
        price = in.readString();
        merch_mobile = in.readString();
        timings = in.readString();
        likes = in.readString();
        area_location = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(display_name);
        dest.writeString(latitu);
        dest.writeString(longitu);
        dest.writeString(address);
        dest.writeString(serve_type);
        dest.writeString(serve_brand);
        dest.writeString(price);
        dest.writeString(merch_mobile);
        dest.writeString(timings);
        dest.writeString(likes);
        dest.writeString(area_location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<carmechantlist> CREATOR = new Creator<carmechantlist>() {
        @Override
        public carmechantlist createFromParcel(Parcel in) {
            return new carmechantlist(in);
        }

        @Override
        public carmechantlist[] newArray(int size) {
            return new carmechantlist[size];
        }
    };
}
