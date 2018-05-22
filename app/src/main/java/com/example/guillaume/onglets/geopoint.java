package com.example.guillaume.onglets;

import static java.lang.Math.sqrt;

/**
 * Created by Guillaume on 11/12/2017.
 */

public class geopoint {
    private double lat;
    private double lon;

    public geopoint (double lat, double lon){
        this.lat=lat;
        this.lon=lon;
    }
    public double distance (geopoint b){
        double latb = b.getLat();
        double lonb = b.getLon();
        return sqrt((this.lat-latb)*(this.lat-latb)+(this.lon-lonb)*(this.lon-lonb));
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return Double.toString(getLat())+Double.toString(getLon());
    }
}
