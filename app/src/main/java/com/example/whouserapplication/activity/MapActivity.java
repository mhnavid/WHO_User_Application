package com.example.whouserapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.whouserapplication.LocaleManager;
import com.example.whouserapplication.PermissionUtils;
import com.example.whouserapplication.R;
import com.example.whouserapplication.api.ApiClient;
import com.example.whouserapplication.api.ApiInterface;
import com.example.whouserapplication.model.Center;
import com.example.whouserapplication.model.CenterDetails;
import com.example.whouserapplication.model.CenterLocation;
import com.example.whouserapplication.model.CurrentLocation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMarkerClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    AutocompleteSupportFragment autocompleteFragment;
    private Marker locationMarker;
    private Button btnRefresh;

    private static ApiInterface apiInterface;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    int AUTOCOMPLETE_REQUEST_CODE = 1;

    private List<CenterDetails> centerDetailsList;
    private List<CenterLocation> centerLocationList;
    private List<Center> centerList;
    private CurrentLocation currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setTitle(R.string.app_name);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        getCenterDetailsList();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        btnRefresh = findViewById(R.id.btnRefresh);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key));
        }

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG));
        autocompleteFragment.setCountry("BD");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        place.getLatLng(), 16));
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i("error", "An error occurred: " + status);
            }
        });

        assert mapFragment != null;
        mapFragment.getMapAsync( this);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMarkerClickListener(this);
        enableMyLocation();

        moveMapToDeviceLocation();
    }

    private void markLocationOnMap() {
//        locationMarker = mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(23.752212, 90.390325))
//                .title("Center Name")
//                .snippet("This is my spot!"));
//        locationMarker = mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(23.773112, 90.415992))
//                .title("Police Plaza")
//                .snippet("Demo EPI Center"));

        if (centerLocationList != null){
            for (CenterLocation centerLocation : centerLocationList){
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(centerLocation.getLatitude(), centerLocation.getLongitude()))
                        .title(centerLocation.getCenterName()));

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            finish();
            startActivity(getIntent());
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            mMap.setMinZoomPreference(10);
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    private void moveMapToDeviceLocation() {

        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(location.getLatitude(),
                                        location.getLongitude()), 15));
                        currentLocation = new CurrentLocation(location.getLatitude(), location.getLongitude());

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
            for (Center center : centerList){
                if (center.getCenterName().equals(marker.getTitle())){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("center",  center);
                    Intent intent = new Intent(MapActivity.this, PlaceDetailsActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("currentLat", String.valueOf(currentLocation.getLatitude()));
                    intent.putExtra("currentLong", String.valueOf(currentLocation.getLongitude()));
                    startActivity(intent);
                }
            }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.local_english:
                setNewLocale(this, LocaleManager.ENGLISH);
                return true;
            case R.id.local_Bengali:
                setNewLocale(this, LocaleManager.BENGALI);
                Log.d("bengali",String.valueOf(LocaleManager.getLocale(getResources())));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }

    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void getCenterDetailsList(){
//        centerDetailsList = new ArrayList<>();
        centerLocationList = new ArrayList<>();
        centerList = new ArrayList<>();

        Call<List<CenterDetails>> call = apiInterface.getAllCenter();
        call.enqueue(new Callback<List<CenterDetails>>() {
            @Override
            public void onResponse(Call<List<CenterDetails>> call, Response<List<CenterDetails>> response) {
                if (response.isSuccessful()){
                    for (CenterDetails centerDetails : response.body()){
//                        Log.d("contacts", String.valueOf(centerDetails.getContacts().get(0).getContactMobileNumber()));

//                        __________________________ADDRESS CHECK_________________________________

                        String address, addressBn;
                        if (!centerDetails.getVcHouseName().equals("") && !centerDetails.getVcHouseNumber().equals("")
                                && !centerDetails.getVcRoadName().equals("")){
                            address = "("+centerDetails.getVcHouseName()+")"+centerDetails.getVcHouseNumber()+", "+centerDetails.getVcRoadName();
                        }
                        else if (centerDetails.getVcHouseName().equals("") && !centerDetails.getVcHouseNumber().equals("")
                                && !centerDetails.getVcRoadName().equals("")){
                            address = centerDetails.getVcHouseNumber()+", "+centerDetails.getVcRoadName();
                        }
                        else if (centerDetails.getVcHouseName().equals("") && centerDetails.getVcHouseNumber().equals("")
                                && !centerDetails.getVcRoadName().equals("")){
                            address = centerDetails.getVcRoadName();
                        }
                        else if (!centerDetails.getVcHouseName().equals("") && centerDetails.getVcHouseNumber().equals("")
                                && !centerDetails.getVcRoadName().equals("")){
                            address = "("+centerDetails.getVcHouseName()+")"+", "+centerDetails.getVcRoadName();
                        }
                        else {
                            address = centerDetails.getVcHouseNumber()+", "+centerDetails.getVcRoadName();
                        }
                        if (!centerDetails.getVcHouseNameBn().equals("") && !centerDetails.getVcHouseNumberBn().equals("")
                                && !centerDetails.getVcRoadNameBn().equals("")){
                            addressBn = "("+centerDetails.getVcHouseNameBn()+")"+centerDetails.getVcHouseNumberBn()+", "+centerDetails.getVcRoadNameBn();
                        }
                        else if (centerDetails.getVcHouseNameBn().equals("") && !centerDetails.getVcHouseNumberBn().equals("")
                                && !centerDetails.getVcRoadNameBn().equals("")){
                            addressBn = centerDetails.getVcHouseNumberBn()+", "+centerDetails.getVcRoadNameBn();
                        }
                        else if (centerDetails.getVcHouseNameBn().equals("") && centerDetails.getVcHouseNumberBn().equals("")
                                && !centerDetails.getVcRoadNameBn().equals("")){
                            addressBn = centerDetails.getVcRoadNameBn();
                        }
                        else if (!centerDetails.getVcHouseNameBn().equals("") && centerDetails.getVcHouseNumberBn().equals("")
                                && !centerDetails.getVcRoadNameBn().equals("")){
                            addressBn = "("+centerDetails.getVcHouseNameBn()+")"+", "+centerDetails.getVcRoadNameBn();
                        }
                        else {
                            addressBn = centerDetails.getVcHouseNumberBn()+", "+centerDetails.getVcRoadNameBn();
                        }

//                        _______________________VACCINATOR CONTACT NO CHECK______________________________
                        String vaccinatorContactNo, vaccinatorContactNoBn;
                        if (centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getVaccinatorMobileNumber().length() == 11){
                            vaccinatorContactNo = centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getVaccinatorMobileNumber();
                        } else{
                            vaccinatorContactNo = "0"+centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getVaccinatorMobileNumber();
                        }
                        if (centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getVaccinatorMobileNumberBn().length() == 11){
                            vaccinatorContactNoBn = centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getVaccinatorMobileNumberBn();
                        } else {
                            vaccinatorContactNoBn = "০"+centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getVaccinatorMobileNumberBn();
                        }

//                        _______________________CONTACT PERSON CONTACT__________________________
                        String contactPersonContactNo, contactPersonContactNoBn;
                        if (centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getContactMobileNumber().length() == 11){
                            contactPersonContactNo = centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getContactMobileNumber();
                        } else {
                            contactPersonContactNo = "0"+centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getContactMobileNumber();
                        }
                        if (centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getContactMobileNumberBn().length() == 11){
                            contactPersonContactNoBn = centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getContactMobileNumberBn();
                        } else {
                            contactPersonContactNoBn = "০"+centerDetails.getContacts().get(centerDetails.getContacts().size()-1).getContactMobileNumberBn();
                        }

                        centerList.add(new Center(
                                centerDetails.getId(),
                                centerDetails.getVcName(),
                                centerDetails.getVcNameBn(),
                                address,
                                addressBn,
                                vaccinatorContactNo,
                                vaccinatorContactNoBn,
                                contactPersonContactNo,
                                contactPersonContactNoBn,
                                centerDetails.getDays().get(centerDetails.getDays().size()-1).getNineToThree(),
                                centerDetails.getDays().get(centerDetails.getDays().size()-1).getFiveToSeven(),
                                centerDetails.getVcOrganized(),
                                centerDetails.getVcOrganizedBn(),
                                centerDetails.getVcLattitude(),
                                centerDetails.getVcLongitude()
                        ));
                        if (centerDetails.getVcLattitude() != 0 && centerDetails.getVcLongitude() != 0){
                            centerLocationList.add(new CenterLocation(
                                    centerDetails.getVcName(),
                                    centerDetails.getVcLattitude(),
                                    centerDetails.getVcLongitude()));
                        }
                    }
                    markLocationOnMap();
//                    Log.d("centerList", String.valueOf(centerList));
                }
            }

            @Override
            public void onFailure(Call<List<CenterDetails>> call, Throwable t) {
                Log.d("CenterList", "error");
            }
        });
    }
}
