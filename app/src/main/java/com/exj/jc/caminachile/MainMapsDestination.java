package com.exj.jc.caminachile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class MainMapsDestination extends Activity implements OnItemSelectedListener {


    private static final String LOG_TAG = "OrderServiceActivity";
    public long mFoanId = -1;
    private static final int CAMERA_REQUEST = 0;
    private ImageView imageView;
    public EditText txtDescripcion;
    private Context mContext = this;
    private int contadorV=0;

    private String mResult;
    private String mResult1;


    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;

    private int     width  = 800;
    private int     height = -1;
    private int     max_images = 4;
    private int     max_videos=1;
    private boolean show_thumbs = true;
    private boolean overwrite = false;
    private boolean black_and_white = false;
    private boolean crop = false;
    private int     crop_width  = -1;
    private int     crop_height = -1;
    private boolean withComment = true;
    private ProgressDialog mDialog;


    //nuevo localizacion
    private static final String LOGTAG = "android-localizacion";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;



    VideoView vid;

    private TextView lblLatitud;
    private TextView lblLongitud;
    private ToggleButton btnActualizar;
    //
    Integer counter = 1;
    String filename;
    File videoFile = new File(String.format(""));
    private String dir;
    private LinearLayout ly_photo;
    private ArrayList<HashMap<String, String>> images = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> videos = new ArrayList<HashMap<String, String>>();
    //  private ArrayList<HashMap<String, String>> videos = new ArrayList<HashMap<String, String>>();
    // private Spinner spTipos, spEspecialidades, spAlarmas, spIndisponibilidades;

    HashMap<String, String> map = new HashMap<String, String>();

    String user_id;
    String defa_id;
    String datosMovil;
    String tracDefaLa;

    Button photoButton = null;
    //Button startButton = null;
    Button button = null;


    Location location;
    LocationManager locationManager;
    double longitude;
    double latitude;

    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int REQUEST_VIDEO_CODE = 1;
    VideoView result_video;

    private static final int REQUEST_AUDIO_RECORD = 1;
    Uri url1;
    ProgressDialog progressDialog;

    MediaRecorder recorder;

    private ProgressBar progressBar;



    Button recordButton = null;
    TextView txt=null;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_maps_destination );


        vid = (VideoView) findViewById( R.id.videoView );


        //Metodo  Video
        recordButton = (Button) findViewById( R.id.recordButton );

        /*String textV = getApplicationContext().getString( R.string.camera_labelV ) + " " + contadorV + "/" + max_videos;
        recordButton.setText( textV );*/



        recordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                File video_file = getFilePath();
                Uri video_uri = Uri.fromFile(video_file);

                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);

                }
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, video_uri);
            }
        });

    }






    //METODO  GRABAR VIDEO.
    public void dispatchTakeVideoIntent(View v) {


        Intent takeVideoIntent = new Intent( MediaStore.ACTION_VIDEO_CAPTURE);

        File video_file = getFilePath();
        Uri video_uri = Uri.fromFile(video_file);

        if (takeVideoIntent.resolveActivity(
                getPackageManager()) != null) {

            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, video_uri);
            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
            takeVideoIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 20000000L);
            //takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, -1);
            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

            //takeVideoIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,15728640L);
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CODE);
            overridePendingTransition(0, 0);

        }



    }

    // DIRECTORIO VIDEO.
    public File getFilePath (){
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        df1.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String date1 = df1.format(new Date());

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss.S");
        df.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String date = df.format(new Date());

        // File folder = new File("sdcard/video_siom_"+date1);//

       /* if(!folder.exists())
        {
            folder.mkdir();
        }*/
        File video_file = new File("sdcard/DCIM/video_os_"+"_"+date+"_video.mp4");
        setVideoFile(video_file);
        return video_file;

    }

    public String getVideoPath (){

        File video_file = getVideoFile();
        String filename = video_file.getName();
        String path = filename;//esta mal esto. No es la ruta completa, le estas pasanoo solo el nombre del archivo. Ahora esta el path completo. Debugueare.
        return path;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (requestCode == REQUEST_VIDEO_CODE){


            if(resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Video guardado correctamente. Solo puedes grabar un video.", Toast.LENGTH_LONG).show();

                if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

                    File video_file = getFilePath();
                    Uri video_uri = Uri.fromFile(video_file);
                    //Uri videoUri = data.getData();
                    //videoView.setVideoURI(videoUri);
                }
                /*recordButton.setBackgroundColor(0xffbcd4e6);
                contadorV++;
                String textV = getApplicationContext().getString(R.string.camera_labelV)+" "+contadorV+"/"+max_videos;
                recordButton.setText(textV);
                recordButton.setEnabled(false);*/


            }else{
                Toast.makeText(getApplicationContext(),"Video no guardado", Toast.LENGTH_LONG).show();
            }

        }

    }

    private File getVideoFile(){
        return videoFile;
    }

    private void setVideoFile(File _video){
        videoFile = _video;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
