package app.cubing.myapplication;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

public class BESTParkingLot {
    public static final int DEPOT=0;
    public static final int TERMINAL=1;

    private String gisId;
    private String ward;
    private String name;
    private LatLng location;
    private String address;
    private int subType;
    private String operator;
    private int heavyVehicleCapacity;
    private int getHeavyVehicleNightCapacity;
    Bitmap icon;

    public BESTParkingLot(String gisId, String ward, String name, LatLng location, String address,
                          int subType, String operator, int heavyVehicleCapacity, int getHeavyVehicleNightCapacity, Bitmap icon) {
        this.gisId = gisId;
        this.ward = ward;
        this.name = name;
        this.location = location;
        this.address = address;
        this.subType = subType;
        this.operator = operator;
        this.heavyVehicleCapacity = heavyVehicleCapacity;
        this.getHeavyVehicleNightCapacity = getHeavyVehicleNightCapacity;
        this.icon=icon;
    }

    public String getGisId() {
        return gisId;
    }

    public String getWard() {
        return ward;
    }

    public String getName() {
        return name;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public int getSubType() {
        return subType;
    }

    public String getOperator() {
        return operator;
    }

    public int getHeavyVehicleCapacity() {
        return heavyVehicleCapacity;
    }

    public int getGetHeavyVehicleNightCapacity() {
        return getHeavyVehicleNightCapacity;
    }

    public Bitmap getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "BESTParkingLot{" +
                "gisId='" + gisId + '\'' +
                ", ward='" + ward + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", address='" + address + '\'' +
                ", subType=" + subType +
                ", operator='" + operator + '\'' +
                ", heavyVehicleCapacity=" + heavyVehicleCapacity +
                ", getHeavyVehicleNightCapacity=" + getHeavyVehicleNightCapacity +
                '}';
    }
}
