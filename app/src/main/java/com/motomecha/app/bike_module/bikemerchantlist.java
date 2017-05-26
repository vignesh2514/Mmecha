package com.motomecha.app.bike_module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vignesh2514 on 5/10/2017.
 */

public class bikemerchantlist implements Parcelable {
    String id;
    String display_name;
    String latitu;
    String longitu;
    String address;
    String serve_type;
    String serve_brand;
    String price;
    String likes;
    String area_location;
    String service_description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getService_description() {
        return service_description;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public static Creator<bikemerchantlist> getCREATOR() {
        return CREATOR;
    }

    protected bikemerchantlist(Parcel in) {
        id = in.readString();
        display_name = in.readString();
        latitu = in.readString();
        longitu = in.readString();
        address = in.readString();
        serve_type = in.readString();
        serve_brand = in.readString();
        price = in.readString();
        likes = in.readString();
        area_location = in.readString();
        service_description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(display_name);
        dest.writeString(latitu);
        dest.writeString(longitu);
        dest.writeString(address);
        dest.writeString(serve_type);
        dest.writeString(serve_brand);
        dest.writeString(price);
        dest.writeString(likes);
        dest.writeString(area_location);
        dest.writeString(service_description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<bikemerchantlist> CREATOR = new Creator<bikemerchantlist>() {
        @Override
        public bikemerchantlist createFromParcel(Parcel in) {
            return new bikemerchantlist(in);
        }

        @Override
        public bikemerchantlist[] newArray(int size) {
            return new bikemerchantlist[size];
        }
    };
}
