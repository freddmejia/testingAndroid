package com.example.testing.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.testing.model.Category;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String CATEGORIES_TABLE_CREATE = "CREATE TABLE " +
            "categories(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, key_cat TEXT UNIQUE)";
    private static final String DB_NAME = "test.sqlite";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CATEGORIES_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL(CATEGORIES_TABLE_CREATE);
    }
    public boolean insert(String category,String key){
        boolean add_yn = false;
        long va = 0;
        ContentValues cv = new ContentValues();
        cv.put("nombre", category);
        cv.put("key_cat", key);
        va =  db.insert("categories", null, cv);
        add_yn = va >= 1? true:false;
        return add_yn;
    }

    public void delete(int id){
        String[] args = new String[]{String.valueOf(id)};
        db.delete("categories", "_id=?", args);
    }

    public ArrayList<Category> getCategories(){
        ArrayList<Category> lista=new ArrayList<Category>();
        Cursor c = db.rawQuery("select _id, nombre,key_cat from categories", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                String nombre = c.getString(c.getColumnIndex("nombre"));
                String key = c.getString(c.getColumnIndex("key_cat"));
                int id=c.getInt(c.getColumnIndex("_id"));
                Category com =new Category(id,nombre,key);
                lista.add(com);
            } while (c.moveToNext());
        }
        c.close();
        return lista;
    }

}
