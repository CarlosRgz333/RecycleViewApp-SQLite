package com.example.recycleviewapp;

import android.app.Application;
import android.util.Log;
import java.util.ArrayList;

import modelo.AlumnoDbHelper;
import modelo.AlumnosDb;


public class Aplicacion extends Application {
    public static ArrayList<Alumno> alumnos;
    private MiAdaptador adaptador;
    private AlumnoDbHelper dbHelper = new AlumnoDbHelper(this);
    private AlumnosDb alumnosDb = new AlumnosDb(this, dbHelper);
    public static ArrayList<Alumno> getAlumnos(){
        return alumnos;
    }

    public MiAdaptador getAdaptador(){
        return adaptador;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        alumnosDb.openDataBase();
        alumnos = alumnosDb.allAlumnos();
        // alumnos = Alumno.llenarAlumnos();
        adaptador = new MiAdaptador(alumnos, this);
        Log.d("", "onCreate: tamaño array list " + alumnos.size());
        alumnosDb.closeDataBase();
    }
}