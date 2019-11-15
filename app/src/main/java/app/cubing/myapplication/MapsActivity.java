package app.cubing.myapplication;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
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
    BottomSheetBehavior bestBehavior;
    Marker currentMarker;
    MaterialButton checkButton;
    MaterialButton directionsButton;
    MaterialButton bestDirectionsButton;
    boolean isFirstLocationChange;
    ArrayList<Circle> circlesArray;
    ArrayList<Marker> parkingIconsArray;
    ArrayList<Marker> BESTParkingLocationsArray;
    ImageView parkingAlert;
    FloatingActionButton infoButton;
    boolean showBusLots;
    SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ConstraintLayout bottomSheet=findViewById(R.id.bottom_sheet_layout);
        ConstraintLayout bestBottomSheet=findViewById(R.id.best_bottom_sheet_layout);
        behavior=BottomSheetBehavior.from(bottomSheet);
        bestBehavior=BottomSheetBehavior.from(bestBottomSheet);

        checkButton=findViewById(R.id.check_button);
        directionsButton=findViewById(R.id.directions_button);
        bestDirectionsButton=findViewById(R.id.best_directions_button);
        infoButton=findViewById(R.id.info_button);
        parkingAlert=findViewById(R.id.parking_alert);
        parkingAlert.setVisibility(View.GONE);
        behavior.setPeekHeight(0);
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bestBehavior.setPeekHeight(0);
        bestBehavior.setHideable(true);
        bestBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        isFirstLocationChange=true;
        circlesArray=new ArrayList<>();
        parkingIconsArray=new ArrayList<>();
        BESTParkingLocationsArray=new ArrayList<>();
        showBusLots=getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).getBoolean("showBusLots", true);

        Log.i("TAG",DataHelper.getSingletonInstance().getParkingSpacesList().toString());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
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
        Log.i("TAG", "MAP READY");
        map = googleMap;
        updateCircles(new LatLng(currentLat, currentLon));
        showBestLots();

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final BESTParkingLot lot = Utils.getClickedBESTLot(marker.getPosition());
                final ParkingLot parkingLot;
                if (lot != null) {
                    Log.i("TAG", lot.toString());
                    showBestBottomSheet(lot);
                    bestDirectionsButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse("google.navigation:q=" + String.valueOf(lot.getLocation().latitude) + "," + String.valueOf(lot.getLocation().longitude));
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setPackage("com.google.android.apps.maps");
                            startActivity(intent);
                        }
                    });
                } else if ((parkingLot = Utils.getClickedLot(marker.getPosition())) != null) {
                    showBottomSheet(parkingLot);
                    directionsButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri mapIntentUri = Uri.parse("google.navigation:q=" + parkingLot.getLocation().latitude + "," + parkingLot.getLocation().longitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);

                        }
                    });
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                return true;


            }
        });
        map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                final ParkingLot lot = Utils.getClickedLot(circle.getCenter());
                if (lot != null) {
                    showBottomSheet(lot);
                    directionsButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri mapIntentUri = Uri.parse("google.navigation:q=" + lot.getLocation().latitude + "," + lot.getLocation().longitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);

                        }
                    });

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
    protected void onResume() {
        super.onResume();
        showBusLots=getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).getBoolean("showBusLots", true);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Restart the application?");
            builder.setMessage("Enabling location-based features requires a restart of the application");
            builder.setPositiveButton("RESTART", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which ){
                    Intent i = new Intent(MapsActivity.this, SplashActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                }

            });
            AlertDialog dialog=builder.create();
            dialog.show();


        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},0);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Restart the application?");
            builder.setMessage("Enabling location-based features requires a restart of the application");
            builder.setPositiveButton("RESTART", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which ){
                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                }

            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }

    }
    public void updateCircles(LatLng currentLocation) {

        for (Circle c : circlesArray) {
            if (c != null) {
                c.remove();
            }
        }
        for (Marker m : parkingIconsArray) {
            if (m != null) {
                m.remove();
            }
        }

        for (ParkingLot lot : DataHelper.getSingletonInstance().getParkingSpacesList()) {
            if (Utils.getDistance(lot.getLocation(), currentLocation) <= 500) {
                Circle circle = map.addCircle(new CircleOptions().center(lot.getLocation()).radius(500).
                        strokeWidth(0).strokeColor(Color.parseColor("#50ff4d4d")).
                        fillColor(Color.parseColor("#50ff4d4d")));
                circlesArray.add(circle);
                circle.setClickable(true);

            } else {
                Circle circle = map.addCircle(new CircleOptions().center(lot.getLocation()).radius(500).
                        strokeWidth(0).strokeColor(Color.parseColor("#50ff4d4d")).
                        fillColor(Color.parseColor("#50009dff")));
                circlesArray.add(circle);
                circle.setClickable(true);
            }
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p_icon);
            Bitmap resizedBitmap = Utils.resizeBitmap(bitmap, 0.25f);
            Marker parkingIcon = map.addMarker(new MarkerOptions().position(lot.getLocation()).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)).anchor(0.5f, 0.5f));
            parkingIconsArray.add(parkingIcon);
        }


    }
    public void showBestLots(){
        for (Marker m : BESTParkingLocationsArray) {
            if(m!=null) {
                m.remove();
            }
        }
        if (showBusLots) {
            for (BESTParkingLot bestLot : DataHelper.getSingletonInstance().getBESTParkingSpacesList()) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bus_icon);
                Bitmap resizedBitmap = Utils.resizeBitmap(bitmap, 0.05f);
                Marker BESTParkingIcon = map.addMarker(new MarkerOptions().position(bestLot.getLocation()).icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)).anchor(0.5f, 0.5f));
                BESTParkingLocationsArray.add(BESTParkingIcon);
            }
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
    public void showBestBottomSheet(BESTParkingLot lot){
        TextView lotName=findViewById(R.id.best_lot_name);
        TextView lotAddress=findViewById(R.id.best_lot_address);
        TextView lot2wCapacity=findViewById(R.id.best_lot_2w_capacity);
        TextView lotLMVCapacity=findViewById(R.id.best_lot_lmv_capacity);
        TextView lotLCVCapacity=findViewById(R.id.best_lot_lcv_capacity);
        TextView lotHMVCapacity=findViewById(R.id.best_lot_hmv_capacity);
        TextView lotHMVNightCapacity=findViewById(R.id.best_lot_hmv_night_capacity);
        TextView lotType=findViewById(R.id.best_lot_type);
        TextView lotOperator=findViewById(R.id.best_lot_operator);

        lotName.setText(lot.getName());
        lotAddress.setText(lot.getAddress());
        lot2wCapacity.setText("2W: ALLOWED");
        lotLMVCapacity.setText("LMV: ALLOWED");
        lotLCVCapacity.setText("LCV: ALLOWED");
        lotHMVCapacity.setText("HMV: "+String.valueOf(lot.getHeavyVehicleCapacity()));
        lotHMVNightCapacity.setText("HMV NIGHT: "+String.valueOf(lot.getGetHeavyVehicleNightCapacity()));
        lotType.setText(DataHelper.getSubTypeInt(lot.getSubType()));
        lotOperator.setText(lot.getOperator());
        bestBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);



    }





}
