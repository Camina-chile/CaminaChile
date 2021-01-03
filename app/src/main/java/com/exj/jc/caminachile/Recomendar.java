package com.exj.jc.caminachile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
//import android.support.v4.BuildConfig;

import android.support.annotation.NonNull;
import android.support.media.ExifInterface;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import cn.pedant.SweetAlert.BuildConfig;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class Recomendar extends AppCompatActivity {
    TextView region;
    TextView provincia;
    TextView comuna;
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
    private String lat;
    private String lng;
    private String lugar;
    private String comentario;
    private String placeName;
    private String commentPlace;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String placefinal;
    private String type_place;

    private String correo_login;
    private String contrasena_login;
    DatabaseReference storageReference;
    StorageReference storageReference1;
    Bitmap image;
    private final int SELECT_PICTURE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.recomendar );

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
        /*firebase*/


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);


        regionName = getIntent().getStringExtra( "name_region" );
        provinciaName = getIntent().getStringExtra( "name_provincia" );
        comunaName = getIntent().getStringExtra( "name_comuna" );
        placeName = getIntent().getStringExtra( "lugar" );
        commentPlace = getIntent().getStringExtra( "comentario" );
        type_place = getIntent().getStringExtra( "type_place" );


        lat = getIntent().getStringExtra( "lat" );
        lng = getIntent().getStringExtra( "lng" );

        editText12 = (EditText) findViewById( R.id.editText12 );
        editText12.setText( placeName );
        editText13 = (EditText) findViewById( R.id.editText13 );
        editText13.setText(commentPlace);



        region = (TextView) findViewById( R.id.editTextRegion );
        region.setText( regionName );
        provincia = (TextView) findViewById( R.id.editTextProvincia );
        provincia.setText( provinciaName );
        comuna = (TextView) findViewById( R.id.editTextComuna );
        comuna.setText( comunaName );

        btnRecomendar = (Button) findViewById( R.id.btnRecomendarAdd );


        /*Video*/
        recordButton  = (Button) findViewById(R.id.recordButton);
        String textV = getApplicationContext().getString(R.string.camera_labelV)+" "+contadorV+"/"+max_videos;
        recordButton.setText(textV);


        recordButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                String place = placeName;
                if(contadorV<max_videos) {
                    SimpleDateFormat df = new SimpleDateFormat( "yyyy_MM_dd_HH_mm_ss_S" );
                    df.setTimeZone( TimeZone.getTimeZone( "GMT-4" ) );
                    String date = df.format( new Date() );
                    mediaFile = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + "/folder/caminachile/_video_cch" + date + "_video.mp4" );
                    //Uri videoUri = Uri.fromFile(mediaFile);

                    videoUri = FileProvider.getUriForFile( Recomendar.this, BuildConfig.APPLICATION_ID, mediaFile );
                    Intent intent = new Intent( MediaStore.ACTION_VIDEO_CAPTURE );
                    intent.putExtra( MediaStore.EXTRA_OUTPUT, videoUri );
                    intent.putExtra( MediaStore.EXTRA_DURATION_LIMIT, 10 );
                    intent.putExtra( MediaStore.EXTRA_SIZE_LIMIT, 20000000L );
                    //takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, -1);
                    intent.putExtra( MediaStore.EXTRA_VIDEO_QUALITY, 1 );


                    startActivityForResult( intent, 0 );
                    overridePendingTransition( 0, 0 );
                }else
                {
                    //Toast.makeText( getApplicationContext(), "Solo puedes tomar " + max_videos + " video por cada lugar recomendado", Toast.LENGTH_LONG ).show();
                    new SweetAlertDialog(Recomendar.this)
                            .setTitleText("Solo puedes tomar " + max_videos + " video por cada lugar recomendado")
                            .show();
                }

            }
        } );

        /* video */

         final int REQUEST_TAKE_PHOTO = 1;
        b = (Button) findViewById( R.id.btn_camara );
        b.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                if (placeName.equals("") || placeName.isEmpty()) {

                    new SweetAlertDialog( Recomendar.this, SweetAlertDialog.ERROR_TYPE )
                            .setTitleText( "Favor indicar nombre a la recomendación antes de agregar imagenes." )
                            .show();
                }else{

                        /*seleccionar camara o galerria*/
                        AlertDialog.Builder dialog = new AlertDialog.Builder( Recomendar.this );
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

                                    new SweetAlertDialog( Recomendar.this )
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

                                            new SweetAlertDialog( Recomendar.this )
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

                        final AlertDialog alert = dialog.create();
                        alert.show();
                        /**/


                    }
            }
        } );

        btnRecomendar.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lugar = editText12.getText().toString();
                comentario = editText13.getText().toString();

                registerPlace();
                AlertDialog.Builder dialog = new AlertDialog.Builder( Recomendar.this );
                dialog.setCancelable( false );
                dialog.setTitle( "Agregar Sitio" );
                dialog.setMessage( "Esta seguro de agregar este sitio?" );
                dialog.setPositiveButton( "Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        SQLite db = new SQLite( getApplicationContext() );

                        db.AddImg( regionName, lat, comunaName, lugar, comentario, images, lng, provinciaName,videoresult );

                        imagesFB = db.list_images_tofb(placefinal);
                        /*int i;
                        for (i = 0; i < imagesFB.size(); i++) {
                            HashMap<String, String> c = imagesFB.get(i);
                            // if (c.get("tare_estado").equals("ASIGNADA")){
                            c.put("image_rute_file", c.get("image_rute_file"));
                            c.put("image_rute_storage", c.get("image_rute_storage"));
                            c.put("image_all", c.get("image_all"));
                            c.put("tipe_font_image", c.get("tipe_font_image"));

                        }*/

                        for (Map.Entry<String, HashMap<String, String>> entry : imagesFB.entrySet()) {
                            String key = entry.getKey();
                            HashMap<String, String> imgfirebase = entry.getValue();
                            String imageRute = "";
                            String imageUri ="";
                            String imageType ="";

                            imageRute = imgfirebase.get("image_rute_file");
                            imageUri = imgfirebase.get("image_rute_storage");
                            imageType = imgfirebase.get("tipe_font_image");


                            Uri contentUri = Uri.parse( imageUri );
                            File imageCCH = new File( imageRute );


                            if(imageType.equals( "CAMERA" )){
                                submit(imageCCH,contentUri  );



                            }else if (imageType.equals("GALLERY")) {


                                Uri path = Uri.parse( imageUri );
                                SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
                                df.setTimeZone( TimeZone.getTimeZone( "GMT-4" ) );
                                String date = df.format( new Date() );



                                StorageReference filePath = storageReference1.child("/picturesCaminaChile_/storage/emulated/0/folder/caminachile/"+placefinal+"/").child("_caminaChile"+date+path.getLastPathSegment()+"_img.jpg");
                                filePath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    }
                                });

                            }
                        }

                        Toast.makeText( getApplicationContext(), "Sitio agregado exitosamente.", Toast.LENGTH_LONG ).show();
                        AlertDialog.Builder dialog_c = new AlertDialog.Builder( Recomendar.this );
                        dialog_c.setCancelable( false );
                        dialog_c.setTitle( "Desea compartir" );
                        dialog_c.setMessage( "Compartir Lugar" );
                        dialog_c.setPositiveButton( "Compartir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog_c, int id) {
                                /*logica para enviar por provider*/
                                Uri imageUri = FileProvider.getUriForFile(
                                        Recomendar.this,
                                        "cn.pedant.SweetAlert",
                                        imageCCH );
                                Intent intent = new Intent( Intent.ACTION_SEND);
                                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                intent.putExtra( Intent.EXTRA_STREAM, imageUri );
                                intent.setType( "image/*" );
                                startActivity( Intent.createChooser( intent, "Compartir vía" ) );
                                /*logica para enviar por provider*/
                                //Toast.makeText( getApplicationContext(), "Aqui se hara la logica de compartir", Toast.LENGTH_LONG ).show();

                                finish();
                            }
                        } )
                                .setNegativeButton( "Salir ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog_c, int which) {
                                        finish();
                                    }
                                } );
                        final AlertDialog alert = dialog_c.create();
                        alert.show();
                        //finish();


                    }
                } )
                        .setNegativeButton( "Cancel ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        } );
                final AlertDialog alert = dialog.create();
                alert.show();
            }


        } );
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

        String reg=regionName;
        String prov=provinciaName;
        String com = comunaName;
        String placeName=lugar;
        String commentPlace = comentario;
        String type_placefb =type_place;
        String mail = correo_login;

        String video= String.valueOf(mediaFile );

        String id =placesDatBase.push().getKey();
        Places places = new Places (id,reg,prov,com,placeName,commentPlace,img1,img2,img3,img4,lat,lng, placefinal,video,type_placefb, mail );
        placesDatBase.child(id).setValue(places);
        //Places places = new Places (/*id,*/reg,prov,com,placeName,commentPlace,img1,img2,img3,img4,lat,lng, placefinal);
        //placesDatBase.setValue(places);




    }
    private void setSupportActionBar(Toolbar toolbar) {
    }


    /*metodo nuevo foto*/




    private File createImageFile() throws IOException {

        String place = placeName;
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



            new SweetAlertDialog(Recomendar.this)
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
                new SweetAlertDialog(Recomendar.this)
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
                new SweetAlertDialog(Recomendar.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Video cancelado.")
                        .show();
            } else {
                //Toast.makeText( this, "Fallo la captura de video", Toast.LENGTH_LONG ).show();
                new SweetAlertDialog(Recomendar.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Fallo la captura de video")
                        .show();
            }
        }
        else if (requestCode == 2 && resultCode == Activity.RESULT_OK){
            String place = placeName;
            place = place.replace( " ","" );
            placefinal=place;
            String tipe_camera="GALLERY";

            SQLite db = new SQLite( getApplicationContext() );

            path = data.getData();

            /*metodo original envio imagen desde galeria a firebase*/

            Uri path = data.getData();
            SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
            df.setTimeZone( TimeZone.getTimeZone( "GMT-4" ) );
            String date = df.format( new Date() );

            final ProgressBar pb = (ProgressBar) findViewById(R.id.pbLoading);
            pb.setVisibility(ProgressBar.VISIBLE);

            StorageReference filePath = storageReference1.child("/picturesCaminaChile_/storage/emulated/0/folder/caminachile/"+placefinal).child("_caminaChile"+date+path.getLastPathSegment()+"_img.jpg");
            filePath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pb.setVisibility(ProgressBar.INVISIBLE);
                }
            });

            new SweetAlertDialog(Recomendar.this)
                    .setTitleText("Imagen subida desde la galeria")
                    .show();

            /*Guardar DD.BB*/

            /* fin Guardar DD.BB*/
            /*para subir a firebase realtime*/
            getImageCCHG = Uri.parse("/storage/emulated/0/folder/caminachile/"+placefinal+"/_caminaChile"+date+path.getLastPathSegment()+"_img.jpg" );
            AddImageToArrayG( getImageCCHG,path);
           // File imageFilePath = new File( String.valueOf( getImageCCHG ) );
           // db.AddImages(imageFilePath, Uri.parse( path.getLastPathSegment() ),placefinal,tipe_camera);


        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image = storageReference1.child("picturesCaminaChile_/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString());
                    }
                });

                Toast.makeText( Recomendar.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Recomendar.this, "Upload Failled.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void submit(File imageCCH, Uri contentUri) {


        //if (images.size() == 3) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
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

                Toast.makeText( Recomendar.this, "Image Is Uploaded.", Toast.LENGTH_SHORT ).show();
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( Recomendar.this, "Upload Failled.", Toast.LENGTH_SHORT ).show();
            }
        } );
     //}
    }

    private void submit2(File f, Uri contentUri) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        SimpleDateFormat df = new SimpleDateFormat( "yyyy_MM_dd_HH_mm_ss_S" );
        df.setTimeZone( TimeZone.getTimeZone( "GMT-4" ) );
        String date = df.format( new Date() );


        byte[] b = stream.toByteArray();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picturesCaminaChile_"+f);
        storageReference.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString());
                    }
                });

                Toast.makeText( Recomendar.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Recomendar.this, "Upload Failled.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void submitV() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        SimpleDateFormat df = new SimpleDateFormat( "yyyy_MM_dd_HH_mm_ss_S" );
        df.setTimeZone( TimeZone.getTimeZone( "GMT-4" ) );
        String date = df.format( new Date() );



        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference photoRef = storageRef.child ("picturesCaminaChile_"+mediaFile);


        photoRef.putFile(Uri.fromFile(mediaFile))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Upload succeeded
                        Toast.makeText(getApplicationContext(), "Upload Success...", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed
                        Toast.makeText(getApplicationContext(), "Upload failed...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(
                new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        //displaying percentage in progress dialog
                        // progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                });
       /* byte[] b = stream.toByteArray();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("picturesCaminaChile_"+mediaFile);

        storageReference.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                Toast.makeText(Recomendar.this, "Imagen subiada", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Recomendar.this,"Fallo la subida",Toast.LENGTH_LONG).show();
            }
        });*/
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
}

