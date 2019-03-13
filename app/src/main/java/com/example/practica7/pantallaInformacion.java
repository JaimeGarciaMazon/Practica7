package com.example.practica7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practica7.DataBaseManager.DB_SQLite;
import com.example.practica7.DataBaseManager.Esquema;
import com.example.practica7.Logic.LogicLugar;

import java.util.List;

public class pantallaInformacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_informacion);
        TextView txt1 = findViewById(R.id.textView11);
        TextView txt2 = findViewById(R.id.textView12);
        TextView txt3 = findViewById(R.id.textView13);
        TextView txt4 = findViewById(R.id.textView14);
        TextView txt5 = findViewById(R.id.textView17);
        RatingBar rb = findViewById(R.id.ratingBar3);
        if(App.lugarActivo!=null)
        {
            txt2.setText(App.getListCategorias(this).get(App.lugarActivo.getCategoria()-1));
            txt1.setText(App.lugarActivo.getNombre());
            txt3.setText(App.lugarActivo.getLongitud().toString());
            txt4.setText(App.lugarActivo.getLatitud().toString());
            txt5.setText(App.lugarActivo.getComentarios());
            rb.setRating(App.lugarActivo.getValoracion());
        }
        else
        {

        }

    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuinformacion, menu);
        MenuBuilder m = (MenuBuilder) menu;
        m.setOptionalIconsVisible(true);
        return true;
    }
    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.opcion1:
                App.SALIDAINFORMACION=1;
                startActivity(new Intent(getApplicationContext(), pantallaNuevo.class));
                finish();
                break;
            case R.id.opcion2:
                confirmacion();
                break;
        }

        return false;
    }
    private void confirmacion()
    {
        new AlertDialog.Builder(this)
                .setTitle("Borrar datos")
                .setMessage("¿Está seguro de borrar los datos?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Borrar aquí todos estos datos
                        borrarBD();
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void borrarBD()
    {
        DB_SQLite db = new DB_SQLite(this);
        SQLiteDatabase conn = db.getWritableDatabase();
        String sqlWhere = Esquema.Lugar.COLUMN_NAME_ID + " LIKE '" + App.lugarActivo.getId() + "'";
        conn.delete(Esquema.Lugar.TABLE_NAME, sqlWhere, null);
        conn.close();
    }
}
