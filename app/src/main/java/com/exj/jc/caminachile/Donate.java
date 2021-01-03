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

public class Donate extends AppCompatActivity implements View.OnClickListener {

    TextView textView;

    ImageView facebook, twitter, instagram, gmail;

    Button donate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        //ocultar Action Bar
        getSupportActionBar().hide();

        donate=findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme("https").authority("www.paypal.com").path("cgi-bin/webscr");
                uriBuilder.appendQueryParameter("cmd", "_donations");

                uriBuilder.appendQueryParameter("business", "renga.valpo@gmail.com");
                uriBuilder.appendQueryParameter("lc", "US");
                uriBuilder.appendQueryParameter("item_name", "CaminaChile");
                uriBuilder.appendQueryParameter("no_note", "1");
                uriBuilder.appendQueryParameter("no_shipping", "1");
                uriBuilder.appendQueryParameter("currency_code", "USD");
                Uri payPalUri = uriBuilder.build();

                Intent viewIntent = new Intent(Intent.ACTION_VIEW, payPalUri);
                startActivity(viewIntent);

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

        }
    }
}