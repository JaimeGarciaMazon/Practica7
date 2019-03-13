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

/*
        Log.i("MyApp", "COLOR ROJO:"+ BitmapDescriptorFactory.HUE_RED);
        Log.i("MyApp", "COLOR AZURE:"+ BitmapDescriptorFactory.HUE_AZURE);
        Log.i("MyApp", "COLOR BLUE:"+ BitmapDescriptorFactory.HUE_BLUE)c;
        Log.i("MyApp", "COLOR CYAN:"+ BitmapDescriptorFactory.HUE_CYAN);
        Log.i("MyApp", "COLOR GREEN:"+ BitmapDescriptorFactory.HUE_GREEN);
*/

        //Log.i("MyApp", "COLOR 01:"+ R.color.clrMarcador01);
/*
        float colorMarcador[] = new float[6];
        colorMarcador[1] = 0.0f + getResources().getColor(R.color.clrMarcador01);
        colorMarcador[2] = 0.0f + getResources().getColor(R.color.clrMarcador02);
        colorMarcador[3] = 0.0f + getResources().getColor(R.color.clrMarcador03);
        colorMarcador[4] = 0.0f + getResources().getColor(R.color.clrMarcador04);
        colorMarcador[5] = 0.0f + getResources().getColor(R.color.clrMarcador05);

for(int i=1;i<5; i++) {
    Log.i("MyApp" , "Color: " + colorMarcador[i]);
}

*/
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
        //mMap.addMarker(new MarkerOptions().position(Sevilla).title("Ciudad de Sevilla").icon(BitmapDescriptorFactory.fromResource(R.drawable.mi_marcador)));
        //mMap.addMarker(new MarkerOptions().position(Sevilla).title("Ciudad de Sevilla").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        //mMap.addMarker(new MarkerOptions().position(Huelva).title("Ciudad de Huelva"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Sevilla));
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
       /* Integer clickCount = (Integer) marker.getTag();

        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this, marker.getTitle() + " ha sido pulsado " + clickCount + " veces.", Toast.LENGTH_SHORT).show();
        }
        */
        return false;
    }
}