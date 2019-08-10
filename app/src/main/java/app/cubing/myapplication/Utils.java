package app.cubing.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

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
    public static Bitmap getBitmapFromResource(int resource, Context context){
        Drawable drawable=context.getResources().getDrawable(resource,context.getTheme());
        Canvas canvas=new Canvas();
        Bitmap bitmap=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;

    }
}

