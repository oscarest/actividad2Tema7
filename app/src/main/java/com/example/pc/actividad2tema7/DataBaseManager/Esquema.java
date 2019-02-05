package com.example.pc.actividad2tema7.DataBaseManager;

import android.provider.BaseColumns;

public class Esquema
{
    public Esquema()
    {
    }
    public static abstract class Producto implements BaseColumns
    {
        public static final String TABLE_NAME = "PRODUCTO";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_CANTIDAD = "cantidad";
        public static final String COLUMN_NAME_SECCION = "seccion";
        public static final String COLUMN_TYPE_ID = "INTEGER";
        public static final String COLUMN_TYPE_NOMBRE = "TEXT";
        public static final String COLUMN_TYPE_CANTIDAD = "INTEGER";
        public static final String COLUMN_TYPE_SECCION = "TEXT";
    }
}
