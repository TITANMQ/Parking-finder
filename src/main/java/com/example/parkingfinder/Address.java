/**
 *
 */
package com.example.parkingfinder;

public class Address {

    private String name;
    private String streetName1;
    private String streetName2;
    private String streetName3;
    private String town;
    private String county;
    private String postcode;


    public Address(String streetName1, String streetName2, String streetName3, String town,
                   String county, String postcode) {
        this.streetName1 = streetName1;
        this.streetName2 = streetName2;
        this.streetName3 = streetName3;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
    }


    public String getStreetName1() {
        return streetName1;
    }

    public String getStreetName2() {
        return streetName2;
    }

    public String getStreetName3() {
        return streetName3;
    }

    public String getTown() {
        return town;
    }

    public String getCounty() {
        return county;
    }

    public String getPostcode() {
        return postcode;
    }
}
