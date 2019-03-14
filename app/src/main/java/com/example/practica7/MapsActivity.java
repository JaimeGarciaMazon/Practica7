package com.example.practica7;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.practica7.DataBaseManager.DB_SQLite;
import com.example.practica7.Logic.LogicLugar;
import com.example.practica7.Model.Lugar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    LatLng nuevaPosicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void mostrarTodo() {

        float colorMarcador[] = {0.0f, 0.0f, 210.0f, 240.0f,180.0f, 120.0f};
        Log.i("MyApp" , "Color: " + App.categoriaSpinnerMapa);
        if(App.categoriaSpinnerMapa==0)
        {
            List<Lugar> lstLugar = LogicLugar.listaLugar(this);
            if (lstLugar == null)
            {

            }
            else {
                for (Lugar p : lstLugar) {
                    Log.i("MyApp", p.toString());
                    nuevaPosicion = new LatLng(p.getLatitud(), p.getLongitud());
                    mMap.addMarker(new MarkerOptions().position(nuevaPosicion).snippet("" +p.getLongitud() + "_" + p.getLatitud()).title(p.getNombre()).icon(BitmapDescriptorFactory.defaultMarker(colorMarcador[p.getCategoria()])));
                }
            }
        }
        else
        {
            List<Lugar> lstLugar = LogicLugar.listaLugar2(this, App.categoriaSpinnerMapa);
            if (lstLugar == null) {
            } else {
                for (Lugar p : lstLugar) {
                    nuevaPosicion = new LatLng(p.getLatitud(), p.getLongitud());
                    mMap.addMarker(new MarkerOptions().position(nuevaPosicion).snippet("" +p.getLongitud() + "_" + p.getLatitud()).title(p.getNombre()).icon(BitmapDescriptorFactory.defaultMarker(colorMarcador[p.getCategoria()])));
                }
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mostrarTodo();
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Log.i("MyApp", "Marcador:"+ marker.toString());
        Log.i("MyApp", "Marcador:"+ marker.getId());
        Log.i("MyApp", "Marcador:"+ marker.getSnippet());
        String cadena = marker.getSnippet();
        String[] separated = cadena.split("_");
        String longitud = separated[0];
        String latitud = separated[1];

        Log.i("MyaPP" , "Buscando Long: " + longitud + " Lat: " + latitud);
        Lugar l = LogicLugar.getLugar(this, latitud, longitud);
        if(l==null) {
            Log.i("MyaPP" , "Lugar NO ENCONTRADO");
        } else {
            App.lugarActivo =l;
            Log.i("MyaPP" , "Lugar obtenido: " + App.lugarActivo.toString());
        }


        startActivity(new Intent(getApplicationContext(), pantallaInformacion.class));

        return false;
    }
}