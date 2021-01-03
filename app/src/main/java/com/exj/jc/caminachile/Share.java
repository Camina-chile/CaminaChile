package com.exj.jc.caminachile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Share extends AppCompatActivity implements View.OnClickListener {

    TextView textView;

    ImageView facebook, twitter, instagram, gmail;

    Button comparte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        //ocultar Action Bar
        getSupportActionBar().hide();

        textView=(TextView)findViewById(R.id.action_we);

        facebook= findViewById(R.id.facebook);
        twitter= findViewById(R.id.twitter);
        instagram= findViewById(R.id.instagram);
        gmail= findViewById(R.id.gmail);

        comparte=findViewById(R.id.comparte);
        comparte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCompartir = new Intent(Intent.ACTION_SEND);
                intentCompartir.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=com.aveschile.jc.recycleraves&hl=es";
                String shareSub = "Prueba nuestra aplicación...";
                intentCompartir.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intentCompartir.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(intentCompartir, "Compartir usando"));
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.facebook: //id de ImageView.
                //realiza operación al dar clic al imageView.
                Intent facebookAppIntent;
                try {
                    facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1493126924111972"));
                    startActivity(facebookAppIntent);
                } catch (ActivityNotFoundException e) {
                    facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/caminachileapp"));
                    startActivity(facebookAppIntent);
                }
                break;
            case R.id.twitter:
                String tweetUrl = "https://twitter.com/CaminachileApp";
                Uri uri = Uri.parse(tweetUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;

            case R.id.instagram:
                String insta = "https://www.instagram.com/caminachile/";
                Uri uriInsta = Uri.parse(insta);
                startActivity(new Intent(Intent.ACTION_VIEW, uriInsta));
                break;

            case R.id.gmail:
                String email = "caminachile.app@gmail.com";
                Intent intentCorreo = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                startActivity(intentCorreo);
            default:
                break;
        }
    }
}
