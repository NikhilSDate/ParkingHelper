package app.cubing.myapplication;

import com.google.android.gms.maps.model.LatLng;

public class Utils {
    public static final int RADIUS=6371000;

    public static ParkingLot getClickedLot(LatLng latLng){
        ParkingLot finalLot=null;
        for(ParkingLot lot:DataHelper.getSingletonInstance().getParkingSpacesList()){
            if(getDistance(latLng,lot.getLocation())<=100){
                finalLot=lot;
            }
        }
        return finalLot;
    }
    public static double getDistance(LatLng firstPoint,LatLng secondPoint){
        int radius=6371000;
        double convesionFactor=Math.PI/180;
        double lat1=firstPoint.latitude*convesionFactor;
        double lon1=firstPoint.longitude*convesionFactor;
        double lat2=secondPoint.latitude*convesionFactor;
        double lon2=secondPoint.longitude*convesionFactor;
        double x=(lon2-lon1)*Math.cos((lat1+lat2)/2);
        double y=lat1-lat2;
        double distance=RADIUS*Math.sqrt(x*x+y*y);
        return distance;

    }
}

