package app.cubing.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataHelper {
    private ArrayList<ParkingLot> parkingSpacesList=new ArrayList<ParkingLot>();
    private ArrayList<BESTParkingLot> BESTParkingSpacesList=new ArrayList<>();
    private static DataHelper dataHelper=new DataHelper();


    private DataHelper(){}

    public static DataHelper getSingletonInstance(){
        return dataHelper;
    }

    public void loadData(Context context) throws IOException {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.parking_locations);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bf = new BufferedReader(reader);
            String currentLine;
            while((currentLine=bf.readLine())!=null){
                String[] tokens=currentLine.split("~");
                String gisId=tokens[0];
                String ward=tokens[1];
                String name=tokens[2];
                LatLng location =new LatLng(Double.valueOf(tokens[3]),Double.valueOf(tokens[4]));
                String address=tokens[5];
                int structureType= getStructureTypeInt(tokens[6]);
                int access= getAccessTypeInt(tokens[7]);
                String operator=tokens[8];
                boolean isFreeParking=Boolean.valueOf(tokens[9]);
                char priceCategory=tokens[10].charAt(0);
                int twoWheelerCapacity=Integer.valueOf(tokens[11]);
                int lightFourWheelerCapacity=Integer.valueOf(tokens[12]);
                int lightCommercialFourWheelerCapacity=Integer.valueOf(tokens[13]);
                int heavyFourWheelerCapacity=Integer.valueOf(tokens[14]);
                Bitmap icon = Utils.resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.p_icon),0.25f);

                ParkingLot lot=new ParkingLot(gisId,ward,name,location,address,structureType,access,
                        operator,isFreeParking,priceCategory,twoWheelerCapacity,lightFourWheelerCapacity,
                        lightCommercialFourWheelerCapacity,heavyFourWheelerCapacity,icon);
                parkingSpacesList.add(lot);

            }
        }catch (IOException ex){
            throw ex;
        }
        try{
            InputStream inputStream=context.getResources().openRawResource(R.raw.best_parking_locations);
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line;
            while((line=bufferedReader.readLine())!=null){
                String[] tokens=line.split("~");
                String gisId=tokens[0];
                String ward=tokens[1];
                String name=tokens[2];
                LatLng location=new LatLng(Double.valueOf(tokens[3]), Double.valueOf(tokens[4]));
                String address=tokens[5];
                int subType = getSubTypeString(tokens[6]);
                String operator=tokens[7];
                int heavyVehicleCapacity=Integer.valueOf(tokens[8]);
                int heavyVehicleNightCapacity=Integer.valueOf(tokens[9]);
                Bitmap icon= Utils.resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bus_icon),0.05f);



                BESTParkingLot bestParkingLot=new BESTParkingLot(gisId, ward, name, location,
                        address, subType, operator, heavyVehicleCapacity, heavyVehicleNightCapacity,icon);
                BESTParkingSpacesList.add(bestParkingLot);
            }
        }catch(IOException ex){
            throw ex;
        }

    }
    public ArrayList<ParkingLot> getParkingSpacesList(){
        return parkingSpacesList;

    }
    public ArrayList<BESTParkingLot> getBESTParkingSpacesList(){
        return BESTParkingSpacesList;
    }
    public static int getStructureTypeInt(String structureType){
        if(structureType.equals("MULTI_STOREY")){
            return ParkingLot.MULTI_STOREY;
        }else if(structureType.equals("UNDERGROUND")){
            return ParkingLot.UNDERGROUND;
        }else{
            return -1;
        }

    }
    public static int getAccessTypeInt(String accessType){
        if(accessType.equals("RAMP")){
            return ParkingLot.RAMP;
        }else if(accessType.equals("ELEVATOR")){
            return ParkingLot.ELEVATOR;
        }else{
            return -1;
        }

    }
    public static String getStructureTypeString(int structureType){
        if(structureType==ParkingLot.MULTI_STOREY){
            return "Multistorey";
        }else if(structureType==ParkingLot.UNDERGROUND){
            return "Underground";
        }else{
            return "";
        }

    }
    public static String getAccessTypeString(int accessType){
        if(accessType==ParkingLot.RAMP){
            return "Ramp";
        }else if(accessType==ParkingLot.ELEVATOR){
            return "Elevator";
        }else{
            return "";
        }

    }
    public static String getIsFreeParkingString(boolean isFreeParking){
        if(isFreeParking){
            return "Free";
        }else{
            return "Paid";
        }
    }
    public static int getSubTypeString(String subType){
        if(subType.equals("DEPOT")){
            return BESTParkingLot.DEPOT;
        }else if(subType.equals("TERMINAL")){
            return BESTParkingLot.TERMINAL;
        }else{
            return -1;
        }
    }
    public static String getSubTypeInt(int subType){
        if(subType==BESTParkingLot.DEPOT){
            return "DEPOT";
        }else if(subType==BESTParkingLot.TERMINAL){
            return "TERMINAL";
        }else{
            return "";
        }
    }

}
