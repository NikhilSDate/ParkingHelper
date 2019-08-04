package app.cubing.myapplication;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class ParkingLot {
    public static final int MULTISTOREY=0;
    public static final int UNDERGROUND=1;
    public static final int RAMP=3;
    public static final int ELEVATOR=4;

    private String gisId;
    private String ward;
    private String name;
    private LatLng location;
    private String address;
    private int structureType;
    private int accessType;
    private String operator;
    private boolean isFreeParking;
    private char priceCategory;
    private int twoWheelerCapacity;
    private int lightFourWheelerCapacity;
    private int lightCommercialFourWheelerCapacity;
    private int heavyFourWheelerCapacity;



    public ParkingLot(String gisId, String ward, String name, LatLng location, String address,
                      int structureType, int accessType, String operator, boolean isFreeParking, char priceCategory,
                      int twoWheelerCapacity, int lightFourWheelerCapacity, int lightCommercialFourWheelerCapacity
                              , int heavyFourWheelerCapacity) {
        this.gisId = gisId;
        this.ward = ward;
        this.name = name;
        this.location = location;
        this.address = address;
        this.structureType = structureType;
        this.accessType=accessType;
        this.operator = operator;
        this.isFreeParking = isFreeParking;
        this.priceCategory = priceCategory;
        this.twoWheelerCapacity = twoWheelerCapacity;
        this.lightFourWheelerCapacity = lightFourWheelerCapacity;
        this.lightCommercialFourWheelerCapacity=lightCommercialFourWheelerCapacity;
        this.heavyFourWheelerCapacity = heavyFourWheelerCapacity;
    }
    @Override
    public String toString() {
        return "ParkingLot{" +
                "gisId='" + gisId + '\'' +
                ", ward='" + ward + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", address='" + address + '\'' +
                ", structureType=" + structureType +
                ", accessType=" + accessType +
                ", operator='" + operator + '\'' +
                ", isFreeParking=" + isFreeParking +
                ", priceCategory=" + priceCategory +
                ", twoWheelerCapacity=" + twoWheelerCapacity +
                ", lightFourWheelerCapacity=" + lightFourWheelerCapacity +
                ", lightCommercialFourWheelerCapacity=" + lightCommercialFourWheelerCapacity +
                ", heavyFourWheelerCapacity=" + heavyFourWheelerCapacity +
                '}';
    }

    public static int getMULTISTOREY() {
        return MULTISTOREY;
    }

    public static int getUNDERGROUND() {
        return UNDERGROUND;
    }

    public static int getRAMP() {
        return RAMP;
    }

    public static int getELEVATOR() {
        return ELEVATOR;
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

    public int getStructureType() {
        return structureType;
    }

    public int getAccessType() {
        return accessType;
    }

    public String getOperator() {
        return operator;
    }

    public boolean isFreeParking() {
        return isFreeParking;
    }

    public char getPriceCategory() {
        return priceCategory;
    }

    public int getTwoWheelerCapacity() {
        return twoWheelerCapacity;
    }

    public int getLightFourWheelerCapacity() {
        return lightFourWheelerCapacity;
    }

    public int getLightCommercialFourWheelerCapacity() {
        return lightCommercialFourWheelerCapacity;
    }

    public int getHeavyFourWheelerCapacity() {
        return heavyFourWheelerCapacity;
    }
}
