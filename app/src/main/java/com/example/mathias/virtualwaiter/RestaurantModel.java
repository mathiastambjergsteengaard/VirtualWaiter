package com.example.mathias.virtualwaiter;

/**
 * Created by solveigdoan on 10/10/16.
 */
public class RestaurantModel {

    private int ID;
    private String Name;
    private int Distance;
    private int Visits;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public int getVisits() {
        return Visits;
    }

    public void setVisits(int visits) {
        Visits = visits;
    }
}
