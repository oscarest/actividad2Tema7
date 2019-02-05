package com.example.pc.actividad2tema7.DataBaseManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.pc.actividad2tema7.DataBaseManager.Esquema.Producto;
public class DB_SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tienda.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Producto.TABLE_NAME + " (" +
                    Producto.COLUMN_NAME_ID + " " + Producto.COLUMN_TYPE_ID + " PRIMARY KEY AUTOINCREMENT, " +
                    Producto.COLUMN_NAME_NOMBRE + " " + Producto.COLUMN_TYPE_NOMBRE + "," +
                    Producto.COLUMN_NAME_CANTIDAD + " " + Producto.COLUMN_TYPE_CANTIDAD +  "," +
                    Producto.COLUMN_NAME_SECCION + " " + Producto.COLUMN_TYPE_SECCION + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Producto.TABLE_NAME;

    public DB_SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
