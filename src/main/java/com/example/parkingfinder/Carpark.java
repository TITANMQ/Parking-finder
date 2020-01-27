/**
 *
 */
package com.example.parkingfinder;

import com.google.android.gms.maps.model.LatLng;

public class Carpark {

    private String name;
    private Address address;
    private long numberOfSpaces;
    private long numberOfDisabledSpaces;
    private LatLng latLng;
    private long phoneNumber;


    public Carpark(String name, Address address, long numberOfSpaces, long numberOfDisabledSpaces, LatLng latLng, long phoneNumber) {
        this.name = name;
        this.address = address;
        this.numberOfSpaces = numberOfSpaces;
        this.numberOfDisabledSpaces = numberOfDisabledSpaces;
        this.latLng = latLng;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public long getNumberOfSpaces() {
        return numberOfSpaces;
    }

    public long getNumberOfDisabledSpaces() {
        return numberOfDisabledSpaces;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }
}
