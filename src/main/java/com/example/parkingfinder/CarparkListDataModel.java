package com.example.parkingfinder;

public class CarparkListDataModel
{
    private String carParkName;
    private long numberOfSpaces;


    public CarparkListDataModel(String carParkName, long numberOfSpaces) {
        this.carParkName = carParkName;
        this.numberOfSpaces = numberOfSpaces;
    }

    public String getCarParkName() {
        return carParkName;
    }

    public long getNumberOfSpaces() {
        return numberOfSpaces;
    }
}
