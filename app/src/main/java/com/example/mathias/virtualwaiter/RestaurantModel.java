package com.example.mathias.virtualwaiter;

import java.io.Serializable;

/**
 * Created by solveigdoan on 10/10/16.
 */
public class RestaurantModel implements Serializable {

    private String ID;
    private String Name;
    private int Distance;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int distance) {
        Distance = distance;
    }

}
