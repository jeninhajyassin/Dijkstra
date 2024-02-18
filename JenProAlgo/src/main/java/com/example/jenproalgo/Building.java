package com.example.jenproalgo;

public class Building {

    private final String name;
    private final Double x;
    private final Double y;

    public Building(String name, Double x, Double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return this.name;
    }

    public Double getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return this.name;
    }

    //equals method
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Building.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Building other = (Building) obj;
        return this.name.equalsIgnoreCase(other.name);
    }



}
