package app.cubing.myapplication;

import android.content.Context;
import android.provider.ContactsContract;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class DataHelper {
    private ArrayList<ParkingLot> parkingSpacesMap=new ArrayList<ParkingLot>();
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
                String name=tokens[0];
                Location location=new Location(Double.valueOf(tokens[1]),Double.valueOf(tokens[2]));
                parkingSpacesMap.put(name,location);

            }
        }catch (IOException ex){

        }

    }
    public HashMap getParkingSpacesMap(){
        return parkingSpacesMap;

    }
}
