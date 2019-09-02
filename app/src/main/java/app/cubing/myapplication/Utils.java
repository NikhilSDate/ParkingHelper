package app.cubing.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class Utils {
    public static final int RADIUS=6371000;

    public static ParkingLot getClickedLot(LatLng latLng){
        ParkingLot finalLot=null;
        for(ParkingLot lot:DataHelper.getSingletonInstance().getParkingSpacesList()){
            if(getDistance(latLng,lot.getLocation())<=400){
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
    public static Bitmap getBitmapFromResource(int resource, Context context){
        Drawable drawable=context.getResources().getDrawable(resource,context.getTheme());
        Canvas canvas=new Canvas();
        Bitmap bitmap=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;

    }
    public static boolean isAtLeastOneLotVisible(LatLngBounds bounds){
        boolean isAtLeastOneLotVisible=false;
        for(ParkingLot lot:DataHelper.getSingletonInstance().getParkingSpacesList()) {
            if(bounds.contains(lot.getLocation())){
                isAtLeastOneLotVisible=true;
            }

        }
        return isAtLeastOneLotVisible;
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
    public static ParkingLot isinNoParking(LatLng latLng){
        ParkingLot noParkingLot=null;
        for(ParkingLot lot:DataHelper.getSingletonInstance().getParkingSpacesList()) {
            if(getDistance(lot.getLocation(),latLng)<=500){
                noParkingLot=lot;
                break;
            }

        }
        return noParkingLot;

    }
}

