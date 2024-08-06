package com.example.demosqllite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "MyDatabaseHelper";
    private static final String DATABASE_NAME = "DICTIONARY_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_WORD = "WORD";
    private static final String ID_COLUMN = "id";
    private static final String WORD_COLUMN = "word";
    private static final String MEAN_COLUMN = "mean";
    private static final String CREATE_WORD_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS " + TABLE_WORD + " (" +
                    ID_COLUMN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    WORD_COLUMN + " TEXT NOT NULL," +
                    MEAN_COLUMN + " TEXT NOT NULL" + ")";
    private static MyDatabaseHelper sInstance;

    public static MyDatabaseHelper getInstance(Context context) {
        if (sInstance == null)
            sInstance = new MyDatabaseHelper(context.getApplicationContext());
        return sInstance;
    }

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_WORD_TABLE_SQL);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD);
        onCreate(db);
    }

    public boolean insertWorld(World world) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORD_COLUMN, world.getmWorld());
        values.put(MEAN_COLUMN, world.getmMean());
        long rowID = db.insert(TABLE_WORD, null, values);
        db.close();
        if (rowID != -1)
            return false;
        return true;
    }

    public World getWorld(String id) {
        SQLiteDatabase db = getReadableDatabase();
        World world = null;
        Cursor cursor = db.query(TABLE_WORD, new String[]{ID_COLUMN, WORD_COLUMN, MEAN_COLUMN},
                WORD_COLUMN + "= ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            world = new World(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
        }
        db.close();
        return world;
    }

    @SuppressLint("Range")
    public List<World> getAllWorld(){
        SQLiteDatabase db = getReadableDatabase();
        List<World> worlds = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_WORD;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null && cursor.moveToFirst()){
            do{
                worlds.add(new
                        World(cursor.getInt(cursor.getColumnIndex(ID_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(WORD_COLUMN)),
                        cursor.getString(cursor.getColumnIndex(MEAN_COLUMN))));
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return worlds;
    }

    public int getTotalWorld(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_WORD;
        Cursor cursor = db.rawQuery(sql, null);
        int total = cursor.getCount();
        cursor.close();
        db.close();
        return total;
    }

    public int updateWorld(World world){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put(WORD_COLUMN, world.getmWorld());
        value.put(MEAN_COLUMN, world.getmMean());

        int rowEffect = db.update(TABLE_WORD, value, ID_COLUMN + " = ?",
                new String[]{String.valueOf(world.getmID())});
        db.close();
        return rowEffect;  // trả về số lượng hàng đã được cập nhật
    }

    public int deleteAllWorld(){
        SQLiteDatabase db = getReadableDatabase();
        int rowEffect = db.delete(TABLE_WORD, null, null);
        db.close();
        return rowEffect;
    }

    public int deleteWorld(World word) {
        SQLiteDatabase db = getReadableDatabase();
        int rowEffect = db.delete(TABLE_WORD, ID_COLUMN + " = ?", new
                String[]{String.valueOf(word.getmID())});
        db.close();
        return rowEffect;
    }
}




