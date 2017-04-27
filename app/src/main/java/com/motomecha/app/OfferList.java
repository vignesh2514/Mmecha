package com.motomecha.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vigneshs on 04/04/17.
 */

public class OfferList implements Parcelable{
    String offer_image;
    String offer_title;
    String offer_desc;
    String offer_validity;
String offer_link;

    public String getOffer_image() {
        return offer_image;
    }

    public void setOffer_image(String offer_image) {
        this.offer_image = offer_image;
    }

    public String getOffer_title() {
        return offer_title;
    }

    public void setOffer_title(String offer_title) {
        this.offer_title = offer_title;
    }

    public String getOffer_desc() {
        return offer_desc;
    }

    public void setOffer_desc(String offer_desc) {
        this.offer_desc = offer_desc;
    }

    public String getOffer_validity() {
        return offer_validity;
    }

    public void setOffer_validity(String offer_validity) {
        this.offer_validity = offer_validity;
    }

    public String getOffer_link() {
        return offer_link;
    }

    public void setOffer_link(String offer_link) {
        this.offer_link = offer_link;
    }

    public static Creator<OfferList> getCREATOR() {
        return CREATOR;
    }

    protected OfferList(Parcel in) {
        offer_image = in.readString();
        offer_title = in.readString();
        offer_desc = in.readString();
        offer_validity = in.readString();
        offer_link = in.readString();
    }

    public static final Creator<OfferList> CREATOR = new Creator<OfferList>() {
        @Override
        public OfferList createFromParcel(Parcel in) {
            return new OfferList(in);
        }

        @Override
        public OfferList[] newArray(int size) {
            return new OfferList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(offer_image);
        dest.writeString(offer_title);
        dest.writeString(offer_desc);
        dest.writeString(offer_validity);
        dest.writeString(offer_link);
    }
}
