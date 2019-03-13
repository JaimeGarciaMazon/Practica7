package com.example.practica7;

import android.content.Context;

import com.example.practica7.Model.Lugar;

import java.util.ArrayList;
import java.util.List;

public class App
{
    public final static int INSERTAR = 1;
    public final static int EDITAR = 2;
    public final static int INFORMACION = 3;
    public static int SALIDAINFORMACION;
    public static int accion;
    public static Lugar lugarActivo;
    public static int categoriaSpinnerMapa;
    public static List<String> getListCategorias(Context context) {
        List<String> list = new ArrayList<String>();
        list.add(context.getResources().getString(R.string.categoria1));
        list.add(context.getResources().getString(R.string.categoria2));
        list.add(context.getResources().getString(R.string.categoria3));
        list.add(context.getResources().getString(R.string.categoria4));
        list.add(context.getResources().getString(R.string.categoria5));
        return list;
    }
}