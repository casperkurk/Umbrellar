package com.casperk.android.umbrellar.models;

/**
 * Created by Caspernicus on 4/03/2018.
 */

public class City {

    private long id;
    private String name;
    private Coordinations coord;
    private String country;
    private long population;
}

class Coordinations {

    private float lat;
    private float lon;
}
