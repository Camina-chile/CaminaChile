package com.exj.jc.caminachile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource;

public class Region extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, AdapterView.OnItemSelectedListener, GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;
    private Spinner mMapTypeSelector;
    private SupportMapFragment fm;
    private int mMapTypes[] = {
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //selección tipo mapa
        Spinner map_type_selector = (Spinner) findViewById(R.id.map_type_selector);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.map_types_list, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        map_type_selector.setAdapter(adapter);

        mMapTypeSelector = (Spinner) findViewById(R.id.map_type_selector);
        mMapTypeSelector.setOnItemSelectedListener(this);


        fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mMap = fm.getMap();
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setPadding(0, 0, 0, 100);



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

       /*// Sets the map type to be "hybrid"
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID); */

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-38.250413, -72.662029);
        mMap.addMarker(new MarkerOptions().position(sydney).title(" ver Balneario Las Monjas").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(7.0f);
        mMap.setMaxZoomPreference(25.0f);



       // Add a marker in Sydney, Australia, and move the camera.
        LatLng traiguen = new LatLng(-38.221059, -72.653420);
        mMap.addMarker(new MarkerOptions().position(traiguen).title("ver Embalse El Castillo").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(traiguen));
        mMap.setMinZoomPreference(7.0f);
        mMap.setMaxZoomPreference(25.0f);


        // Add a marker in Sydney, Australia, and move the camera.
        LatLng traiguen1 = new LatLng(-38.245044, -72.572768);
        mMap.addMarker(new MarkerOptions().position(traiguen1).title("ver Quichamahuida").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(traiguen1));
        mMap.setMinZoomPreference(7.0f);
        mMap.setMaxZoomPreference(25.0f);

        // Add a marker cabaña mapuche, and move the camera.
        LatLng trovolhue = new LatLng(-39.227599, -72.137474);
        mMap.addMarker(new MarkerOptions().position(trovolhue).title("EPÚ RUKA ANTU RAYEN")
        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador))).setSnippet(("Familia Mapuche, invita a conocer su cultura, juegos y\n" +
                "sabores. Cuenta con fogón. Reserva previa.\n" +
                "SERVICIOS Y ACTIVIDADES: Hospedaje rural en ruca y\n" +
                "cabaña, gastronomía mapuche y cabalgatas.\n" +
                "PRECIOS: Hospedaje desde $35.000 8/p. y hospedaje en\n" +
                "ruka desde $15.000 p/p.\n" +
                "UBICACIÓN: Ruta S-199 Km. 8, Quelhue, Pucón.\n" +
                "CONTACTO: Rosario del Carmen Colipi Mora y Florencio\n" +
                "Huaiquifil. (+569) 77470563, (+569) 96994539\n" +
                "rosariocolipimora@gmail.com"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(trovolhue));
        mMap.setMinZoomPreference(7.0f);
        mMap.setMaxZoomPreference(25.0f);



        // Add a marker in Sydney, Australia, and move the camera.
        LatLng rioGrande = new LatLng(-38.011911, -72.089445);
        mMap.addMarker(new MarkerOptions().position(rioGrande).title("ver Río Grande").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rioGrande));
        mMap.setMinZoomPreference(7.0f);
        mMap.setMaxZoomPreference(25.0f);


        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);

        /*
        mMap1.setOnInfoWindowClickListener(this);
        mMap2.setOnInfoWindowClickListener(this);*/

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador))
                .anchor(0.0f,1.0f)
                .position(latLng));
            }
        });

       /* mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent sydney = new Intent(Region.this, Recomendar.class);
                startActivity(sydney);

                return false;
            }

        });*/
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        /*Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();*/

        /*Intent sydney = new Intent(Region.this, Monjas.class);
        startActivity(sydney);*/

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMap.setMapType(mMapTypes[position]);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
