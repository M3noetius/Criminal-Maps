package com.example.criminal_maps.Classes;

import androidx.annotation.NonNull;

public class Category {
    private String name;
    private int id;

    public Category( int id, String name){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.id + " | " +this.name;
    }
}
