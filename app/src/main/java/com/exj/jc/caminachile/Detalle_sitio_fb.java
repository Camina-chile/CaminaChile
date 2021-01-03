package com.exj.jc.caminachile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class Detalle_sitio_fb extends AppCompatActivity /*implements View.OnClickListener*/ {

    private Boolean isFabOpen = false;

    private Animation fab_open,fab_close,rotate_forward,rotate_backward;



    private FloatingActionButton fb,fab,fab1,fab2,fab3,fab7,fab8,fab9;
    private FloatingTextButton b_back;
    String dat1;
    TextView region;
    TextView provincia;
    TextView ciudad;
    TextView lugar;
    TextView lugar_final;
    TextView comentario;
    TextView lat;
    TextView lng;
    TextView descripcion;
    TextView id_place;
    ListView listView;
    private boolean isFABExpened;

    String reN;
    String prN;
    String valorzizacionN;
    String comN;
    String placeN;
    String title_market;
    String idPlace;

    Bitmap bmImg1;
    private Uri myURI;
    private Uri myURI1;
    private Uri myURI2;
    private Uri myURI3;
    String part1;
    String pa2;
    String pa3;
    String pa4;
    VideoView vid;
    String uriVideo;
    String mail;
    String img1;
    String img2;
    String img3;
    String img4;
    String video_fb;


    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    VideoView videofb;
    Bitmap bitmap;
    /*firebase image*/
     DatabaseReference reff;
    private ProgressBar progressBar;
    Integer counter = 1;
    TextView txt;
    /*fin firebase image*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detalle_sitio_fb );
        listView = findViewById( R.id.lisView );



        region = findViewById( R.id.region );
        provincia = findViewById( R.id.provincia );
        ciudad = findViewById( R.id.ciudad );
        lugar = findViewById( R.id.lugar );
        comentario = findViewById( R.id.comentario);

        title_market  = getIntent().getStringExtra("title");
        idPlace  = getIntent().getStringExtra("idPlace");
        mail  = getIntent().getStringExtra("mail");

        /*get data place from realtime*/
        reff = FirebaseDatabase.getInstance().getReference().child( "places" ).child(idPlace);
        reff.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String regionfb = dataSnapshot.child( "region" ).getValue().toString();
                String provinciafb = dataSnapshot.child( "provincia" ).getValue().toString();
                String comunafb = dataSnapshot.child( "comuna" ).getValue().toString();
                String lugarfb = dataSnapshot.child( "placeName" ).getValue().toString();
                String comentariofb = dataSnapshot.child( "commentPlace" ).getValue().toString();
                //video_fb = dataSnapshot.child( "mediaFile" ).getValue().toString();
                img1 = dataSnapshot.child( "image1" ).getValue().toString();
                img2 = dataSnapshot.child( "image2" ).getValue().toString();
                img3 = dataSnapshot.child( "image3" ).getValue().toString();
                img4 = dataSnapshot.child( "image4" ).getValue().toString();
                region.setText(regionfb);
                provincia.setText(provinciafb);
                ciudad.setText(comunafb);
                lugar.setText(lugarfb);
                comentario.setText( comentariofb);


                /*imagen*/
                FirebaseStorage storage=FirebaseStorage.getInstance();
                StorageReference storageReference= storage.getReferenceFromUrl( "gs://caminachile-43bb6.appspot.com/").child( "picturesCaminaChile_"+img1);
                StorageReference storageReference1= storage.getReferenceFromUrl( "gs://caminachile-43bb6.appspot.com/").child( "picturesCaminaChile_"+img2);
                StorageReference storageReference2= storage.getReferenceFromUrl( "gs://caminachile-43bb6.appspot.com/").child( "picturesCaminaChile_"+img3);
                StorageReference storageReference3= storage.getReferenceFromUrl( "gs://caminachile-43bb6.appspot.com/").child( "picturesCaminaChile_"+img4);
                //StorageReference storageReference4= storage.getReferenceFromUrl( "https://firebasestorage.googleapis.com/v0/b/caminachile-43bb6.appspot.com/o/").child( "picturesCaminaChile_"+video_fb);


                image1=(ImageView)findViewById( R.id.image1);
                image2=(ImageView)findViewById( R.id.image2);
                image3=(ImageView)findViewById( R.id.image3);
                image4=(ImageView)findViewById( R.id.image4);
                //videofb=(VideoView)findViewById( R.id.videofb);



                try {
                    final File file=File.createTempFile( "image","jpg" );
                    storageReference.getFile( file ).addOnSuccessListener( new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            bitmap =BitmapFactory.decodeFile( file.getAbsolutePath() );
                           // Bitmap rotatedBitmap=bitmap;
                            try {
                                ExifInterface ei = new ExifInterface(file.getAbsolutePath());
                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                                switch (orientation) {
                                    case ExifInterface.ORIENTATION_ROTATE_90:
                                        bitmap = Utils.rotateImage( bitmap, 90);
                                        image1.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                        bitmap =  Utils.rotateImage(bitmap, 180) ;
                                        image1.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_270:
                                        bitmap =  Utils.rotateImage(bitmap, 270) ;
                                        image1.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_NORMAL:
                                    default:
                                        bitmap = ( bitmap );
                                        image1.setImageBitmap(bitmap);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new SweetAlertDialog( Detalle_sitio_fb.this, SweetAlertDialog.ERROR_TYPE )
                                    .setTitleText( "No se pudo cargar la imagen." )
                                    .show();
                        }
                    } );

                } catch (IOException e) {
                    //Crashlytics.logException(e);
                    e.printStackTrace();
                }


                try {
                    final File file=File.createTempFile( "image","jpg" );
                    storageReference1.getFile( file ).addOnSuccessListener( new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Bitmap bitmap=BitmapFactory.decodeFile( file.getAbsolutePath() );
                            try {
                                ExifInterface ei = new ExifInterface(file.getAbsolutePath());
                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                                switch (orientation) {
                                    case ExifInterface.ORIENTATION_ROTATE_90:
                                        bitmap = Utils.rotateImage( bitmap, 90);
                                        image2.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                        bitmap =  Utils.rotateImage(bitmap, 180) ;
                                        image2.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_270:
                                        bitmap =  Utils.rotateImage(bitmap, 270) ;
                                        image2.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_NORMAL:
                                    default:
                                        bitmap = ( bitmap );
                                        image2.setImageBitmap(bitmap);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new SweetAlertDialog( Detalle_sitio_fb.this, SweetAlertDialog.ERROR_TYPE )
                                    .setTitleText( "No se pudo cargar la imagen." )
                                    .show();
                        }
                    } );

                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    final File file=File.createTempFile( "image","jpg" );
                    storageReference2.getFile( file ).addOnSuccessListener( new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Bitmap bitmap=BitmapFactory.decodeFile( file.getAbsolutePath() );
                            try {
                                ExifInterface ei = new ExifInterface(file.getAbsolutePath());
                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                                switch (orientation) {
                                    case ExifInterface.ORIENTATION_ROTATE_90:
                                        bitmap = Utils.rotateImage( bitmap, 90);
                                        image3.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                        bitmap =  Utils.rotateImage(bitmap, 180) ;
                                        image3.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_270:
                                        bitmap =  Utils.rotateImage(bitmap, 270) ;
                                        image3.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_NORMAL:
                                    default:
                                        bitmap = ( bitmap );
                                        image3.setImageBitmap(bitmap);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new SweetAlertDialog( Detalle_sitio_fb.this, SweetAlertDialog.ERROR_TYPE )
                                    .setTitleText( "No se pudo cargar la imagen." )
                                    .show();
                        }
                    } );

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    final File file=File.createTempFile( "image","jpg" );
                    storageReference3.getFile( file ).addOnSuccessListener( new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Bitmap bitmap=BitmapFactory.decodeFile( file.getAbsolutePath() );
                            try {
                                ExifInterface ei = new ExifInterface(file.getAbsolutePath());
                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                                switch (orientation) {
                                    case ExifInterface.ORIENTATION_ROTATE_90:
                                        bitmap = Utils.rotateImage( bitmap, 90);
                                        image4.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                        bitmap =  Utils.rotateImage(bitmap, 180) ;
                                        image4.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_270:
                                        bitmap =  Utils.rotateImage(bitmap, 270) ;
                                        image4.setImageBitmap(bitmap);
                                        break;

                                    case ExifInterface.ORIENTATION_NORMAL:
                                    default:
                                        bitmap = ( bitmap );
                                        image4.setImageBitmap(bitmap);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new SweetAlertDialog( Detalle_sitio_fb.this, SweetAlertDialog.ERROR_TYPE )
                                    .setTitleText( "No se pudo cargar la imagen." )
                                    .show();
                        }
                    } );

                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*logica de traer video firebase*/
                /*
                try {
                    final File file=File.createTempFile( "videos","mp4" );
                    storageReference4.getFile( file ).addOnSuccessListener( new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                            Uri uri=Uri.fromFile( file.getAbsoluteFile() );
                            videofb.setVideoURI( uri);
                            videofb.setMediaController(new MediaController(Detalle_sitio_fb.this));
                            videofb.animate().alpha(1);
                            videofb.requestFocus();
                            videofb.start();

                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            new SweetAlertDialog( Detalle_sitio_fb.this )
                                    .setTitleText( "Espere la carga del video..." )
                                    .show();
                        }
                    } );

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                /*logica de traer video firebase*/





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        /**/
        b_back = (FloatingTextButton) findViewById(R.id.btnBack);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent btnNumero = new Intent(Detalle_sitio_fb.this, Mis_sitios_fb.class);
                startActivity(btnNumero);
            }
        });

        title_market  = getIntent().getStringExtra("title");
    }


}
