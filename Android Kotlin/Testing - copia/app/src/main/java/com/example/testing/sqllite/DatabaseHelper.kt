package com.example.testing.sqllite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.testing.model.Category
import java.util.*

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val db: SQLiteDatabase
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CATEGORIES_TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS categories")
        db.execSQL(CATEGORIES_TABLE_CREATE)
    }

    fun insert(category: String?, key: String?): Boolean {
        var add_yn = false
        var va: Long = 0
        val cv = ContentValues()
        cv.put("nombre", category)
        cv.put("key_cat", key)
        va = db.insert("categories", null, cv)
        add_yn = if (va >= 1) true else false
        return add_yn
    }

    fun delete(id: Int) {
        val args = arrayOf(id.toString())
        db.delete("categories", "_id=?", args)
    }

    val categories: ArrayList<Category?>
        get() {
            val lista = ArrayList<Category?>()
            val c = db.rawQuery("select _id, nombre,key_cat from categories", null)
            if (c != null && c.count > 0) {
                c.moveToFirst()
                do {
                    val nombre = c.getString(c.getColumnIndex("nombre"))
                    val key = c.getString(c.getColumnIndex("key_cat"))
                    val id = c.getInt(c.getColumnIndex("_id"))
                    val com = Category(id, nombre, key)
                    lista.add(com)
                } while (c.moveToNext())
            }
            c!!.close()
            return lista
        }

    companion object {
        private const val CATEGORIES_TABLE_CREATE = "CREATE TABLE " +
                "categories(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, key_cat TEXT UNIQUE)"
        private const val DB_NAME = "test.sqlite"
        private const val DB_VERSION = 1
    }

    init {
        db = this.writableDatabase
    }
}