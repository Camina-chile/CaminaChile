package com.exj.jc.caminachile;

import android.Manifest;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.view.View;

import org.w3c.dom.Text;

import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Inicio extends AppCompatActivity {

    private Button btnInicio,btnRegistro;
    private AnimationDrawable anim;

    private EditText usuario;
    private EditText contrasena;
    private EditText correo;

    private ImageView imageView2;
    TextView sal, textViewReg,textViewForgetpass;
    private Context mContext;
    public long mFoanId = -1;
    private long animationDuration=2000;

    private SharedPreferences prefs;
    private Switch switchRemember;

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private String usuario_login = "";
    private String contrasena_login = "";
    private String correo_login = "";
    private View view;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        init();
        anim = (AnimationDrawable) btnInicio.getBackground();
        anim.setEnterFadeDuration(2300);
        anim.setExitFadeDuration(2300);



        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);*/
        mAuth = FirebaseAuth.getInstance();

        checkPermission();
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        switchRemember = (Switch)findViewById(R.id.switchRemember);
        /*getData();*/
        //usuario = (EditText) findViewById(R.id.usuario);
        correo = (EditText) findViewById(R.id.correo);
        contrasena = (EditText) findViewById(R.id.contrasena);
       // btnInicio = (Button)findViewById(R.id.btnInicio);



        textViewReg = (TextView)findViewById(R.id.textViewReg);
        textViewReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(Inicio.this, Datos.class);
                startActivity(registro);
            }
        });

        textViewForgetpass = (TextView)findViewById(R.id.textViewFgpass);
        textViewForgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(Inicio.this, RestablecerPass.class);
                startActivity(registro);
            }
        });


        setCredentialsIfExists();
        imageView2=(ImageView)findViewById( R.id.imageView2 );
        handleAnimation(view);
    }

    private void handleAnimation(View view) {
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView2,"y", 100f);
        animatorY.setDuration(animationDuration);
        /*ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat( imageView2, View.ALPHA, 1.0f, 0.0f);
        alphaAnimation.setDuration( animationDuration );*/

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether( animatorY/*, alphaAnimation */);
        animatorSet.start();
    }



    private void init() {
        this.btnInicio = findViewById( R.id.btnInicio);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                //String usuario_login = usuario.getText().toString();
                //String contrasena_login = contrasena.getText().toString();
                SQLite db = new SQLite(getApplicationContext());

                int countRegion = db.getRegion();
                if(countRegion<=0){
                    db.AddRegion();
                }else if(countRegion>0){
                }
                int countProvincia = db.getProvincia();
                if(countProvincia<=0){
                    db.AddProvincia();
                }else if(countProvincia>0){
                }
                int countComuna = db.getComuna();
                if(countComuna<=0){
                    db.AddComuna();
                }else if(countComuna>0){
                }

                int countTipo_place = db.getPLace_type();
                if(countTipo_place<=0){
                    db.Add_type_market();
                }else if(countTipo_place>0){
                }
                /*db getusuario*/

                /*para validar localmente sin internet*/
                /*
                final String usuaNom = db.getUser(usuario_login);
                String getUs = usuaNom;

                final String usuaMail = db.getMail(correo_login);
                String getMa = usuaMail;

                final String usuaPass = db.getPass(contrasena_login);
                String getPass = usuaPass;

                if(usuario_login.equals("") && contrasena_login.equals("") || usuario_login.equals("") || contrasena_login.equals("")){
                    new SweetAlertDialog(Inicio.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("No has ingresado tus credenciales!")
                            .show();

                } else if (usuario_login.equals(getUs) && contrasena_login.equals(getPass) ){
                    Toast.makeText(context,"Bienvenido a Camina Chile!", Toast.LENGTH_LONG).show();

                    Intent btnNumero = new Intent(Inicio.this, Ubicacion.class);

                    btnNumero.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(btnNumero);

                    saveOnPreferences(usuario_login, contrasena_login);
                }else{
                    new SweetAlertDialog(Inicio.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Credenciales Incorrectas!")
                            .setContentText("Vuelve a intentarlo!")
                            .show();
                }
                */

                correo_login = correo.getText().toString();
                contrasena_login = contrasena.getText().toString();
                if(!correo_login.isEmpty()&& !contrasena_login.isEmpty()){
                    final ProgressBar pb = (ProgressBar) findViewById(R.id.pbLoading);
                    pb.setVisibility(ProgressBar.VISIBLE);
                    loginUser();
                }else if(correo_login.equals("") && contrasena_login.equals("") || correo_login.equals("") || contrasena_login.equals("")) {
                    final ProgressBar pb = (ProgressBar) findViewById(R.id.pbLoading);
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    new SweetAlertDialog(Inicio.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("No has ingresado tus credenciales!")
                            .show();
                }else{
                    final ProgressBar pb = (ProgressBar) findViewById(R.id.pbLoading);
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    new SweetAlertDialog(Inicio.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Credenciales Incorrectas!")
                            .setContentText("Vuelve a intentarlo!")
                            .show();
                }

            }

        });

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

    private void loginUser(){


      mAuth.signInWithEmailAndPassword( correo_login,contrasena_login).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){

                  Intent btnNumero = new Intent(Inicio.this, Ubicacion.class);
                  btnNumero.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  btnNumero.putExtra( "correo_login", correo_login );
                  btnNumero.putExtra( "contrasena_login", contrasena_login );
                  startActivity(btnNumero);
                  saveOnPreferences(correo_login, contrasena_login);



              }else{
                  new SweetAlertDialog(Inicio.this, SweetAlertDialog.ERROR_TYPE)
                          .setTitleText("No se pudo iniciar sesión!")
                          .setContentText("Valida tus credenciales!")
                          .show();
              }
          }
      } );
    }

    private void saveOnPreferences(String correo_login, String contrasena_login){
        if(switchRemember.isChecked()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("correo", correo_login);
            editor.putString("pass", contrasena_login);
            editor.apply();
        }
    }

    private void setCredentialsIfExists(){
        String user = getUsersPrefs();
        String pass = getPassPrefs();
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)){
            correo.setText(user);
            contrasena.setText(pass);
        }
    }

    private String getUsersPrefs(){
        return prefs.getString("correo","");
    }

    private String getPassPrefs(){
        return prefs.getString("pass","");
    }

    /*
    public void getData(){
        //String sql = "https://gist.githubusercontent.com/juanbrujo/0fd2f4d126b3ce5a95a7dd1f28b3d8dd/raw/83e6907641ed46fbad41484c6e69ba09afa56251/comunas-regiones.json";
          String sql = "https://gist.githubusercontent.com/rhernandog/7d055482f5cc803852a762de873bea62/raw/2bed9aed94ab644533b5e624a4e8f165a4650d48/regiones-provincias-comunas.json";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conn;
        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json = "";
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            json = response.toString();
            JSONArray jsonArr = null;
            jsonArr = new JSONArray(json);
            //jsonArray = new JSONArray(jSon.toString());
            final String[] items = new String[jsonArr.length()];
            // looping through All Contacts
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject c = jsonArr.getJSONObject((i));
                String strCode = c.getString("region");
                items[i] = c.getString("region");
            }
             //sal.setText(mensaje);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    private void checkPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            Toast.makeText(this, "Su version de sistema no es igual o superior a 6 " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

        } else {

            if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
                    &&(checkSelfPermission(ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)){
                return;
            }
            if((shouldShowRequestPermissionRationale( CAMERA ))||(shouldShowRequestPermissionRationale( WRITE_EXTERNAL_STORAGE ))||(shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION))){
               cargarDialogoRecomendacion();
            }else{
                requestPermissions( new String[]{CAMERA,WRITE_EXTERNAL_STORAGE,ACCESS_FINE_LOCATION},100 );
            }

            /*para la la ubicacion */
            /*
            int hasWriteContactsPermission = checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION);
            int hasWriteContactsPermission1 = checkSelfPermission( Manifest.permission.CAMERA);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED && hasWriteContactsPermission1 != PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                requestPermissions(new String[] {Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS1);
                Toast.makeText(this, "Requiere permisos ", Toast.LENGTH_LONG).show();
            }else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Los permisos ya estan otorgados ", Toast.LENGTH_LONG).show();
            }   */
        }

        return;
    }

    private  void cargarDialogoRecomendacion(){
        AlertDialog.Builder dialog= new AlertDialog.Builder( Inicio.this );
        dialog.setTitle( "Permisos Desactivados" );
        dialog.setMessage( "Debe aceptar los permisos para correcto funcionamianto de la app" );
        dialog.setPositiveButton( "Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
             requestPermissions( new String[]{CAMERA,WRITE_EXTERNAL_STORAGE,ACCESS_FINE_LOCATION},100 );
            }
        } );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Los permisos se otorgaron ! :-) " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Los permisos no estan otorgados ! :-( " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
