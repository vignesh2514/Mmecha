package com.motomecha.app.Global_classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vigneshs on 04/04/17.
 */

public class More_list_menu implements Parcelable {
 String more_title;
      String more_description;
       String more_expiry;
        String request_link;
     String more_image;

    public String getMore_title() {
        return more_title;
    }

    public void setMore_title(String more_title) {
        this.more_title = more_title;
    }

    public String getMore_description() {
        return more_description;
    }

    public void setMore_description(String more_description) {
        this.more_description = more_description;
    }
    public String getMore_expiry() {
        return more_expiry;
    }

    public void setMore_expiry(String more_expiry) {
        this.more_expiry = more_expiry;
    }

    public String getRequest_link() {
        return request_link;
    }

    public void setRequest_link(String request_link) {
        this.request_link = request_link;
    }

    public String getMore_image() {
        return more_image;
    }

    public void setMore_image(String more_image) {
        this.more_image = more_image;
    }

    public static Creator<More_list_menu> getCREATOR() {
        return CREATOR;
    }

    protected More_list_menu(Parcel in) {
        more_title = in.readString();
        more_description = in.readString();
        more_expiry = in.readString();
        request_link = in.readString();
        more_image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(more_title);
        dest.writeString(more_description);
        dest.writeString(more_expiry);
        dest.writeString(request_link);
        dest.writeString(more_image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<More_list_menu> CREATOR = new Creator<More_list_menu>() {
        @Override
        public More_list_menu createFromParcel(Parcel in) {
            return new More_list_menu(in);
        }

        @Override
        public More_list_menu[] newArray(int size) {
            return new More_list_menu[size];
        }
    };
}
