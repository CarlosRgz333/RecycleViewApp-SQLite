package com.example.recycleviewapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.recycleviewapp.Aplicacion;

import modelo.AlumnoDbHelper;
import modelo.AlumnosDb;

public class AlumnoAlta extends AppCompatActivity {
    private Button btnGuardar, btnRegresar, btnBorrar;
    private Alumno alumno;
    private EditText txtNombre, txtMatricula, txtGrado;
    private ImageView imgAlumno;
    private TextView lblImagen, lblId;
    private String carrera = "Ing. Tec. Información";
    private int posicion;
    private Uri uri;
    private AlumnoDbHelper dbHelper = new AlumnoDbHelper(this);
    private AlumnosDb alumnoDb = new AlumnosDb(this, dbHelper);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_alta);


        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnGuardar = (Button) findViewById(R.id.btnSalir);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        txtMatricula = (EditText) findViewById(R.id.txtMatricula);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtGrado = (EditText) findViewById(R.id.txtGrado);
        imgAlumno = (ImageView) findViewById(R.id.imgAlumno);
        lblImagen = (TextView) findViewById(R.id.lblFoto);

        Bundle bundle = getIntent().getExtras();
        alumno = (Alumno) bundle.getSerializable("alumno");
        posicion = bundle.getInt("posicion");
        //If para habilitar o deshabilidatar el boton de borrar
        if(posicion == -1){
            btnBorrar.setEnabled(false);
        }
        //If para setear los datos
        if(posicion >= 0){
            txtMatricula.setText(alumno.getMatricula());
            txtNombre.setText(alumno.getNombre());
            txtGrado.setText(alumno.getGrado());
            imgAlumno.setImageURI(Uri.parse(alumno.getImg()));
            lblImagen.setText(alumno.getImg());
        }
        imgAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Imagen", Toast.LENGTH_SHORT).show();
                cargarImagen();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alumno == null || posicion == -1){
                    //Agregar u nuevo alumno
                    alumno = new Alumno();
                    alumno.setGrado(txtGrado.getText().toString());
                    alumno.setMatricula(txtMatricula.getText().toString());
                    alumno.setNombre(txtNombre.getText().toString());
                    alumno.setImg(lblImagen.getText().toString());

                    if(validar()){
                        alumnoDb.insertAlumno(alumno);
                        Aplicacion.alumnos.add(alumno);
                        setResult(Activity.RESULT_OK);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Falto capturar datos", Toast.LENGTH_SHORT).show();
                        txtMatricula.requestFocus();
                    }
                }
                if(posicion >= 0){
                    if(validar()){
                        alumno.setMatricula(txtMatricula.getText().toString());
                        alumno.setNombre(txtNombre.getText().toString());
                        alumno.setGrado(txtGrado.getText().toString());
                        alumno.setImg(lblImagen.getText().toString());

                        Aplicacion.alumnos.get(posicion).setMatricula(alumno.getMatricula());
                        Aplicacion.alumnos.get(posicion).setNombre(alumno.getNombre());
                        Aplicacion.alumnos.get(posicion).setGrado(alumno.getGrado());
                        Aplicacion.alumnos.get(posicion).setImg(alumno.getImg());
                        alumnoDb.updateAlumno(alumno);
                        Toast.makeText(getApplicationContext(), " se modifico con exito", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Falto capturar datos", Toast.LENGTH_SHORT).show();
                        txtMatricula.requestFocus();
                    }

                }
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        btnBorrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirmar = new AlertDialog.Builder(AlumnoAlta.this);
                confirmar.setTitle("Borrar");
                confirmar.setMessage("¿Seguro que quiere borrar este registro?");
                confirmar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(posicion >= 0){
                            Aplicacion.alumnos.remove(posicion);
                            alumnoDb.deleteAlumno(alumno.getId());
                            finish();
                        }
                    }
                });
                confirmar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                confirmar.show();
            }
        });
    }


    private boolean validar(){
        boolean exito = true;
        Log.d("nombre", "validar: "+ txtNombre.getText());
        if(txtNombre.getText().toString().equals("")) exito = false;
        if(txtMatricula.getText().toString().equals("")) exito = false;
        if(txtGrado.getText().toString().equals("")) exito = false;
        if(lblImagen.getText().toString().equals("Url Foto")) exito = false;

        return exito;
    }

    private void cargarImagen(){
        Intent intento = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intento.setType("image/");
        startActivityForResult(intento.createChooser(intento, "Seleccione la imagen"),200);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode == 200){
                Uri path = data.getData();

                if(null != path){
                    imgAlumno.setImageURI(path);
                    lblImagen.setText(path.toString());
                }
            }


        }
    }
}