package app.cubing.myapplication;

import android.content.Context;
import android.location.Location;
import android.provider.ContactsContract;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DataHelper {
    private ArrayList<ParkingLot> parkingSpacesList=new ArrayList<ParkingLot>();
    private static DataHelper dataHelper=new DataHelper();
    public DataHelper(){}
    public static DataHelper getSingletonInstance(){
        return dataHelper;
    }
    public void loadData(Context context){
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
                int structureType=getStructureType(tokens[6]);
                int access=getAccessType(tokens[7]);
                String operator=tokens[8];
                boolean isFreeParking=Boolean.valueOf(tokens[9]);
                char priceCategory=tokens[10].charAt(0);
                int twoWheelerCapacity=Integer.valueOf(tokens[11]);
                int lightFourWheelerCapacity=Integer.valueOf(tokens[12]);
                int lightCommercialFourWheelerCapacity=Integer.valueOf(tokens[13]);
                int heavyFourWheelerCapacity=Integer.valueOf(tokens[14]);
                ParkingLot lot=new ParkingLot(gisId,ward,name,location,address,structureType,access,
                        operator,isFreeParking,priceCategory,twoWheelerCapacity,lightFourWheelerCapacity,
                        lightCommercialFourWheelerCapacity,heavyFourWheelerCapacity);
                parkingSpacesList.add(lot);

            }
        }catch (IOException ex){

        }

    }
    public ArrayList<ParkingLot> getParkingSpacesList(){
        return parkingSpacesList;

    }
    public int getStructureType(String structureType){
        if(structureType.equals("MULTISTOREY")){
            return ParkingLot.MULTISTOREY;
        }else if(structureType.equals("UNDERGROUND")){
            return ParkingLot.UNDERGROUND;
        }else{
            return -1;
        }

    }
    public int getAccessType(String accessType){
        if(accessType.equals("RAMP")){
            return ParkingLot.RAMP;
        }else if(accessType.equals("ELEVATOR")){
            return ParkingLot.ELEVATOR;
        }else{
            return -1;
        }

    }

}
