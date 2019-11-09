package app.cubing.myapplication;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap map;
    double currentLat;
    double currentLon;
    BottomSheetBehavior behavior;
    Marker currentMarker;
    MaterialButton checkButton;
    MaterialButton directionsButton;
    boolean isFirstLocationChange;
    ArrayList<Circle> circlesArray;
    ImageView parkingAlert;
    FloatingActionButton infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ConstraintLayout bottomSheet=findViewById(R.id.bottom_sheet_layout);
        behavior=BottomSheetBehavior.from(bottomSheet);
        checkButton=findViewById(R.id.check_button);
        directionsButton=findViewById(R.id.directions_button);
        infoButton=findViewById(R.id.info_button);
        parkingAlert=findViewById(R.id.parking_alert);
        parkingAlert.setVisibility(View.GONE);
        checkButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e0da28")));
        behavior.setPeekHeight(0);
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        isFirstLocationChange=true;
        circlesArray=new ArrayList<>();
        Log.i("TAG",DataHelper.getSingletonInstance().getParkingSpacesList().toString());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getPermissions();
        

        LocationManager manager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

        try {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);

            Log.i("TAG","GOT LOCATION");
        }catch (SecurityException ex){
            Log.i("TAG","EXCEPTION");
            ex.printStackTrace();

        }
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLat,currentLon)));
               LatLngBounds.Builder builder=new LatLngBounds.Builder();
               LatLng nearestLotLocation=Utils.getNearestLot(new LatLng(currentLat,currentLon)).getLocation();
                LatLng center =map.getCameraPosition().target;
                LatLng lotLocationOpposite=new LatLng(2*center.latitude-nearestLotLocation.latitude,2*center.longitude-nearestLotLocation.longitude);
                builder.include(nearestLotLocation);
                builder.include(lotLocationOpposite);
                LatLngBounds mapBounds=builder.build();
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(mapBounds,10));
            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapsActivity.this, InfoActivity.class);
                startActivity(intent);

            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        updateCircles(new LatLng(currentLat, currentLon));
        map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                LatLng latLng=circle.getCenter();
                final ParkingLot clickedLot=Utils.getClickedLot(latLng);
                if(clickedLot!=null) {
                    showBottomSheet(clickedLot);
                    directionsButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri mapIntentUri=Uri.parse("google.navigation:q="+clickedLot.getLocation().latitude+","+clickedLot.getLocation().longitude);
                            Intent mapIntent=new Intent(Intent.ACTION_VIEW,mapIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);

                        }
                    });
                }else{
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }

            }
        });
    }






    @Override
    public void onLocationChanged(Location location) {
        currentLat=location.getLatitude();
        currentLon=location.getLongitude();
        if(currentMarker!=null) {
            currentMarker.remove();
        }
        updateCircles(new LatLng(currentLat,currentLon));
        Log.i("TAG","lat:"+currentLat+"lon:"+currentLon);
        LatLng current = new LatLng(currentLat, currentLon);
        Bitmap bitmap=Utils.getBitmapFromResource(R.drawable.location_dot,this);
        currentMarker=map.addMarker(new MarkerOptions().position(current).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
        if(isFirstLocationChange) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLon), 15));
            isFirstLocationChange=false;
        }
        if((Utils.isinNoParking(new LatLng(currentLat,currentLon)))){
            parkingAlert.setVisibility(View.VISIBLE);
            Toast.makeText(this, R.string.no_parking_message,Toast.LENGTH_LONG).show();


        }else{
            parkingAlert.setVisibility(View.GONE);
        }



    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void getPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);

        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},0);

        }

    }
    public void updateCircles(LatLng currentLocation) {
        for(Circle c:circlesArray){
            if(c!=null) {
                c.remove();
            }
        }
        for(ParkingLot lot:DataHelper.getSingletonInstance().getParkingSpacesList()){
            if(Utils.getDistance(lot.getLocation(),currentLocation)<=500) {
                Circle circle=map.addCircle(new CircleOptions().center(lot.getLocation()).radius(500).
                        strokeWidth(0).strokeColor(Color.parseColor("#50ff4d4d")).
                        fillColor(Color.parseColor("#50ff4d4d")));
                circlesArray.add(circle);
                circle.setClickable(true);

            }else{
                Circle circle=map.addCircle(new CircleOptions().center(lot.getLocation()).radius(500).
                        strokeWidth(0).strokeColor(Color.parseColor("#50ff4d4d")).
                        fillColor(Color.parseColor("#50009dff")));
                circlesArray.add(circle);
                circle.setClickable(true);
            }
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.p_icon);
            Bitmap resizedBitmap=Utils.resizeBitmap(bitmap,0.25f);
            map.addMarker(new MarkerOptions().position(lot.getLocation()).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)).anchor(0.5f,0.5f));
//            map.addCircle(new CircleOptions().center(lot.getLocation()).radius(16).fillColor(Color.parseColor("#ff4d4d")).strokeWidth(0));
        }
    }
    public void showBottomSheet(ParkingLot currentLot){
        TextView lotName=findViewById(R.id.lot_name);
        TextView lotAddress=findViewById(R.id.lot_address);
        TextView lot2wCapacity=findViewById(R.id.lot_2w_capacity);
        TextView lotLMVCapacity=findViewById(R.id.lot_lmv_capacity);
        TextView lotLCVCapacity=findViewById(R.id.lot_lcv_capacity);
        TextView lotHMVCapacity=findViewById(R.id.lot_hmv_capacity);
        TextView lotStructureType=findViewById(R.id.lot_structure_type);
        TextView lotAccessType=findViewById(R.id.lot_access_type);
        TextView lotOperator=findViewById(R.id.lot_operator);

        lotName.setText(currentLot.getName());
        lotAddress.setText(currentLot.getAddress());
        lot2wCapacity.setText("2W : " + Utils.getParkingCapacity(currentLot.getTwoWheelerCapacity()));
        lotLMVCapacity.setText("LMV : "+String.valueOf(currentLot.getLightFourWheelerCapacity()));
        lotLCVCapacity.setText("LCV : "+Utils.getParkingCapacity(currentLot.getLightCommercialFourWheelerCapacity()));
        lotHMVCapacity.setText("HMV : "+String.valueOf(currentLot.getHeavyVehicleCapacity()));
        lotStructureType.setText(DataHelper.getStructureTypeString(currentLot.getStructureType()));
        lotAccessType.setText(DataHelper.getAccessTypeString(currentLot.getAccessType()));
        lotOperator.setText(currentLot.getOperator());

        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


    }

}
