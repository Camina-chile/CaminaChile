package com.exj.jc.caminachile;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class RestablecerPass extends AppCompatActivity  {
    private EditText mEditextCorreo;
    private Button mButtonResetPass,mButtonResetBack;
    private FloatingTextButton mBlack;
    private String email ="";
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    /*navigator menu*/
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private SwitchCompat darkModeSwitch;
    /*navigator menu*/
    private AnimationDrawable anim;
    private View view;
    private ImageView imageView2;
    private long animationDuration=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_restablecer_pass );

        init();
        anim = (AnimationDrawable) mButtonResetPass.getBackground();
        anim.setEnterFadeDuration(2300);
        anim.setExitFadeDuration(2300);


        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        mEditextCorreo = (EditText) findViewById( R.id.correoReset);
        mButtonResetPass = (Button) findViewById( R.id.btnResetPass);
        mButtonResetPass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEditextCorreo.getText().toString();
                if (!email.isEmpty()){
                    mDialog.setMessage("Espere mientras se prosesa la informacion");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }else{
                    new SweetAlertDialog(RestablecerPass.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("No has ingresado email!")
                            .setContentText("Favor ingresarlo!")
                            .show();
                }

            }
        } );

        //mButtonResetBack = (Button) findViewById( R.id.btnBack);
        mBlack = (FloatingTextButton) findViewById(R.id.btnBack);
        mBlack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnBack = new Intent(RestablecerPass.this, Inicio.class);
                btnBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(btnBack);

            }
        } );

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
        this.mButtonResetPass = findViewById( R.id.btnResetPass);

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


    private void resetPassword() {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
             if(task.isSuccessful()){
                 new SweetAlertDialog( RestablecerPass.this )
                         .setTitleText( "Se ha enviado un correo a tu bandeja para restablecer tu clave" )
                         .show();


             }else{
                 new SweetAlertDialog(RestablecerPass.this, SweetAlertDialog.ERROR_TYPE)
                         .setTitleText("No se ha podido enviar correo!")
                         .setContentText("Favor intenta nuevamente!")
                         .show();
                 }

                mDialog.dismiss();
                //finish();
             }
        } );
    }


}
