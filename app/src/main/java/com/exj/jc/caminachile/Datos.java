package com.exj.jc.caminachile;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class Datos extends AppCompatActivity {

    private AnimationDrawable anim;
    private Button btnRegister;
    private EditText editText3;
    private EditText editText5;
    private EditText editText7;
    private EditText editText;
    private EditText editText21;
    private EditText editTextP;

    private String nombre = "";
    private String nacionalidad = "";
    private String fono = "";
    private String correo = "";
    private String dni_pasaporte = "";
    private String clave = "";
    private FloatingTextButton b_back;

    FirebaseAuth mAuth;
    DatabaseReference mDatBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos);

        init();
        anim = (AnimationDrawable) btnRegister.getBackground();
        anim.setEnterFadeDuration(2300);
        anim.setExitFadeDuration(2300);


        mAuth = FirebaseAuth.getInstance();
        mDatBase = FirebaseDatabase.getInstance().getReference();

        editText3 = (EditText) findViewById(R.id.editText3);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText7 = (EditText) findViewById(R.id.editText7);
        editText = (EditText) findViewById(R.id.editText);
        editText21 = (EditText) findViewById(R.id.editText21);
        editTextP  = (EditText) findViewById(R.id.editTextP);
        btnRegister = (Button)findViewById(R.id.btnRegister);




        final SQLite db = new SQLite(getApplicationContext());
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Context context = v.getContext();

                nombre = editText3.getText().toString();
                nacionalidad = editText5.getText().toString();
                fono = editText7.getText().toString();
                correo = editText.getText().toString();
                dni_pasaporte = editText21.getText().toString();
                clave = editTextP.getText().toString();

                if(nombre.equals("") || nacionalidad.equals("")
                        ||fono.equals("") || correo.equals("") || dni_pasaporte.equals("") || clave.equals("") ){

                    //Toast.makeText(context,"Complete los campos requeridos (*)", Toast.LENGTH_LONG).show();
                    new SweetAlertDialog(Datos.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Complete los campos requeridos!")
                            .show();
                }else{

                    if(clave.length() >=6){

                        registeruser();
                        db.AddUser(nombre,nacionalidad,fono,correo,dni_pasaporte,clave);
                        Intent btnCampeonato = new Intent(Datos.this, Inicio.class);
                        startActivity(btnCampeonato);
                        Toast.makeText(context,"Gracias por registrarte en Camina Chile", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context,"La clave debe contener al menos 6 caracteres", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        b_back = (FloatingTextButton) findViewById(R.id.btnBack);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent btnNumero = new Intent(Datos.this, Inicio.class);
                startActivity(btnNumero);
            }
        });
    }

    private void init() {
        this.btnRegister = findViewById( R.id.btnRegister);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(anim!=null && !anim.isRunning()){
            anim.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(anim!=null && anim.isRunning()){
            anim.stop();
        }
    }



    private void registeruser() {
        mAuth.createUserWithEmailAndPassword( correo,clave ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre",nombre);
                    map.put("nacionalidad",nacionalidad);
                    map.put("fono",fono);
                    map.put("correo",correo);
                    map.put("dni_pasaporte",dni_pasaporte);
                    map.put("clave",clave);
                    String id = mAuth.getCurrentUser().getUid();
                    mDatBase.child( "Users").child(id).setValue(map).addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){

                                Intent btnCampeonato = new Intent(Datos.this, Inicio.class);
                                startActivity(btnCampeonato);
                            }
                        }
                    } );
                }else{

                }
            }
        } );

    }
}
