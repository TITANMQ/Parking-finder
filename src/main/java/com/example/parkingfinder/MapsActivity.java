package com.example.parkingfinder;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ListIterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private ArrayList<Carpark> carparks;


    double earth_radius = 3960.0;
    double degrees_to_radians = Math.PI/180.0;
    double radians_to_degrees = 180.0/Math.PI;


    private Button searchBtn;
    private EditText radiusInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        carparks = new ArrayList<>();

        searchBtn =  findViewById(R.id.radiusSearchBtn);

        radiusInput = findViewById(R.id.radiusInput);




    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        carparks = loadMapJSON();
        final Context context = this.getBaseContext();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(radiusInput.getText() != null)
                {
                    mMap.clear();
                    updateClosestCarParks(Double.parseDouble(radiusInput.getText().toString()));
                }else{
                    Toast.makeText(context, "Enter a numberical value", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }


    private void updateClosestCarParks(final double radius){



        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {


                int found = 0;

                LatLng user = new LatLng(location.getLatitude(), location.getLongitude());


                LatLng leftPoint = new LatLng(location.getLatitude()+ changeInLat(radius) , location.getLongitude() - changeInLng(location.getLongitude() ,radius*2));
                LatLng rightPoint = new LatLng(location.getLatitude()- changeInLat(radius) , location.getLongitude() + changeInLng(location.getLongitude() ,radius*2));
                LatLng leftPoint2 = new LatLng(location.getLatitude()+ changeInLat(radius) , location.getLongitude() + changeInLng(location.getLongitude() ,radius*2));
                LatLng rightPoint2 = new LatLng(location.getLatitude()- changeInLat(radius) , location.getLongitude() - changeInLng(location.getLongitude() ,radius*2));
                //radius markers [testing]

//                PolylineOptions rectOptions = new PolylineOptions()
//                        .add(leftPoint2)
//                        .add(leftPoint)  // North of the previous point, but at the same longitude
//                        .add(rightPoint2)  // Same latitude, and 30km to the west
//                        .add(rightPoint)  // Same longitude, and 16km to the south
//                        .add(leftPoint2); // Closes the polyline.
//
//                // Get back the mutable Polyline
//                Polyline polyline = mMap.addPolyline(rectOptions);

//
//                CircleOptions circleOptions = new CircleOptions()
//                        .center(user)
//                        .radius(radius* 1609.34); // In meters
//
//                // Get back the mutable Circle
//                Circle circle = mMap.addCircle(circleOptions);

//                mMap.addMarker(new MarkerOptions().position(leftPoint).title("leftpoint"));
//                mMap.addMarker(new MarkerOptions().position(rightPoint).title("rightpoint"));
//                mMap.addMarker(new MarkerOptions().position(leftPoint2).title("leftpoint2"));
//                mMap.addMarker(new MarkerOptions().position(rightPoint2).title("rightpoint2"));

                //gets carparks within the mile radius

                for(Carpark carpark: carparks){

                    if(carpark.getLatLng().longitude > leftPoint.longitude && carpark.getLatLng().latitude < leftPoint.latitude)
                    {
                        if(carpark.getLatLng().longitude <rightPoint.longitude && carpark.getLatLng().latitude > rightPoint.latitude )
                        {
                            found++;
                            mMap.addMarker(new MarkerOptions().position(carpark.getLatLng())
                                    .title(carpark.getName())).setTag(carpark);


                        }
                    }
                }


                if(found == 0 )
                {
                    Toast.makeText(getBaseContext(), "No car parks found", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), found + " car parks found", Toast.LENGTH_LONG).show();
                }

            }


        });


    }

    /**
     * gets a JSON resource file
     * @param context context
     * @param rawId raw resource id
     * @return JSON object
     */
    public static Object readJsonFile(Context context, int rawId)
    {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                JSONParser parser = new JSONParser();
                Resources resources = context.getResources();
                InputStream inputStream = resources.openRawResource(rawId);

                return parser.parse(new InputStreamReader(inputStream));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        Log.v("JSON Error", "JSON file not found");
        return null;
    }
    private ArrayList<Carpark> loadMapJSON(){

        JSONArray json = (JSONArray)  readJsonFile(this.getBaseContext(), R.raw.carparks_uk);
        if(json != null) {
            Log.v("JSON ",json.toString());
            ListIterator iterator = json.listIterator();

            while(iterator.hasNext())
            {
                JSONObject carPark = (JSONObject) iterator.next();


                //adds all carparks to an array
                carparks.add(new Carpark(
                        (String) carPark.get("Car Park Name"),
                        new Address(
                                (String) carPark.get("Street 1"),
                                (String) carPark.get("Street 2"),
                                (String) carPark.get("Street 3"),
                                (String) carPark.get("Town"),
                                (String) carPark.get("County"),
                                (String) carPark.get("Postcode")),
                        (long) carPark.get("Number of Spaces"),
                        (long) carPark.get("Disabled Spaces"),
                        new LatLng( (double) carPark.get("Latitude"),
                                (double) carPark.get("Longitude")),
                        Long.valueOf(((String) carPark.get("Car Park Phone")).replaceAll("[-]|\\s", ""))
                ));

            }
        }else{
            Log.v("JSON", "Json has not loaded");
        }


        return carparks;
    }

    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {


    }


    private double changeInLat(double miles)
    {
        return (miles/earth_radius)*radians_to_degrees;

    }

    private double changeInLng(double lat, double miles)
    {
        double radius = earth_radius*Math.cos(lat*degrees_to_radians);
        return (miles/radius)*radians_to_degrees;

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Carpark carpark = (Carpark) marker.getTag();
        assert carpark != null;
        Toast.makeText(getBaseContext(), String.valueOf(carpark.getNumberOfSpaces()), Toast.LENGTH_LONG ).show();
        return true;
    }
}
