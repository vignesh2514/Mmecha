package com.motomecha.app.Global_classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vignesh2514 on 5/15/2017.
 */

public class Service_Tracking_List implements Parcelable{
    String id;
    String    user_uid;
    String   requested_address;
    String   code;
    String   regNumber;
    String   serviceType;
    String   pickup;
    String   scheduleDate;
    String    date_time;
    String    price;
    String    orderStatus;
    String    other_requirements;
    String    order_status_short;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getRequested_address() {
        return requested_address;
    }

    public void setRequested_address(String requested_address) {
        this.requested_address = requested_address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOther_requirements() {
        return other_requirements;
    }

    public void setOther_requirements(String other_requirements) {
        this.other_requirements = other_requirements;
    }

    public String getOrder_status_short() {
        return order_status_short;
    }

    public void setOrder_status_short(String order_status_short) {
        this.order_status_short = order_status_short;
    }

    public static Creator<Service_Tracking_List> getCREATOR() {
        return CREATOR;
    }

    protected Service_Tracking_List(Parcel in) {
        id = in.readString();
        user_uid = in.readString();
        requested_address = in.readString();
        code = in.readString();
        regNumber = in.readString();
        serviceType = in.readString();
        pickup = in.readString();
        scheduleDate = in.readString();
        date_time = in.readString();
        price = in.readString();
        orderStatus = in.readString();
        other_requirements = in.readString();
        order_status_short = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_uid);
        dest.writeString(requested_address);
        dest.writeString(code);
        dest.writeString(regNumber);
        dest.writeString(serviceType);
        dest.writeString(pickup);
        dest.writeString(scheduleDate);
        dest.writeString(date_time);
        dest.writeString(price);
        dest.writeString(orderStatus);
        dest.writeString(other_requirements);
        dest.writeString(order_status_short);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Service_Tracking_List> CREATOR = new Creator<Service_Tracking_List>() {
        @Override
        public Service_Tracking_List createFromParcel(Parcel in) {
            return new Service_Tracking_List(in);
        }

        @Override
        public Service_Tracking_List[] newArray(int size) {
            return new Service_Tracking_List[size];
        }
    };
}
