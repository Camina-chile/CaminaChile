package com.exj.jc.caminachile;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.media.ExifInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class Ubicacion extends AppCompatActivity implements OnMapReadyCallback/*, NavigationView.OnNavigationItemSelectedListener*/ {

    private FirebaseAnalytics mFirebaseAnalytics;
    // private static final String SAMPLE_DB_NAME = "";
    private String DATABASE_NAME = SQLite.DATABASE_NAME;
    private GoogleMap mMap;
    private Spinner spRegion;
    private Button btnRegion,btndatos;
    private Button buttonDB;
    //private Button btnRecomendar;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;


    private String DirectoryName;
    private String LatiLong;
    private String p1 = "";
    private String p2 = "";
    String regiN = "";
    String id_prov = "";
    String comunaID = "";
    String type_place;

    /*Inicio Datos desde la db Local regiones_provincias_comunas*/
    Spinner spinner;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    /*Fin Datos desde la db Local regiones_provincias_comunas*/

    private SharedPreferences prefs;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private NavigationView navigationView;
    private SwitchCompat darkModeSwitch;

    private String correo_login;
    private String contrasena_login;
    //private String lugar;
    //private String comentario;
    private EditText namePlace;
    private EditText commentPlace;
    private AnimationDrawable anim;

    /*logica desde class recomendar*/
    private ArrayList<HashMap<String, String>> images = new ArrayList<HashMap<String, String>>();
    private HashMap<String,HashMap<String, String>> imagesFB = null;
    private Button btnRecomendar;
    HashMap<String, String> map = new HashMap<String, String>();
    private Button b;
    private boolean overwrite = false;
    private EditText editText12;
    private EditText editText13;
    private int max_images = 4;
    File imageCCH;
    Uri getImageCCHG;
    File videoCCH;

    Uri uri;
    /*video*/
    private int max_videos=1;
    private static final int CAMERA_REQUEST = 0;
    File videoFile = new File(String.format(""));
    private static final int REQUEST_VIDEO_CODE = 0;
    Button recordButton = null;
    private int contadorV=0;
    Intent VideoIntent;
    File mediaFile;
    String videoresult;
    Uri videoUri;
    private String mDefaultVideosDir;
    String currentPhotoPath;
    Uri path;
    /*video*/

    Bitmap bitmap= null;
    /*firebase*/
    FirebaseAuth mAuth;
    DatabaseReference placesDatBase;


    StorageReference mstorageRef;
    String userId;
    //private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    /*firebase*/

    private String regionName;
    private String provinciaName;
    private String comunaName;

    private String lugar;
    private String comentario;
    private String placeName;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String placefinal;
    DatabaseReference storageReference;
    StorageReference storageReference1;
    Bitmap image;
    private final int SELECT_PICTURE = 2;

    /* fin loica desde class recomendar*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.ubicacion );

        //init();

        /*firebase*/
        mAuth = FirebaseAuth.getInstance();
        placesDatBase = FirebaseDatabase.getInstance().getReference("places");
        correo_login = getIntent().getStringExtra( "correo_login" );
        contrasena_login = getIntent().getStringExtra( "contrasena_login" );
        storageReference = FirebaseDatabase.getInstance().getReference();
        storageReference1 = FirebaseStorage.getInstance().getReference();



        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("ProfileInfo");
        mAuth = FirebaseAuth.getInstance();

        firebaseUser = mAuth.getCurrentUser();
        userId = firebaseUser.getUid();


       // correo_login = getIntent().getStringExtra( "correo_login" );
       // contrasena_login = getIntent().getStringExtra( "contrasena_login" );
       /* initializeViews();
        toggleDrawer();
        initializeDefaultFragment(savedInstanceState,0);*/
        //setDarkModeSwitchListener();


        /*mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);*/
        //getSharedPreferneces
        prefs = getSharedPreferences( "Preferences", Context.MODE_PRIVATE );

        /*Inicio Datos desde la db Local regiones_provincias_comunas*/
        //spinner = (Spinner) findViewById(R.id.spinner);
        /*spinner1 = (Spinner) findViewById( R.id.spinner1 );
        spinner2 = (Spinner) findViewById( R.id.spinner2 );
        spinner3 = (Spinner) findViewById( R.id.spinner3 );


        final SQLite db = new SQLite( getApplicationContext() );
        List<String> regionesI = db.getRegiones();
        loadSpinnerData( spinner1, regionesI );

        spinner1.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String regionesI = spinner1.getSelectedItem().toString();

                regiN = regionesI;
                String idRegion = db.ID_region( regionesI );

                List<String> provincias = db.getProvincias( idRegion );
                loadSpinnerData( spinner2, provincias );
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        } );


        spinner2.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String provincia = spinner2.getSelectedItem().toString();
                String idProvincia = db.ID_provincia( provincia );
                id_prov = provincia;
                List<String> comunas = db.getComunas( idProvincia );
                loadSpinnerData( spinner3, comunas );
                comunaID = spinner3.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        } );
        spinner3.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                comunaID = spinner3.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        } );*/
        /*Fin Datos desde la db Local regiones_provincias_comunas*/

        new SweetAlertDialog( Ubicacion.this )
                .setTitleText( "Debes seleccionar un sitio en el mapa para poder recomendar :)" )
                .show();

        //contenedor mapa
        FloatingActionButton mUb = (FloatingActionButton) findViewById( R.id.btnUb );
        mUb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById( R.id.map );
                mapFragment.getMapAsync( Ubicacion.this );
            }
        } );

        btnRegion = (Button) findViewById( R.id.btnRegion );
        btnRegion.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                //Intent btnNumero = new Intent( Ubicacion.this, Mis_sitios.class );
                //startActivity( btnNumero );

            }

        } );
        /*inicio ejecutar API rest REGIONES*/
        /*getData();*/
        /*fin ejecutar API rest REGIONES*/

        /*INICIO SELECCIONAR REGIÓN DESDE LA API REST*/
        //final Spinner spCategoria = (Spinner) findViewById( R.id.spRegion );

        FloatingTextButton ms = (FloatingTextButton) findViewById( R.id.btnMis_sitios );
        ms.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnNumero = new Intent( Ubicacion.this, Mis_sitios_fb.class );
                btnNumero.putExtra("mail", correo_login);
                startActivity( btnNumero );
            }
        } );

        btnRegion = (Button) findViewById( R.id.btnRegion );
        btnRegion.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
               // Intent btnNumero = new Intent( Ubicacion.this, Mis_sitios.class );
               // startActivity( btnNumero );

            }

        } );/*FIN SELECCIONAR REGIÓN DESDE LA API REST*/



        /*btnRecomendar = (Button) findViewById(R.id.btnRecomendar);
        btnRecomendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p1.equals("")&&p2.equals("")){
                    Toast.makeText(getApplicationContext(),"Debes haber ingresado un sitio (Marcador) para poder recomendar.", Toast.LENGTH_LONG).show();
                }else{
                    //final String selectedRegion = spCategoria.getSelectedItem().toString();
                    Intent btnNumero = new Intent(Ubicacion.this, Recomendar.class);
                    btnNumero.putExtra("name_region", regiN);
                    btnNumero.putExtra("name_provincia",id_prov);
                    btnNumero.putExtra("name_comuna",comunaID);

                    btnNumero.putExtra("lat", p1);
                    btnNumero.putExtra("lng", p2);
                    startActivity(btnNumero);
                }
            }
        });*/


        FloatingTextButton rs = (FloatingTextButton) findViewById( R.id.btnM );
        rs.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (p1.equals( "" ) && p2.equals( "" )) {
                    //Toast.makeText(getApplicationContext(),"Debes haber ingresado un sitio (Marcador) para poder recomendar.", Toast.LENGTH_LONG).show();
                    new SweetAlertDialog( Ubicacion.this, SweetAlertDialog.ERROR_TYPE )
                            .setTitleText( "Debes haber ingresado un sitio (Marcador) para poder recomendar." )
                            .show();
                } else {

                    //}

                    //AlertDialog.Builder mBuilder = new AlertDialog.Builder( Ubicacion.this, R.style.AlertDialogCustom );
                    final android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder( Ubicacion.this/*, R.style.AlertDialogCustom*/ );
                    //mBuilder.getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFF000000, CUSTOM_COLOR));

                    View mView = getLayoutInflater().inflate( R.layout.dialog_spinner, null );

                   /* btndatos = mView.findViewById( R.id.btndatos );
                    anim = (AnimationDrawable) btndatos.getBackground();
                    anim.setEnterFadeDuration(2300);
                    anim.setExitFadeDuration(2300);


                    if(anim!=null && !anim.isRunning()){
                        anim.start();
                    }else if (anim!=null && anim.isRunning()){
                        anim.stop();
                    }*/

                   /*mBuilder.setIcon(R.mipmap.marcador);
                    mBuilder.setTitle( "Datos para compartir lugar" );*/
                    spinner1 = (Spinner) mView.findViewById( R.id.spinner1 );
                    spinner2 = (Spinner) mView.findViewById( R.id.spinner2 );
                    spinner3 = (Spinner) mView.findViewById( R.id.spinner3 );
                    spinner4 = (Spinner) mView.findViewById( R.id.spinner4 );
                    namePlace = (EditText) mView.findViewById( R.id.place );
                    commentPlace = (EditText) mView.findViewById( R.id.comment );

                    /*CAMERA OR GALLERY*/
                    final int REQUEST_TAKE_PHOTO = 1;
                    b = (Button) mView.findViewById( R.id.btn_camara );
                    b.setOnClickListener( new View.OnClickListener() {
                        public void onClick(View v) {

                            String lugar = namePlace.getText().toString();
                            if (lugar.equals("") || lugar.isEmpty()) {

                                new SweetAlertDialog( Ubicacion.this, SweetAlertDialog.ERROR_TYPE )
                                        .setTitleText( "Favor indicar nombre a la recomendación antes de agregar imagenes." )
                                        .show();
                            }else{

                                /*seleccionar camara o galerria*/
                                android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder( Ubicacion.this );
                                dialog.setCancelable( false );
                                dialog.setTitle( "Selecciona la fuente de las imagenes" );
                                dialog.setMessage( "Puedes tomar fotografías o seleccionar desde la galería." );
                                dialog.setPositiveButton( "Camara", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                        if (images.size() < max_images) {

                                            Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                                            // Ensure that there's a camera activity to handle the intent
                                            if (takePictureIntent.resolveActivity( getPackageManager() ) != null) {
                                                // Create the File where the photo should go
                                                File photoFile = null;
                                                try {
                                                    photoFile = createImageFile();
                                                } catch (IOException ex) {

                                                }
                                                // Continue only if the File was successfully created
                                                if (photoFile != null) {
                                                    Uri photoURI = FileProvider.getUriForFile( getApplicationContext(),
                                                            "cn.pedant.SweetAlert",
                                                            photoFile );
                                                    takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT, photoURI );
                                                    startActivityForResult( takePictureIntent, 1 );
                                                }
                                            }

                                            /*antiguo método foto*/
                            /*Intent cameraIntent = new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
                            startActivityForResult( cameraIntent, 1 );*/
                                        } else {

                                            new SweetAlertDialog( Ubicacion.this )
                                                    .setTitleText( "Solo puedes tomar " + max_images + " imagenes por cada lugar recomendado" )
                                                    .show();
                                        }


                                    }
                                } )
                                        .setNegativeButton( "Galería", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                if (images.size() < max_images) {
                                      /*Intent intent = new Intent(Intent.ACTION_PICK,
                                      android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                      intent.setType("image/*");
                                      startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                                        */

                                                    Intent photoPickerIntent = new Intent( Intent.ACTION_PICK,
                                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                                                    photoPickerIntent.setType( "image/*" );
                                                    startActivityForResult( photoPickerIntent.createChooser( photoPickerIntent, "Selecciona app de imagen" ), SELECT_PICTURE );


                                       /* Intent intentimagen = new Intent();
                                        intentimagen.setType("image/*");
                                        intentimagen.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intentimagen,"Selecciona una imagen"),SELECT_PICTURE);
                                        */
                                                } else {

                                                    new SweetAlertDialog( Ubicacion.this )
                                                            .setTitleText( "Solo puedes tomar " + max_images + " imagenes por cada lugar recomendado" )
                                                            .show();
                                                }
                                            }
                                        } )

                                        .setNeutralButton( "Cancelar ", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                            }
                                        } );

                                final android.app.AlertDialog alert = dialog.create();
                                alert.show();
                                /**/


                            }
                        }
                    } );

                    /*FIN VAMERA OR GALLERY*/


                    final SQLite db = new SQLite( getApplicationContext() );
                    List<String> regionesI = db.getRegiones();
                    loadSpinnerData( spinner1, regionesI );

                    //spinner1.getBackground().setColorFilter( Color.parseColor("#B72FE5"), PorterDuff.Mode.SRC_ATOP);
                    spinner1.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            String regionesI = spinner1.getSelectedItem().toString();

                            regiN = regionesI;
                            String idRegion = db.ID_region( regionesI );

                            List<String> provincias = db.getProvincias( idRegion );
                            loadSpinnerData( spinner2, provincias );
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }
                    } );

                    spinner2.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            String provincia = spinner2.getSelectedItem().toString();
                            String idProvincia = db.ID_provincia( provincia );
                            id_prov = provincia;
                            List<String> comunas = db.getComunas( idProvincia );
                            loadSpinnerData( spinner3, comunas );
                            comunaID = spinner3.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }
                    } );
                    spinner3.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            comunaID = spinner3.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }
                    } );

                    List<String> typle_place = db.type_fb();
                    loadSpinnerData( spinner4, typle_place );

                    spinner4.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            type_place = spinner4.getSelectedItem().toString();

                            /*regiN = regionesI;
                            String idRegion = db.ID_region( regionesI );

                            List<String> provincias = db.getProvincias( idRegion );
                            loadSpinnerData( spinner2, provincias )*/;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }
                    } );

                    mBuilder.setPositiveButton( "Recomendar", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {



                            android.app.AlertDialog.Builder dialog1 = new android.app.AlertDialog.Builder( Ubicacion.this );
                            dialog1.setCancelable( false );
                            dialog1.setTitle( "Agregar Sitio" );
                            dialog1.setMessage( "Esta seguro de agregar este sitio?" );
                            dialog1.setPositiveButton( "Agregar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    registerPlace();

                                    String latR = getIntent().getStringExtra( "lat" );
                                    String lngR = getIntent().getStringExtra( "lng" );
                                    SQLite db = new SQLite( getApplicationContext() );
                                    db.AddImg( regionName, latR, comunaName, lugar, comentario, images, lngR, provinciaName, videoresult );

                                    imagesFB = db.list_images_tofb( placefinal );

                                    for (Map.Entry<String, HashMap<String, String>> entry : imagesFB.entrySet()) {
                                        String key = entry.getKey();
                                        HashMap<String, String> imgfirebase = entry.getValue();
                                        String imageRute = "";
                                        String imageUri = "";
                                        String imageType = "";

                                        imageRute = imgfirebase.get( "image_rute_file" );
                                        imageUri = imgfirebase.get( "image_rute_storage" );
                                        imageType = imgfirebase.get( "tipe_font_image" );


                                        Uri contentUri = Uri.parse( imageUri );
                                        File imageCCH = new File( imageRute );


                                        if (imageType.equals( "CAMERA" )) {
                                            submit( imageCCH, contentUri );


                                        } else if (imageType.equals( "GALLERY" )) {


                                            Uri path = Uri.parse( imageUri );
                                            SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
                                            df.setTimeZone( TimeZone.getTimeZone( "GMT-4" ) );
                                            String date = df.format( new Date() );


                                            StorageReference filePath = storageReference1.child( "/picturesCaminaChile_/storage/emulated/0/folder/caminachile/" + placefinal + "/" ).child( "_caminaChile" + date + path.getLastPathSegment() + "_img.jpg" );
                                            filePath.putFile( path ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                }
                                            } );

                                        }
                                    }

                                    Toast.makeText( getApplicationContext(), "Sitio agregado exitosamente.", Toast.LENGTH_LONG ).show();
                                    android.app.AlertDialog.Builder dialog_c = new android.app.AlertDialog.Builder( Ubicacion.this );
                                    dialog_c.setCancelable( false );
                                    dialog_c.setTitle( "Desea compartir" );
                                    dialog_c.setMessage( "Compartir Lugar" );
                                    dialog_c.setPositiveButton( "Compartir", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog_c, int id) {

                                            SQLite db = new SQLite( getApplicationContext() );
                                            String tipeImage = db.tipeFontImage( placefinal );

                                            if (tipeImage.equals( "CAMERA" )) {

                                                Uri imageUri = FileProvider.getUriForFile(
                                                        Ubicacion.this,
                                                        "cn.pedant.SweetAlert",
                                                        imageCCH );
                                                Intent intent = new Intent( Intent.ACTION_SEND );
                                                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                                intent.putExtra( Intent.EXTRA_STREAM, imageUri );
                                                intent.setType( "image/jpg" );
                                                startActivity( Intent.createChooser( intent, "Compartir vía" ) );
                                                finish();

                                            } else if (tipeImage.equals( "GALLERY" )) {

                                                /*String fileGallery = db.get_image_rute_file(placefinal);
                                                Uri uriGallery = Uri.parse( db.get_image_rute_storage(placefinal) );
                                                File flG = new File(fileGallery);
                                                Uri imgUri = uriGallery;
                                                imgUri = FileProvider.getUriForFile(
                                                        Ubicacion.this,
                                                        "cn.pedant.SweetAlert",
                                                        flG);
                                                Intent intent = new Intent( Intent.ACTION_SEND);
                                                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                                intent.putExtra( Intent.EXTRA_STREAM, imgUri );
                                                intent.setType( "image/jpg" );
                                                startActivity( Intent.createChooser( intent, "Compartir vía" ) );*/
                                                Intent sendIntent = new Intent();
                                                sendIntent.setAction( Intent.ACTION_SEND );
                                                sendIntent.putExtra( Intent.EXTRA_TEXT, placefinal );
                                                sendIntent.setType( "text/plain" );

                                                Intent shareIntent = Intent.createChooser( sendIntent, null );
                                                startActivity( shareIntent );
                                                finish();

                                            }
                                        }
                                    } )
                                            .setNegativeButton( "Salir ", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog_c, int which) {
                                                    Intent intent = new Intent( getApplicationContext(), Ubicacion.class );
                                                    startActivity( intent );
                                                }
                                            } );
                                    final android.app.AlertDialog alert = dialog_c.create();
                                    alert.show();
                                    //finish();


                                }
                            } )
                                    .setNegativeButton( "Cancel ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    } );


                            final android.app.AlertDialog alert = dialog1.create();
                            alert.show();
                        }
                    } );

                    mBuilder.setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    } );
                    mBuilder.setView( mView );
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    if (images.size() < max_images) {
                        //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                        new SweetAlertDialog( Ubicacion.this )
                                .setTitleText( "Recuerda adjuntar 4 imágenes para poder recomendar." )
                                .show();
                    }else if(images.size() == max_images){
                        //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }

                /*if (p1.equals( "" ) && p2.equals( "" )) {
                    //Toast.makeText(getApplicationContext(),"Debes haber ingresado un sitio (Marcador) para poder recomendar.", Toast.LENGTH_LONG).show();
                    new SweetAlertDialog( Ubicacion.this, SweetAlertDialog.ERROR_TYPE )
                            .setTitleText( "Debes haber ingresado un sitio (Marcador) para poder recomendar." )
                            .show();
                } else {
                    //final String selectedRegion = spCategoria.getSelectedItem().toString();
                    Intent btnNumero = new Intent( Ubicacion.this, Recomendar.class );
                    btnNumero.putExtra( "name_region", regiN );
                    btnNumero.putExtra( "name_provincia", id_prov );
                    btnNumero.putExtra( "name_comuna", comunaID );

                    btnNumero.putExtra( "correo_login", correo_login );
                    btnNumero.putExtra( "contrasena_login", contrasena_login );

                    btnNumero.putExtra( "lat", p1 );
                    btnNumero.putExtra( "lng", p2 );
                    startActivity( btnNumero );
                }*/
                }
            }
        } );


        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions( new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS );
                return;
            }
        }

        getLocation();

    }


    /*private void init() {

        this.btndatos = mView.findViewById( R.id.btndatos );

    }*/

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

        //get access to location permission
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    // Permission Denied
                    Toast.makeText( this, "Validar Permisos de Ubicación", Toast.LENGTH_SHORT )
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        }
    }

    //Get location
    public void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService( LOCATION_SERVICE );
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
        if (myLocation == null)
        {
            myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        }
    }


    /*Inicio Datos desde la db Local regiones_provincias_comunas*/
    private void loadSpinnerData(Spinner spinner1, List<String> list) {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter);
    }
    /*Fin Datos desde la db Local regiones_provincias_comunas*/


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                           @Override
                                           public void onMapLongClick(LatLng latLng) {

                                               String miUbi = "";
                                               mMap.addMarker(new MarkerOptions()
                                                       .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador))
                                                       .anchor(1.0f,2.0f)
                                                       .title("Mi Marcador")
                                                       .snippet("Este sitio será compartido")
                                                      .position(latLng));
                                               LatiLong = String.valueOf(latLng);
                                               LatiLong = LatiLong.replace("lat/lng: (", "");
                                               String result =LatiLong;
                                               result  = result.replace(")", "");
                                               String[] parts = result.split(",");
                                               String part1 = parts[0];
                                               String part2 = parts[1];
                                               p1 = part1;
                                               p2 = part2;


                                               //}
                                           }
        });
    }

    private void agregarMarcador(double lat, double lng) {
        //String lt = LatiLong;
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi ubicación actual")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.camina_market_foreground)));
        mMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            //lat = Double.parseDouble( getIntent().getStringExtra( "lat" ) );
            //lng = Double.parseDouble( getIntent().getStringExtra( "lng" ) );

            agregarMarcador(lat, lng);
        }
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
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,0,locListener);
    }

    //Opciones de menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.action_general:
                return true;
            case R.id.action_locations:
                Intent loactions = new Intent(Ubicacion.this, Ubicacion.class);
                startActivity(loactions);
                return true;
            case R.id.action_places:
                Intent places = new Intent(Ubicacion.this, Mis_sitios_fb.class);
                startActivity(places);
                return true;
            case R.id.action_we:
                Intent we = new Intent(Ubicacion.this, WeCaminaChile.class);
                startActivity(we);
                return true;
            case R.id.action_socialnetwork:
                Intent socialNetworks = new Intent(Ubicacion.this, Share.class);
                startActivity(socialNetworks);
                return true;
            /*case R.id.menu_contact:
                Intent contacts = new Intent(Ubicacion.this, Contact.class);
                startActivity(contacts);
                return true;*/
            case R.id.menu_logout:
                logOut();
                return true;
            case R.id.menu_forget_logout:
                removeSharedPreferences();
                logOut();
                return true;
            case R.id.menu_change_pass:
                Intent registro = new Intent(Ubicacion.this, RestablecerPass.class);
                startActivity(registro);
                return true;
            default: return super.onOptionsItemSelected(item);

            case R.id.action_donate:
                Intent donateCch = new Intent(Ubicacion.this, Donate.class);
                startActivity(donateCch);
                return true;
        }
    }

    private void logOut(){
        Intent intent = new Intent(this, Inicio.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void removeSharedPreferences(){
        prefs.edit().clear().apply();
    }


    private File createImageFile() throws IOException {

        //String place = String.valueOf( namePlace );
        String place = namePlace.getText().toString();
        place = place.replace( " ","" );
        //String placefinal=place;
        placefinal=place;

        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
        df.setTimeZone( TimeZone.getTimeZone( "GMT-4" ) );
        String date = df.format( new Date() );

        File sdCardDirectory = Environment.getExternalStorageDirectory();
        new File( sdCardDirectory + "/folder/caminachile/"+placefinal+"/" ).mkdirs();
        imageCCH = new File( sdCardDirectory + "/folder/caminachile/"+placefinal+"/_camina_chile" + date + "_img.jpg" );
        currentPhotoPath = imageCCH.getAbsolutePath();
        return imageCCH;

    }

    /*metodo nuevo foto*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            SQLite db = new SQLite(getApplicationContext());
            final String tipe_camera="CAMERA";
            File f = new File(currentPhotoPath);
            Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            /*for(int x = 0; x < images.size(); x++){
            }*/

            Bitmap bitmap= BitmapFactory.decodeFile( f.getAbsolutePath() );
            try {
                ExifInterface ei = new ExifInterface(f.getAbsolutePath());
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        bitmap = Utils.rotateImage( bitmap, 90);
                        //  submit(imageCCH,contentUri);
                        db.AddImages(imageCCH,contentUri,placefinal,tipe_camera);

                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        bitmap =  Utils.rotateImage(bitmap, 180) ;
                        //submit(imageCCH,contentUri);
                        db.AddImages(imageCCH,contentUri,placefinal,tipe_camera);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        bitmap =  Utils.rotateImage(bitmap, 270) ;
                        //submit(imageCCH,contentUri);
                        db.AddImages(imageCCH,contentUri,placefinal,tipe_camera);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        //submit(imageCCH,contentUri);
                        db.AddImages(imageCCH,contentUri,placefinal,tipe_camera);
                        bitmap = ( bitmap );

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            AddImageToArray( imageCCH );
            //submit();
            boolean success = false;
            FileOutputStream fOut;



            new SweetAlertDialog(Ubicacion.this)
                    .setTitleText("Imagen guardada exitosamente")
                    .show();
            /*try {
                fOut = new FileOutputStream( imageCCH );

                fOut.flush();
                fOut.close();
                success = true;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            /*if (success) {
                new SweetAlertDialog(Recomendar.this)
                        .setTitleText("Imagen guardada exitosamente")
                        .show();
            } else {
                new SweetAlertDialog(Recomendar.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error mientras guardaba la imagen")
                        .show();
            }*/
        }
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {


            if (resultCode == RESULT_OK) {
                //Toast.makeText( getApplicationContext(), "Video guardado correctamente. Solo puedes grabar un video.", Toast.LENGTH_LONG ).show();
                new SweetAlertDialog(Ubicacion.this)
                        .setTitleText("Video guardado correctamente. Solo puedes grabar un video")
                        .show();
                /*ruta en string del File de video*/
                videoresult= String.valueOf( mediaFile );


                VideoView myVideoView = (VideoView)findViewById(R.id.VidView);
                /*myVideoView.setVisibility(View.VISIBLE);
                myVideoView.setEnabled(false);*/
                myVideoView.setVideoPath(videoresult);
                myVideoView.setMediaController(new MediaController(this));
                myVideoView.requestFocus();
                myVideoView.animate().alpha(1);
                myVideoView.start();
                /**/
                //recordButton.setBackgroundColor( 0xffbcd4e6 );
                contadorV++;
                String textV = /*getApplicationContext().getString( R.string.camera_labelV ) + " " + */contadorV + "/" + max_videos;
                recordButton.setText( textV );
                /*subir video a firebase*/
                //submitV();
                /*fin subir video a firebase*/
                //recordButton.setEnabled( false );

            } else if (resultCode == RESULT_CANCELED) {
                //Toast.makeText( this, "Video cancelado.", Toast.LENGTH_LONG ).show();
                new SweetAlertDialog(Ubicacion.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Video cancelado.")
                        .show();
            } else {
                //Toast.makeText( this, "Fallo la captura de video", Toast.LENGTH_LONG ).show();
                new SweetAlertDialog(Ubicacion.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Fallo la captura de video")
                        .show();
            }
        }
        else if (requestCode == 2 && resultCode == Activity.RESULT_OK /*&& data.getData()!=null*/){
            String place = namePlace.getText().toString();
            place = place.replace( " ","" );
            placefinal=place;
            String tipe_camera="GALLERY";

            SQLite db = new SQLite( getApplicationContext());

            path = data.getData();
            /*String path1 = String.valueOf( path );
            ImageView imageView = (ImageView)findViewById(R.id.image1);
            bitmap = ImageUtils.getInstant().getCompressedBitmap( path1);
            imageView.setImageBitmap(bitmap);*/

            SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
            df.setTimeZone( TimeZone.getTimeZone( "GMT-4" ) );
            String date = df.format( new Date() );
            StorageReference filePath = storageReference1.child("/picturesCaminaChile_/storage/emulated/0/folder/caminachile/"+placefinal).child("_caminaChile"+date+path.getLastPathSegment()+"_img.jpg");
            filePath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            });



            new SweetAlertDialog(Ubicacion.this)
                    .setTitleText("Imagen subida desde la galeria")
                    .show();


            /*para subir a firebase realtime*/
            getImageCCHG = Uri.parse("/storage/emulated/0/folder/caminachile/"+placefinal+"/_caminaChile"+date+path.getLastPathSegment()+"_img.jpg" );
            AddImageToArrayG( getImageCCHG,path);
            /*db*/
            //File imageFilePath = new File( String.valueOf( getImageCCHG ) );
            //db.AddImages(imageFilePath, Uri.parse( path.getLastPathSegment() ),placefinal,tipe_camera);
            /*db*/


        }
    }

    private void submit(File imageCCH, Uri contentUri) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //image.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        SimpleDateFormat df = new SimpleDateFormat( "yyyy_MM_dd_HH_mm_ss_S" );
        df.setTimeZone( TimeZone.getTimeZone( "GMT-4" ) );
        String date = df.format( new Date() );
        byte[] b = stream.toByteArray();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child( "picturesCaminaChile_" + imageCCH );
        storageReference.putFile( contentUri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d( "tag", "onSuccess: Uploaded Image URl is " + uri.toString() );
                    }
                } );

                Toast.makeText( Ubicacion.this, "Image Is Uploaded.", Toast.LENGTH_SHORT ).show();
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( Ubicacion.this, "Upload Failled.", Toast.LENGTH_SHORT ).show();
            }
        } );
        //}
    }

    private void AddImageToArray(File imageCCH) {
        map = new HashMap<String, String>();
        map.put("image", String.valueOf(imageCCH));
        if(0<images.size() && overwrite){
            images.set(0,map);
        }
        else {
            images.add( map );

            for (int x = 0; x < images.size(); x++) {

                if(images.size()==1){
                    img1 = String.valueOf( (imageCCH) );
                }else if (images.size()==2){
                    img2 = String.valueOf( (imageCCH) );
                }else if (images.size()==3){
                    img3 = String.valueOf( (imageCCH) );
                }else if (images.size()==4){
                    img4 = String.valueOf( (imageCCH) );
                }

            }

            /*if(images.size()==2){

                String content = images.toString();
                System.out.println("content = " + content);
                String img = String.valueOf( imageCCH );
            }*/

        }
        UpdateButton();
    }


    private void AddImageToArrayG(Uri getImageCCHG,Uri path) {
        map = new HashMap<String, String>();
        map.put("image", String.valueOf(getImageCCHG));
        if(0<images.size() && overwrite){
            images.set(0,map);
        }
        else {
            images.add( map );

            for (int x = 0; x < images.size(); x++) {
                String tipe_camera="GALLERY";
                SQLite db = new SQLite( getApplicationContext() );
                File imageFilePath = new File( String.valueOf( getImageCCHG ) );
                db.AddImages(imageFilePath,Uri.parse( path.getLastPathSegment() ),placefinal,tipe_camera);

                if(images.size()==1){
                    img1 = String.valueOf( (getImageCCHG) );
                }else if (images.size()==2){
                    img2 = String.valueOf( (getImageCCHG) );
                }else if (images.size()==3){
                    img3 = String.valueOf( (getImageCCHG) );
                }else if (images.size()==4){
                    img4 = String.valueOf( (getImageCCHG) );
                }

            }

            /*if(images.size()==2){

                String content = images.toString();
                System.out.println("content = " + content);
                String img = String.valueOf( imageCCH );
            }*/

        }
        UpdateButton();
    }

    private void UpdateButton() {
        if (b != null) {
            if (images.size() <= max_images) {
                String text =  images.size() + "/" + max_images;
                b.setText(text);
                b.setEnabled(true);
                b.setTextColor(0xFFFFFFFF);
            }
            else {
                String text = images.size() + "/" + max_images;
                b.setText(text);
                b.setEnabled(false);
            }
        }

    }


    private void registerPlace() {
        /*mAuth.createUserWithEmailAndPassword( correo_login,contrasena_login ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                 // mAuth.signInWithEmailAndPassword( correo_login,contrasena_login).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("region",regionName);
                    map.put("provincia",provinciaName);
                    map.put("comuna",comunaName);
                    map.put("correo",correo_login);
                    map.put("contrasena_login",contrasena_login);
                    String id = mAuth.getCurrentUser().getUid();
                    mDatBase.child( "places").child(id).setValue(map).addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                            }
                        }
                    } );
                }else{
                }
            }
        } );*/

        String reg=regiN;
        String prov=id_prov;
        String com = comunaID;
        String placeName = namePlace.getText().toString();
        String commentPlaceR = commentPlace.getText().toString();
        String type_placefb =type_place;
        String video= String.valueOf(mediaFile );
        String id =placesDatBase.push().getKey();
        String mail =correo_login;

        Places places = new Places (id,reg,prov,com,placeName,commentPlaceR,img1,img2,img3,img4,p1,p2, placefinal,video,type_placefb,mail);
        placesDatBase.child(id).setValue(places);
        //Places places = new Places (/*id,*/reg,prov,com,placeName,commentPlace,img1,img2,img3,img4,lat,lng, placefinal);
        //placesDatBase.setValue(places);




    }

    /*API REST QUE OBTIENE LAS REGIONES Y COMUNAS DE CHILE.*/
   /* public void getData(){
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
            spRegion = (Spinner) findViewById(R.id.spRegion);
            loadSpinnerData(spRegion,items);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadSpinnerData(Spinner spRegion, String[] items) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRegion.setAdapter(dataAdapter);
    }*/

   /*INICIO MENU

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar_id);
        toolbar.setTitle(R.string.Location);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_id);
        frameLayout = findViewById(R.id.framelayout_id);
        navigationView = findViewById(R.id.navigationview_id);
        navigationView.setNavigationItemSelectedListener(this);
        darkModeSwitch = (SwitchCompat)navigationView.getMenu().findItem(R.id.nav_darkmode_id).getActionView();
    }


    private void initializeDefaultFragment(Bundle savedInstanceState, int itemIndex){
        if (savedInstanceState == null){
            MenuItem menuItem = navigationView.getMenu().getItem(itemIndex).setChecked(true);
            onNavigationItemSelected(menuItem);
        }
    }


   private void toggleDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawerLayout.addDrawerListener(drawerToggle);
       drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        //Checks if the navigation drawer is open -- If so, close it
        if (drawerLayout.isDrawerOpen( GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_message_id:

                break;
            case R.id.nav_chat_id:

                Intent btnNumero = new Intent(Ubicacion.this, Ubicacion.class);
                startActivity(btnNumero);
                closeDrawer();
                break;
            case R.id.nav_profile_id:
                closeDrawer();
                break;
            case R.id.nav_notebooks_id:
                closeDrawer();
                break;
        }
        return true;
    }

    private void setDarkModeSwitchListener(){
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    Toast.makeText(Ubicacion.this, "Dark Mode Turn Off", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Ubicacion.this, "Dark Mode Turn On", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void closeDrawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void deSelectCheckedState(){
        int noOfItems = navigationView.getMenu().size();
        for (int i=0; i<noOfItems;i++){
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    FIN MENU*/

}




