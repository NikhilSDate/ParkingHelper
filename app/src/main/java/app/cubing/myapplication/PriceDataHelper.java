package app.cubing.myapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;



public class PriceDataHelper {
    private HashMap<PriceRow,Integer> priceTable=new HashMap<>();
    private static PriceDataHelper priceDataHelper=new PriceDataHelper();

    private PriceDataHelper(){

    }
    public static PriceDataHelper getSingletonInstance(){
        return priceDataHelper;
    }
    public HashMap getPriceTable(){
        return priceTable;
    }
    public void loadData(Context context){
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.price_table);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bf = new BufferedReader(reader);
            String line;
            while ((line = bf.readLine()) != null){
                String[] tokens=line.split("~");
                char priceCategory=tokens[0].charAt(0);
                int time=getTimeFromString(tokens[1]);
                int vehicle=getVehicleTypeFromString(tokens[2]);
                int price;
                if(tokens[3].equals("NA")){
                    price=PriceRow.PRICE_NA;
                }else {
                    price = Integer.parseInt(tokens[3]);
                }
                PriceRow row=new PriceRow(priceCategory,time,vehicle);
                priceTable.put(row,price);
            }
        }catch (IOException e){

        }
    }
    public static int getTimeFromString(String categoryString){
        int timeCategory;
        if(categoryString.equals("Upto 1")){
            timeCategory=PriceRow.TIME_UPTO_1;
        }else if(categoryString.equals("1-3 hrs")){
            timeCategory=PriceRow.TIME_1TO3;
        }else if(categoryString.equals("3-6 hrs")){
            timeCategory=PriceRow.TIME_3TO6;
        }else if(categoryString.equals("6-12 hrs")){
            timeCategory=PriceRow.TIME_6TO12;
        }else if(categoryString.equals(">12 hrs")){
            timeCategory=PriceRow.TIME_MORETHAN12;
        }else if(categoryString.equals("Monthly (day)")){
            timeCategory=PriceRow.TIME_MONTHLY_DAY;
        }else if(categoryString.equals("Monthly (night)")){
            timeCategory=PriceRow.TIME_MONTHLY_NIGHT;
        }else{
            timeCategory=-1;
        }
        return timeCategory;
    }
    public static int getVehicleTypeFromString(String vehicleString){
        int vehicleType;
        if(vehicleString.equals("3/4 W")){
            vehicleType=PriceRow.VEHICLE_34W;
        }else if(vehicleString.equals("2 W")){
            vehicleType=PriceRow.VEHICLE_2W;
        }else if(vehicleString.equals("Truck")){
            vehicleType=PriceRow.VEHICLE_TRUCK;
        }else if(vehicleString.equals("Auto/Taxi")){
            vehicleType=PriceRow.VEHICLE_AUTOTAXI;
        }else if(vehicleString.equals("PT")){
            vehicleType=PriceRow.VEHICLE_PT;


        }else{
            vehicleType=-1;
        }
        return vehicleType;
    }

}
