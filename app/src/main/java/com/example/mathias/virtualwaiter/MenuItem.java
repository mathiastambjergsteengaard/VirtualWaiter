package com.example.mathias.virtualwaiter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mathias on 06/10/16.
 */
public class MenuItem implements Parcelable{
    public int Id;
    public float Price;
    public String Name;
    public int Tag;
    public boolean Chosen;
    public String RestaurantID;

    protected MenuItem(Parcel in) {
        Id = in.readInt();
        Price = in.readFloat();
        Name = in.readString();
        Tag = in.readInt();
        //http://stackoverflow.com/questions/6201311/how-to-read-write-a-boolean-when-implementing-the-parcelable-interface
        Chosen = in.readByte() != 0;
        RestaurantID = in.readString();
    }

    public MenuItem() {
        Id = 0;
        Price = 0;
        Name = "";
        Tag = 0;
        RestaurantID = "";
        Chosen = false;
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
        parcel.writeInt(Id);
        parcel.writeFloat(Price);
        parcel.writeString(Name);
        parcel.writeInt(Tag);
        //http://stackoverflow.com/questions/6201311/how-to-read-write-a-boolean-when-implementing-the-parcelable-interface
        parcel.writeByte((byte) (Chosen ? 1 : 0));
        parcel.writeString(RestaurantID);
    }
}




