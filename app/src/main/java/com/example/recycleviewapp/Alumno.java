package com.example.recycleviewapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Alumno implements Serializable {
    private int id;
    private String carrera;
    private String nombre;
    private String img;
    private String matricula;

    public Alumno(String carrera, String nombre, String img, String matricula) {
        this.carrera = carrera;
        this.nombre = nombre;
        this.img = img;
        this.matricula = matricula;
    }

    public Alumno(){

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getGrado() {
        return carrera;
    }
    public void setGrado(String grado) {
        this.carrera = grado;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public static ArrayList<Alumno> llenarAlumnos() {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        String carrera = "Ing. Tec. Información";
        //"android.resource://com.example.recycleviewapp/" + R.drawable.a01
        alumnos.add(new Alumno(carrera, "PERAZA SÁINZ ÁNGEL ADRIÁN", "android.resource://com.example.recycleviewapp/" + R.drawable.a01, "2017030274"));
        alumnos.add(new Alumno(carrera, "GÓMEZ CAMARENA JOSÉ RICARDO", "android.resource://com.example.recycleviewapp/" + R.drawable.a02, "2017030432"));
        alumnos.add(new Alumno(carrera, "PEÑA SOLÍS DIEGO ARMANDO", "android.resource://com.example.recycleviewapp/" + R.drawable.a03, "2018030098"));
        alumnos.add(new Alumno(carrera, "GARCÍA SILVA ADRIÁN", "android.resource://com.example.recycleviewapp/" + R.drawable.a04, "2018030099"));
        alumnos.add(new Alumno(carrera, "GARCÍA RODRÍGUEZ JESÚS EFRAÍN", "android.resource://com.example.recycleviewapp/" + R.drawable.a05, "2018030103"));
        alumnos.add(new Alumno(carrera, "LIZÁRRAGA CAMACHO JESÚS ARMANDO", "android.resource://com.example.recycleviewapp/" + R.drawable.a06, "2018030108"));
        alumnos.add(new Alumno(carrera, "GARCÍA GÓMEZ JUAN ANTONIO", "android.resource://com.example.recycleviewapp/" + R.drawable.a07, "2018030119"));
        alumnos.add(new Alumno(carrera, "LIZÁRRAGA LUNA JUAN FRANCISCO", "android.resource://com.example.recycleviewapp/" + R.drawable.a08, "2018030121"));
        alumnos.add(new Alumno(carrera, "OSUNA HIGUERA ALEJANDRO", "android.resource://com.example.recycleviewapp/" + R.drawable.a09, "2018030133"));
        alumnos.add(new Alumno(carrera, "MARÍN GALINDO KIMBERLY VANESSA", "android.resource://com.example.recycleviewapp/" + R.drawable.a10, "2018030155"));

        return alumnos;
    }


}