package com.pablo.agendacontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    Button guardar;
    Button consultar;
    Button eliminar;
    Button actualizar;
    EditText nombre;
    EditText telefono;
    EditText correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nombre = findViewById(R.id.editTextNombre);
        telefono = findViewById(R.id.editTextTelefono);
        correo = findViewById(R.id.editTextTextMail);
        guardar = findViewById(R.id.btnGuadar);
        consultar = findViewById(R.id.btnConsultar);
        eliminar = findViewById(R.id.btnEliminar);
        actualizar = findViewById(R.id.btnActualizar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "bbdd.db", null, 1);
                SQLiteDatabase sql = admin.getWritableDatabase();
                String strNombre = nombre.getText().toString();
                String strTelefono = telefono.getText().toString();
                String strMail = correo.getText().toString();
                ContentValues registro = new ContentValues();
                registro.put("Nombre", strNombre);
                registro.put("telefono", strTelefono);
                registro.put("email", strMail);
                sql.insert("contactos", null, registro);
                nombre.setText("");
                telefono.setText("");
                correo.setText("");
                Toast.makeText(MainActivity2.this,"Se han guardado los datos", Toast.LENGTH_LONG).show();
            }
        });

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "bbdd.db", null, 1);
                SQLiteDatabase sql = admin.getReadableDatabase();
                String strNombre = nombre.getText().toString();
                Cursor cursor = sql.rawQuery("select nombre, telefono, email from contactos where nombre = '"+strNombre+"'",null);
                    if(cursor.moveToFirst()){
                        nombre.setText(cursor.getString(0));
                        telefono.setText(cursor.getString(1));
                        correo.setText(cursor.getString(2));
                    }else{
                        Toast.makeText(MainActivity2.this, "No se ha encontrado", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                    sql.close();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "bbdd.db", null, 1);
                SQLiteDatabase sql = admin.getWritableDatabase();
                String strNombre = nombre.getText().toString();
                int cantidadFilas = sql.delete("contactos","nombre = '"+strNombre+"'",null);
                if(cantidadFilas != 0){
                    nombre.setText("");
                    telefono.setText("");
                    correo.setText("");
                    Toast.makeText(MainActivity2.this, "Se ha eliminado " + cantidadFilas , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity2.this, "No existe el contacto" , Toast.LENGTH_SHORT).show();
                }
                sql.close();
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "bbdd.db", null, 1);
                SQLiteDatabase sql = admin.getWritableDatabase();
                String strNombre = nombre.getText().toString();
                ContentValues valores = new ContentValues();
                valores.put("nombre", nombre.getText().toString());
                valores.put("telefono", telefono.getText().toString());
                valores.put("email", correo.getText().toString());
                int filas = sql.update("contactos",valores,"nombre = '"+strNombre+"'",null);
                if(filas != 0){
                    nombre.setText("");
                    telefono.setText("");
                    correo.setText("");
                    Toast.makeText(MainActivity2.this, "Se han actualizado correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity2.this, "No existe ese campo nombre", Toast.LENGTH_SHORT).show();
                }
                sql.close();
            }
        });
    }
}