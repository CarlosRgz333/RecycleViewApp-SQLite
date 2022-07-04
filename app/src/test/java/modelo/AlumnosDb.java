package modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.recycleviewapp.Alumno;

import java.util.ArrayList;

public class AlumnosDb implements Persistencia, Proyeccion{
    private Context context;
    private AlumnoDbHelper helper;
    private SQLiteDatabase db;

    public AlumnosDb(Context context, AlumnoDbHelper helper){
        this.context = context;
        this.helper = helper;
    }

    public AlumnosDb(Context context){
        this.context = context;
        this.helper = new AlumnoDbHelper(this.context);
    }

    @Override
    public void openDataBase(){
        db = helper.getWritableDatabase();
    }

    @Override
    public void closeDataBase(){
        helper.close();
    }

    @Override
    public long insertAlumno(Alumno alumno){
        ContentValues values = new ContentValues();
        values.put(DefineTabla.Alumnos.COLUMN_NAME_MATRICULA, alumno.getMatricula());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_NOMBRE, alumno.getNombre());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_CARRERA, alumno.getGrado());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_FOTO, alumno.getImg());
        this.openDataBase();
        long num = db.insert(DefineTabla.Alumnos.TABLE_NAME, null, values);
        this.closeDataBase();
        Log.d("agregar", "insertAlumno: " + num);
        return num;
    }

    @Override
    public long updateAlumno(Alumno alumno){
        ContentValues values = new ContentValues();
        values.put(DefineTabla.Alumnos.COLUMN_NAME_MATRICULA, alumno.getMatricula());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_NOMBRE, alumno.getNombre());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_CARRERA, alumno.getGrado());
        values.put(DefineTabla.Alumnos.COLUMN_NAME_FOTO, alumno.getImg());
        this.openDataBase();
        long num = db.update(DefineTabla.Alumnos.TABLE_NAME, values,
                DefineTabla.Alumnos.COLUMN_NAME_ID +" = " + alumno.getId(), null);
        this.closeDataBase();
        return num;
    }

    @Override
    public void deleteAlumno(int id){
        this.openDataBase();
        db.delete(DefineTabla.Alumnos.TABLE_NAME, DefineTabla.Alumnos.COLUMN_NAME_ID + "=?", new String[]{
                String.valueOf(id)
        });
        this.closeDataBase();
    }

    @Override
    public Alumno getAlumno(String matricula){
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(DefineTabla.Alumnos.TABLE_NAME,
                DefineTabla.Alumnos.REGISTRO, DefineTabla.Alumnos.COLUMN_NAME_ID + " = ?",
                new String[]{ matricula }, null, null, null);
        cursor.moveToFirst();
        Alumno alumno = readAlumno(cursor);
        return alumno;
    }

    @Override
    public ArrayList<Alumno> allAlumnos(){
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(DefineTabla.Alumnos.TABLE_NAME, DefineTabla.Alumnos.REGISTRO,
                null, null, null, null, null);
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Alumno alumno = readAlumno(cursor);
            alumnos.add(alumno);
            cursor.moveToNext();
        }
        cursor.close();
        return alumnos;
    }

    @Override
    public Alumno readAlumno(Cursor cursor){
        Alumno alumno = new Alumno();
        alumno.setId(cursor.getInt(0));
        alumno.setMatricula(cursor.getString(1));
        alumno.setNombre(cursor.getString(2));
        alumno.setGrado(cursor.getString(3));
        return alumno;
    }
}
