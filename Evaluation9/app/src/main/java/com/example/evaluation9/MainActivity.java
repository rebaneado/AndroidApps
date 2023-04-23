package com.example.evaluation9;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolygonClickListener, GoogleMap.OnPolylineClickListener {

    String TAG = "Demo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        //TODO: goto https://console.cloud.google.com/ to setup the google maps sdk for this project
        //TODO: use the https://developers.google.com/maps/documentation/android-sdk/config to setup the google maps sdk in this project

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        ArrayList<LatLng> arrayList = DataServices.getPath();


        LatLng temp = null;
        Polyline polyline1;

           // temp = arrayList.get(i);
            //googleMap.addMarker(new MarkerOptions().position(temp).title("Marker for i variable: "+ i));

            polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .addAll(arrayList)
            );
            polyline1.setTag("A");

        googleMap.addMarker(new MarkerOptions().position(arrayList.get(0)).title("Begin"));
        googleMap.addMarker(new MarkerOptions().position(arrayList.get((arrayList.size() - 1))).title("End"));
        LatLng variable = arrayList.get(0);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(variable));


        Double x = arrayList.get(0).latitude;
        Double y = arrayList.get(0).longitude;

        Double x1 = arrayList.get((arrayList.size() - 1)).latitude;
        Double y1 = arrayList.get((arrayList.size() - 1)).longitude;


        LatLngBounds bounds = new LatLngBounds(
                new LatLng(x, y),
                new LatLng(x1, y1)
        );
        LatLng center = bounds.getCenter();

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(bounds.getCenter()));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());

       // googleMap.setLatLngBoundsForCameraTarget(center);


        //!below here is working, above i am creating a protocall to make the lines

//        LatLng temp = null;
//        Polyline polyline1;
//        for (int i = 0; i < arrayList.size(); i++) {
//
//            temp = arrayList.get(i);
//            googleMap.addMarker(new MarkerOptions().position(temp).title("Marker for i variable: "+ i));
//
//            polyline1 = googleMap.addPolyline(new PolylineOptions()
//                    .clickable(true)
//                    .add(temp));
//            polyline1.setTag("A");
//
//        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayList.get(0), 4));
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);

//        googleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        // [START_EXCLUDE silent]
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        // [END_EXCLUDE]
    }

    @Override
    public void onPolygonClick(@NonNull Polygon polygon) {

    }

    @Override
    public void onPolylineClick(@NonNull Polyline polyline) {

    }


    //TODO: The retrieved trip points should be plotted on the Google Map
    // using “Polyline” shape https://developers.google.com/maps/documentation/android-sdk/polygon-tutorial

    //TODO: The start and end points of the trip should be indicated with markers
    // https://developers.google.com/maps/documentation/android-sdk/map-with-marker

    //TODO: Also map should be auto zoomed to include all the trip points in the map’s bounding box.
    //Check CameraUpdateFactory class check: https://developers.google.com/android/reference/com/google/android/gms/maps/CameraUpdateFactory

}
