package com.example.testing.model

class Category(var id: Int, var nombre: String, var key: String) {
    override fun toString(): String {
        return nombre
    }
}