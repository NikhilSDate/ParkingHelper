package app.cubing.myapplication;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap map;
    double currentLat;
    double currentLon;
    BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ConstraintLayout bottomSheet=findViewById(R.id.bottom_sheet_layout);
        behavior=BottomSheetBehavior.from(bottomSheet);
        DataHelper.getSingletonInstance().loadData(this);
        Log.i("TAG",DataHelper.getSingletonInstance().getParkingSpacesList().toString());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LocationManager manager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getPermissions();

        try {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);

            Log.i("TAG","GOT LOCATION");
        }catch (SecurityException ex){
            Log.i("TAG","EXCEPTION");
            ex.printStackTrace();

        }
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
        drawCircles();
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                ParkingLot clickedLot=Utils.getClickedLot(latLng);
                showBottomSheet(clickedLot);
            }
        });


        // Add a marker in Sydney and move the camera


    }



    @Override
    public void onLocationChanged(Location location) {
        currentLat=location.getLatitude();
        currentLon=location.getLongitude();

        Log.i("TAG","lat:"+currentLat+"lon:"+currentLon);
        LatLng current = new LatLng(currentLat, currentLon);
        map.addMarker(new MarkerOptions().position(current).title("Marker in Sydney"));



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
    public void drawCircles() {
        for(ParkingLot lot:DataHelper.getSingletonInstance().getParkingSpacesList()){
            map.addCircle(new CircleOptions().center(lot.getLocation()).radius(500).
                    strokeWidth(0).strokeColor(Color.parseColor("#50ff4d4d")).
                    fillColor(Color.parseColor("#50ff4d4d")));
            map.addCircle(new CircleOptions().center(lot.getLocation()).radius(20).
                    strokeWidth(0).strokeColor(Color.parseColor("#ff4d4d")).
                    fillColor(Color.parseColor("#000000")));
        }
    }
    public void showBottomSheet(ParkingLot currentLot){
        TextView lotName=findViewById(R.id.lot_name);
        TextView lotAddress=findViewById(R.id.lot_address);
        TextView lotLatitude=findViewById(R.id.lot_latitude);
        TextView lotLongitude=findViewById(R.id.lot_longitude);
        TextView lot2wCapacity=findViewById(R.id.lot_2w_capacity);
        TextView lotLMVCapacity=findViewById(R.id.lot_lmv_capacity);
        TextView lotLCVCapacity=findViewById(R.id.lot_lcv_capacity);
        TextView lotHMVCapacity=findViewById(R.id.lot_hmv_capacity);
        TextView lotStructureType=findViewById(R.id.lot_structure_type);
        TextView lotAccessType=findViewById(R.id.lot_access_type);
        TextView lotPriceCategory=findViewById(R.id.lot_price_category);
        TextView lotFreeParking=findViewById(R.id.lot_free_parking);
        TextView lotWard=findViewById(R.id.lot_ward);
        TextView lotGisId=findViewById(R.id.lot_gis_id);
        TextView lotOperator=findViewById(R.id.lot_operator);

        lotName.setText(currentLot.getName());
        lotAddress.setText(currentLot.getAddress());
        lotLatitude.setText(String.valueOf(currentLot.getLocation().latitude));
        lotLongitude.setText(String.valueOf(currentLot.getLocation().longitude));
        lot2wCapacity.setText(String.valueOf(currentLot.getTwoWheelerCapacity()));
        lotLMVCapacity.setText(String.valueOf(currentLot.getLightFourWheelerCapacity()));
        lotLCVCapacity.setText(String.valueOf(currentLot.getLightCommercialFourWheelerCapacity()));
        lotHMVCapacity.setText(String.valueOf(currentLot.getHeavyVehicleCapacity()));
        lotStructureType.setText(DataHelper.getStructureTypeString(currentLot.getStructureType()));
        lotAccessType.setText(DataHelper.getAccessTypeString(currentLot.getAccessType()));
        lotPriceCategory.setText(currentLot.getPriceCategory());
        lotFreeParking.setText(DataHelper.getIsFreeParkingString(currentLot.isFreeParking()));
        lotWard.setText(currentLot.getWard());
        lotGisId.setText(currentLot.getGisId());
        lotOperator.setText(currentLot.getOperator());

        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


    }
}
