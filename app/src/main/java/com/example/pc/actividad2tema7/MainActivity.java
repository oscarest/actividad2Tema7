package com.example.pc.actividad2tema7;

import com.example.pc.actividad2tema7.DataBaseManager.DB_SQLite;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pc.actividad2tema7.DataBaseManager.Esquema.Producto;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private EditText txtNombre;
    private EditText txtCantidad;
    private Spinner lstSeccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        txtCantidad = findViewById(R.id.txtCantidad);
        lstSeccion = findViewById(R.id.lstSeccion);

        List secciones = Arrays.asList("Monitor", "DiscoDuro", "Memoria", "Teclado", "Ratón", "Impresora");
        lstSeccion.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, secciones));
    }

    public void insertarProducto(View view) {
        String strNombre = txtNombre.getText().toString();
        String strCantidad = txtCantidad.getText().toString();
        String strSeccion = lstSeccion.getSelectedItem().toString();

        if (strNombre.equals("") || strCantidad.equals("")) {
            mostrarMensaje("Todos los campos son obligatorios.");
        } else {

            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put(Producto.COLUMN_NAME_NOMBRE, strNombre);
            content.put(Producto.COLUMN_NAME_CANTIDAD, strCantidad);
            content.put(Producto.COLUMN_NAME_SECCION, strSeccion);
            conn.insert(Producto.TABLE_NAME, null, content);
            conn.close();

            mostrarMensaje("El producto " + strNombre + " ha sido insertado.");
            limpiarCuadrosTexto();
        }

    }

    public void eliminarProducto(View view) {
        String strNombre = txtNombre.getText().toString();

        if (strNombre.equals("")) {
            mostrarMensaje("Debe indicar el producto a eliminar");
        } else {
            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getWritableDatabase();
            String sqlWhere = Producto.COLUMN_NAME_NOMBRE + " LIKE '" + strNombre + "'";
            int count = conn.delete(Producto.TABLE_NAME, sqlWhere, null);
            conn.close();
            mostrarMensaje("Se ha eliminado el producto " + strNombre + ". Registros afectados: " + count);
            limpiarCuadrosTexto();
        }

    }

    public void modificarProducto(View view) {
        String strNombre = txtNombre.getText().toString();
        String strCantidad = txtCantidad.getText().toString();
        String strSeccion = lstSeccion.getSelectedItem().toString();

        if (strNombre.equals("") || strCantidad.equals("")) {
            mostrarMensaje("Todos los campos son obligatorios.");
        } else {
            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put(Producto.COLUMN_NAME_NOMBRE, strNombre);
            content.put(Producto.COLUMN_NAME_CANTIDAD, strCantidad);
            content.put(Producto.COLUMN_NAME_SECCION, strSeccion);
            String sqlWhere = Producto.COLUMN_NAME_NOMBRE + " LIKE '" + strNombre + "'";
            int count = conn.update(Producto.TABLE_NAME, content, sqlWhere, null);
            conn.close();
            mostrarMensaje("Se ha actualizado el producto " + strNombre + ". Registros afectados: " + count);
            limpiarCuadrosTexto();
        }

    }

    public void buscarProducto(View view) {
        String strNombre = txtNombre.getText().toString();

        if (strNombre.equals("")) {
            mostrarMensaje("Debe indicar el producto a buscar");

        } else {
            DB_SQLite db = new DB_SQLite(this);
            SQLiteDatabase conn = db.getReadableDatabase();

            String[] sqlFields = {Producto.COLUMN_NAME_ID, Producto.COLUMN_NAME_NOMBRE, Producto.COLUMN_NAME_CANTIDAD, Producto.COLUMN_NAME_SECCION};
            String sqlWhere = Producto.COLUMN_NAME_NOMBRE + " LIKE '" + txtNombre.getText().toString() + "'";

            Cursor cursor = conn.query(Producto.TABLE_NAME, sqlFields, sqlWhere, null, null, null, null);
            if (cursor.getCount() == 0) {
                mostrarMensaje("El producto " + strNombre + " no existe.");
            }else{
                cursor.moveToFirst();
                long dataIdProducto = cursor.getLong(cursor.getColumnIndex(Producto.COLUMN_NAME_ID));
                String dataNombre = cursor.getString(cursor.getColumnIndex(Producto.COLUMN_NAME_NOMBRE));
                Integer dataCantidad = cursor.getInt(cursor.getColumnIndex(Producto.COLUMN_NAME_CANTIDAD));
                String dataSeccion = cursor.getString(cursor.getColumnIndex(Producto.COLUMN_NAME_SECCION));
                mostrarMensaje("RECUPERADO: id:" + dataIdProducto + " nombre=" + dataNombre + " cantidad=" + dataCantidad + " seccion=" + dataSeccion);
                limpiarCuadrosTexto();
            }
            cursor.close();
            conn.close();
        }
    }

    public void listarProductos(View view) {
        TextView txtResultados = findViewById(R.id.txtResultados);
        txtResultados.setMovementMethod(new ScrollingMovementMethod());
        txtResultados.setText("");

        DB_SQLite db = new DB_SQLite(this);
        SQLiteDatabase conn = db.getReadableDatabase();

        String[] sqlFields = {Producto.COLUMN_NAME_ID, Producto.COLUMN_NAME_NOMBRE, Producto.COLUMN_NAME_CANTIDAD, Producto.COLUMN_NAME_SECCION};
        String sqlWhere = "";
        String sqlOrderBy = Producto.COLUMN_NAME_NOMBRE + " ASC";

        Cursor cursor = conn.query(Producto.TABLE_NAME, sqlFields, sqlWhere, null, null, null, sqlOrderBy);

        if (cursor.getCount() == 0) {
            mostrarMensaje("La base de datos está vacía.");
        } else {
            cursor.moveToFirst();
            String msg = "";
            do {
                long dataIdProducto = cursor.getLong(cursor.getColumnIndex(Producto.COLUMN_NAME_ID));
                String dataNombre = cursor.getString(cursor.getColumnIndex(Producto.COLUMN_NAME_NOMBRE));
                Integer dataCantidad = cursor.getInt(cursor.getColumnIndex(Producto.COLUMN_NAME_CANTIDAD));
                String dataSeccion = cursor.getString(cursor.getColumnIndex(Producto.COLUMN_NAME_SECCION));
                msg += "id:" + dataIdProducto + " nombre=" + dataNombre + " cantidad=" + dataCantidad + " seccion=" + dataSeccion  + "\n";
            } while (cursor.moveToNext());
            txtResultados.append(msg);
        }

        cursor.close();
        conn.close();
    }

    private void mostrarMensaje(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void limpiarCuadrosTexto() {
        txtNombre.setText("");
        txtCantidad.setText("");
    }

}
