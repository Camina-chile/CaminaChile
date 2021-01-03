package com.exj.jc.caminachile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class Mis_sitios_fb extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    //DatabaseReference mMainMenuRef = mRootRef.child("places");
    DatabaseReference mMainMenuRef = mRootRef.child("places");

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mGetReference = mDatabase.getReference();
    private FloatingTextButton b_back;
    private FloatingTextButton filter;
    private Marker marcador;
    private GoogleMap mMap;
    double lat = 0.0;
    double lng = 0.0;
    String part1;
    String part2;
    String title;
    String idPlacefb;
    String comunaFB;
    String mail;
    Spinner spinner4;
    String type_place;
    LatLng market1;
    public Mis_sitios_fb() {
    }

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mis_sitios_fb );

        mail  = getIntent().getStringExtra("mail");

        b_back = (FloatingTextButton) findViewById(R.id.btnBack);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent btnNumero = new Intent(Mis_sitios_fb.this, Ubicacion.class);
                startActivity(btnNumero);
            }
        });


        //contenedor mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*
        mMainMenuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren())
                {

                    for (DataSnapshot property :dataSnapshot1.getChildren()) {
                        //String value = property.getValue(String.class);
                        String PlaceName = dataSnapshot.child("placeName").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        */

        b_back = (FloatingTextButton) findViewById(R.id.btnBack);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent btnNumero = new Intent(Mis_sitios_fb.this, Ubicacion.class);
                startActivity(btnNumero);
            }
        });

    }

    private void loadSpinnerData(Spinner spinner4, List<String> list) {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner4.setAdapter(dataAdapter);
    }

    private void agregarMarcador(double lat, double lng) {

        //String lt = LatiLong;
        LatLng coordenadas = new LatLng(lat, lng);

        String latlng= String.valueOf( coordenadas );
        latlng=latlng.replace("lat/lng: (","");
        String latlngR=latlng;
        latlngR=latlngR.replace(")","");
        String latlngResult=latlngR;

        String[] parts = latlngResult.split(",");
        part1 = parts[0];
        part2 = parts[1];


        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi ubicación")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.camina_market_foreground)));
        mMap.animateCamera(miUbicacion);
    }

    public void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Antut(googleMap);
    }
    public void Antut (final GoogleMap googleMap){

        filter = (FloatingTextButton) findViewById(R.id.btnFilter);
        filter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder( Mis_sitios_fb.this );
                View mView = getLayoutInflater().inflate( R.layout.dialog_spinner1, null );
                spinner4 = (Spinner) mView.findViewById( R.id.spinner4 );

               /*spinner de filtro opcion 1*/
                final SQLite db = new SQLite( getApplicationContext() );
                List<String> typle_place = db.type_fbfilter();
                loadSpinnerData( spinner4, typle_place );
                spinner4.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        type_place = spinner4.getSelectedItem().toString();

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                } );

                /*fin spinner de filtro opcion 1*/
                mBuilder.setPositiveButton( "Buscar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        googleMap.clear();
                        if(type_place.equals("Todo")){
                            DatabaseReference reff;
                            reff = FirebaseDatabase.getInstance().getReference().child( "places" )/*.child( "-MFgnWwRL8Wf0OPwom98")*/;
                            reff.addValueEventListener( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                                    for(final DataSnapshot ds : dataSnapshot.getChildren()) {

                                        final String lugarfb = ds.child( "placeName" ).getValue().toString();
                                        final String latfb = ds.child( "lat" ).getValue().toString();
                                        final String lngfb = ds.child( "lng" ).getValue().toString();
                                        idPlacefb = ds.child( "idplace" ).getValue().toString();
                                        comunaFB = ds.child( "comuna" ).getValue().toString();
                                        final String tipo_lugar = ds.child( "type_placefb" ).getValue().toString();
                                        SQLite db = new SQLite( getApplicationContext() );
                                        db.addplaces_fb( lugarfb, latfb, lngfb, idPlacefb );

                                        if(tipo_lugar.equals( "Alojamiento" )){

                                            double getLat = Double.parseDouble( latfb );
                                            double getLng = Double.parseDouble( lngfb );
                                            mMap = googleMap;
                                            market1 = new LatLng( getLat, getLng );
                                            mMap.addMarker( new MarkerOptions()
                                                    .position( market1 ).title( lugarfb )
                                                    .icon( BitmapDescriptorFactory.fromResource( R.mipmap.bedroom_camina_foreground ) )
                                                    .snippet( comunaFB )
                                            );

                                        }else if(tipo_lugar.equals( "Comida" )) {
                                            double getLat = Double.parseDouble( latfb );
                                            double getLng = Double.parseDouble( lngfb );
                                            mMap = googleMap;
                                            market1 = new LatLng( getLat, getLng );
                                            mMap.addMarker( new MarkerOptions()
                                                    .position( market1 ).title( lugarfb )
                                                    .icon( BitmapDescriptorFactory.fromResource( R.mipmap.food_camina_foreground ) )
                                                    .snippet( comunaFB )
                                            );

                                        }else if(tipo_lugar.equals( "Recreación" )) {
                                            double getLat = Double.parseDouble( latfb );
                                            double getLng = Double.parseDouble( lngfb );
                                            mMap = googleMap;
                                            market1 = new LatLng( getLat, getLng );
                                            mMap.addMarker( new MarkerOptions()
                                                    .position( market1 ).title( lugarfb )
                                                    .icon( BitmapDescriptorFactory.fromResource( R.mipmap.camina_walk_foreground) )
                                                    .snippet( comunaFB )
                                            );

                                        }


                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            } );

                        }

                        if(type_place.equals("Recreación")){

                            Query reff;
                            reff = FirebaseDatabase.getInstance().getReference().child( "places" ).orderByChild("type_placefb").equalTo("Recreación");
                            reff.addValueEventListener( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                                    for(final DataSnapshot ds : dataSnapshot.getChildren()) {

                                        final String lugarfb = ds.child( "placeName" ).getValue().toString();
                                        final String latfb = ds.child( "lat" ).getValue().toString();
                                        final String lngfb = ds.child( "lng" ).getValue().toString();
                                        idPlacefb = ds.child( "idplace" ).getValue().toString();
                                        comunaFB = ds.child( "comuna" ).getValue().toString();
                                        SQLite db = new SQLite( getApplicationContext() );
                                        db.addplaces_fb( lugarfb, latfb, lngfb, idPlacefb );
                                        double getLat = Double.parseDouble( latfb );
                                        double getLng = Double.parseDouble( lngfb );
                                        mMap = googleMap;
                                        market1 = new LatLng( getLat, getLng );
                                        mMap.addMarker( new MarkerOptions()
                                                .position( market1 ).title( lugarfb )
                                                .icon( BitmapDescriptorFactory.fromResource( R.mipmap.camina_walk_foreground) )
                                                .snippet( comunaFB )
                                        );

                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            } );

                        } if(type_place.equals( "Alojamiento" )){

                            Query reff;
                            reff = FirebaseDatabase.getInstance().getReference().child( "places" ).orderByChild("type_placefb").equalTo("Alojamiento");
                            reff.addValueEventListener( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                                    for(final DataSnapshot ds : dataSnapshot.getChildren()) {

                                        final String lugarfb = ds.child( "placeName" ).getValue().toString();
                                        final String latfb = ds.child( "lat" ).getValue().toString();
                                        final String lngfb = ds.child( "lng" ).getValue().toString();
                                        idPlacefb = ds.child( "idplace" ).getValue().toString();
                                        comunaFB = ds.child( "comuna" ).getValue().toString();
                                        SQLite db = new SQLite( getApplicationContext() );
                                        db.addplaces_fb( lugarfb, latfb, lngfb, idPlacefb );
                                        double getLat = Double.parseDouble( latfb );
                                        double getLng = Double.parseDouble( lngfb );
                                        mMap = googleMap;
                                        market1 = new LatLng( getLat, getLng );
                                        mMap.addMarker( new MarkerOptions()
                                                .position( market1 ).title( lugarfb )
                                                .icon( BitmapDescriptorFactory.fromResource( R.mipmap.bedroom_camina_foreground) )
                                                .snippet( comunaFB )
                                        );

                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            } );

                        } if(type_place.equals( "Comida" )){

                            Query reff;
                            reff = FirebaseDatabase.getInstance().getReference().child( "places" ).orderByChild("type_placefb").equalTo("Comida");
                            reff.addValueEventListener( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                                    for(final DataSnapshot ds : dataSnapshot.getChildren()) {

                                        final String lugarfb = ds.child( "placeName" ).getValue().toString();
                                        final String latfb = ds.child( "lat" ).getValue().toString();
                                        final String lngfb = ds.child( "lng" ).getValue().toString();
                                        idPlacefb = ds.child( "idplace" ).getValue().toString();
                                        comunaFB = ds.child( "comuna" ).getValue().toString();
                                        SQLite db = new SQLite( getApplicationContext() );
                                        db.addplaces_fb( lugarfb, latfb, lngfb, idPlacefb );
                                        double getLat = Double.parseDouble( latfb );
                                        double getLng = Double.parseDouble( lngfb );
                                        mMap = googleMap;
                                        market1 = new LatLng( getLat, getLng );
                                        mMap.addMarker( new MarkerOptions()
                                                .position( market1 ).title( lugarfb )
                                                .icon( BitmapDescriptorFactory.fromResource( R.mipmap.food_camina_foreground ) )
                                                .snippet( comunaFB )
                                        );

                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            } );
                        }

                        dialog.dismiss();
                    }
                } );
                mBuilder.setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                } );
                mBuilder.setView( mView );
                android.support.v7.app.AlertDialog dialog = mBuilder.create();
                dialog.show();
                /*filtro*/



        }
    } );




        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            public boolean onMarkerClick(Marker marker1 ) {

                title = marker1.getTitle();

                //final String ctm=title;

                if (title.equals("Mi ubicación")){

                }else {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(Mis_sitios_fb.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Lugar: "+title);
                    dialog.setMessage("Ver ruta al lugar, acceder al detalle del lugar." );
                    dialog.setPositiveButton("Acceder", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            SQLite db = new SQLite(getApplicationContext());
                            String placetodetails = db.getplacetodtails(title);

                            Intent intent = new Intent(Mis_sitios_fb.this, Detalle_sitio_fb.class);
                            intent.putExtra("title", title);
                            intent.putExtra("idPlace", placetodetails);
                            intent.putExtra("mail", mail);

                            //intent.putExtra("venue",venue.toJSON());
                            startActivity(intent);

                        }
                    })
                            .setNegativeButton("Ver ruta ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    SQLite db = new SQLite(getApplicationContext());
                                    String latDestino= db.GetLatFinal(title);
                                    String lngDestino= db.GetLntFinal(title);

                                    Uri.Builder directionsBuilder = new Uri.Builder()
                                            .scheme("https")
                                            .authority("www.google.com")
                                            .appendPath("maps")
                                            .appendPath("dir")
                                            .appendPath("")
                                            .appendQueryParameter("api", "1")
                                            .appendQueryParameter("origin", part1 + "," + part2)
                                            .appendQueryParameter("destination", latDestino + "," + lngDestino);

                                    startActivity(new Intent(Intent.ACTION_VIEW, directionsBuilder.build()));

                                }
                            })


                    .setNeutralButton("Cancelar ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    final AlertDialog alert = dialog.create();
                    alert.show();

                }
                return false;

            }
        });

        /*inicio quizas de fallas de ubicacion en tiempo real*/
        miUbicacion();
        /*fin quizas de fallas de ubicacion en tiempo real*/

    }


    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }





        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,locListener);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen( GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int title;
        switch (menuItem.getItemId()) {
            case R.id.nav_camera:
                //title = R.string.menu_camera;
                break;
            case R.id.nav_gallery:
                title = R.string.menu_gallery;
                break;
            case R.id.nav_manage:
                title = R.string.menu_tools;
                break;
            case R.id.nav_share:
                title = R.string.menu_share;
                break;
            case R.id.nav_send:
                title = R.string.menu_send;
                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }

       /* HomeContentFragment fragment = HomeContentFragment.newInstance(getString(title));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_content, fragment).commit();

        setTitle(getString(title));*/

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
        //cambio en la posición del drawer
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
        //el drawer se ha abierto completamente
        Toast.makeText(this, getString(R.string.navigation_drawer_open),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDrawerClosed(@NonNull View view) {
        //el drawer se ha cerrado completamente
    }

    @Override
    public void onDrawerStateChanged(int i) {
        //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
    }
}