package com.example.mathias.virtualwaiter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mathias on 06/10/16.
 */
public class MenuItem implements Parcelable{
    public float Price;
    public String Name;
    public int Tag;
    public int RestaurantID;

    protected MenuItem(Parcel in) {
        Price = in.readFloat();
        Name = in.readString();
        Tag = in.readInt();
        RestaurantID = in.readInt();
    }

    public MenuItem() {
        Price = 0;
        Name = "";
        Tag = 0;
        RestaurantID = 0;
    }


    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(Price);
        parcel.writeString(Name);
        parcel.writeInt(Tag);
        parcel.writeInt(RestaurantID);
    }
}




