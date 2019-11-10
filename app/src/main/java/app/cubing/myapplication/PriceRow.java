package app.cubing.myapplication;

import android.util.Log;

public class PriceRow {
    public static final int TIME_UPTO_1=0;
    public static final int TIME_1TO3=1;
    public static final int TIME_3TO6=2;
    public static final int TIME_6TO12=3;
    public static final int TIME_MORETHAN12=4;
    public static final int TIME_MONTHLY_DAY=5;
    public static final int TIME_MONTHLY_NIGHT=6;


    public static final int VEHICLE_34W=7;
    public static final int VEHICLE_2W=8;
    public static final int VEHICLE_TRUCK=9;
    public static final int VEHICLE_AUTOTAXI=10;
    public static final int VEHICLE_PT=11;


    char priceCategory;
    int timeCategory;
    int vehicle;
    int price;
    public PriceRow(char priceCategory, int timeCategory, int vehicle, int price){
        this.priceCategory =priceCategory;
        this.timeCategory=timeCategory;
        this.vehicle=vehicle;
        this.price=price;
    }

    public char getCategory() {
        return priceCategory;
    }

    public int getTimeCategory() {
        return timeCategory;
    }

    public int getVehicle() {
        return vehicle;
    }

    public int getPrice() {
        return price;
    }

    public int getTimeFromString(String categoryString){
        int timeCategory;
        if(categoryString.equals("Upto 1")){
            timeCategory=TIME_UPTO_1;
        }else if(categoryString.equals("1-3 hrs")){
            timeCategory=TIME_1TO3;
        }else if(categoryString.equals("3-6 hrs")){
            timeCategory=TIME_3TO6;
        }else if(categoryString.equals("6-12 hrs")){
            timeCategory=TIME_6TO12;
        }else if(categoryString.equals(">12 hrs")){
            timeCategory=TIME_MORETHAN12;
        }else if(categoryString.equals("Monthly (day)")){
            timeCategory=TIME_MONTHLY_DAY;
        }else if(categoryString.equals("Monthly (night)")){
            timeCategory=TIME_MONTHLY_NIGHT;
        }else{
            timeCategory=-1;
        }
        return timeCategory;
    }
    public int getVehicleTypeFromString(String vehicleString){
        int vehicleType;
        if(vehicleString.equals("3/4 W")){
            vehicleType=VEHICLE_34W;
        }else if(vehicleString.equals("2 W")){
            vehicleType=VEHICLE_2W;
        }else if(vehicleString.equals("Truck")){
            vehicleType=VEHICLE_TRUCK;
        }else if(vehicleString.equals("Auto/Taxi")){
            vehicleType=VEHICLE_AUTOTAXI;
        }else if(vehicleString.equals("PT")){
            vehicleType=VEHICLE_PT;
        }else{
            vehicleType=-1;
        }
        return vehicleType;
    }




}
