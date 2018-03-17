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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinations getCoord() {
        return coord;
    }

    public void setCoord(Coordinations coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}

class Coordinations {

    private float lat;
    private float lon;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
