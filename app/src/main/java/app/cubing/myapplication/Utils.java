package app.cubing.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Utils {
    final  static boolean DEV_BUILD=true;

    public static final int EARTH_RADIUS = 6371000;
    public static final int WARNING_RADIUS = 500;

    public static final int WARNING_CIRCLE_FILL_BLUE=Color.parseColor("#50009dff");
    public static final int WARNING_CIRCLE_FILL_RED=Color.parseColor("#50ff4d4d");

    public static ParkingLot getClickedLot(LatLng latLng){
        ParkingLot finalLot=null;
        for(ParkingLot lot:DataHelper.getSingletonInstance().getParkingSpacesList()){
            if(getDistance(latLng,lot.getLocation())<=300){
                finalLot=lot;
            }
        }

        return finalLot;
    }
    public static BESTParkingLot getClickedBESTLot(LatLng location){
        BESTParkingLot finalLOT=null;
        for(BESTParkingLot bestParkingLot:DataHelper.getSingletonInstance().getBESTParkingSpacesList()){
            if(getDistance(location, bestParkingLot.getLocation())<50){
                finalLOT=bestParkingLot;
            }
        }
        return  finalLOT;
    }
    public static double  getDistance(LatLng firstPoint,LatLng secondPoint){
        double conversionFactor=Math.PI/180;
        double lat1=firstPoint.latitude*conversionFactor;
        double lon1=firstPoint.longitude*conversionFactor;
        double lat2=secondPoint.latitude*conversionFactor;
        double lon2=secondPoint.longitude*conversionFactor;
        double x=(lon2-lon1)*Math.cos((lat1+lat2)/2);
        double y=lat1-lat2;
        double distance= EARTH_RADIUS *Math.sqrt(x*x+y*y);
        return distance;

    }
    public static Bitmap getBitmapFromResource(int resource, Context context){
        Drawable drawable=context.getResources().getDrawable(resource,context.getTheme());
        Canvas canvas=new Canvas();
        Bitmap bitmap=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;

    }


    public static ParkingLot getNearestLot(LatLng location){
        ParkingLot nearestLot=DataHelper.getSingletonInstance().getParkingSpacesList().get(0);
        for(ParkingLot lot:DataHelper.getSingletonInstance().getParkingSpacesList()){
            if(getDistance(location,lot.getLocation())<getDistance(location,nearestLot.getLocation())){
                nearestLot=lot;
            }
        }
        return nearestLot;
    }
    public static Bitmap resizeBitmap(Bitmap originalBitmap, float factor){
        Bitmap resizedBitmap=Bitmap.createScaledBitmap(originalBitmap,(int)(originalBitmap.getWidth()*factor),(int)(originalBitmap.getHeight()*factor),true);
        return resizedBitmap;
    }
    public static boolean isInNoParking(LatLng latLng){
        boolean isInNoParking=false;
        for(ParkingLot lot:DataHelper.getSingletonInstance().getParkingSpacesList()) {
            if(getDistance(lot.getLocation(),latLng)<=WARNING_RADIUS){
                isInNoParking=true;
                break;
            }

        }
        return isInNoParking;

    }
    public static String getParkingCapacity(int capacity){
        String result;
        if(capacity==-1){
            result="ALLOWED";
        }else{
            result=Integer.toString(capacity);
        }
        return result;
    }

}

