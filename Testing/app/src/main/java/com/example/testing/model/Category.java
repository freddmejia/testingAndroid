package com.example.testing.model;

public class Category {
    int id;
    String nombre;
    String key;
    public Category(Integer id, String nombre, String key)
    {
        this.id = id;
        this.nombre = nombre;
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
