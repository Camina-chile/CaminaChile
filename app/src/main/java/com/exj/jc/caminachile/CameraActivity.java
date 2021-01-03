package com.exj.jc.caminachile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.firebase.crash.FirebaseCrash;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import utils.LocalStorage;

public class CameraActivity extends Activity {
    private static final String LOG_TAG = "CameraActivity";
	private int id;
	private String filename;
	private int width;
	private int height;
    private boolean black_and_white = false;
    private boolean crop = false;
    private int rotation;
    private int crop_width;
    private int crop_height;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		Log.e(LOG_TAG,"CameraActivity.onCreate -INICIO");
		try {
        super.onCreate(savedInstanceState);
        Intent sender=getIntent();
        if(sender.getExtras()!=null){
        	try {
        		id = sender.getExtras().getInt("id");
        		filename   = sender.getExtras().getString("filename");
        		width = sender.getExtras().getInt("width");
        		height = sender.getExtras().getInt("height");
                black_and_white = sender.getExtras().getBoolean("black_and_white");
                crop = sender.getExtras().getBoolean("crop");
                crop_width = sender.getExtras().getInt("crop_width");
                crop_height = sender.getExtras().getInt("crop_height");
	        
        		
        		File newfile = new File(filename);
        		if(!newfile.exists()){       			
        			newfile.createNewFile();
        		}

        		//BWBB INICIO
		        /*Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newfile));*/
				//Activar opcion de camara Frontal o trasera
				//Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Intent cameraIntent = ContractsHelper.activarCamaraFoT(getApplicationContext(), newfile);
				//Valida Version SDK
				//if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
					String authorities= getApplication().getPackageName()+".provider";
					Uri imgUri= FileProvider.getUriForFile(this, authorities, newfile);
					cameraIntent.putExtra( MediaStore.EXTRA_OUTPUT, imgUri);
				/*}else{
					cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newfile));
				}*/
				//BWBB FIN

	            startActivityForResult(cameraIntent,0);
        	} catch (IOException e) {
                Toast.makeText(this,"No se puede crear archivo de imagen", Toast.LENGTH_LONG).show();

        		Log.e(LOG_TAG,"onCreate: Error al generar archivo",e);
        		Intent intent=new Intent();
                setResult( Activity.RESULT_CANCELED,intent);
                finish(); 
			}
        }
        else{
        	Log.e(LOG_TAG,"FormMobile: No hay parametros");
            FirebaseCrash.log(LOG_TAG+"FormMobile: No hay parametros");
        	Intent intent=new Intent();
            setResult( Activity.RESULT_CANCELED,intent);
            finish(); 
        }

		if(LocalStorage.Get(this, "screen_on", false)){
			getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}

		//BWBB LOG ERROR INICIO
		}catch (Exception e){
			Log.e(LOG_TAG,"CameraActivity.onCreate error metodo",e);
			Intent intent=new Intent();
			setResult( Activity.RESULT_CANCELED,intent);
			finish();
		}
		Log.e(LOG_TAG,"CameraActivity.onCreate -FIN");
		//BWBB LOG ERROR FIN
    }
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//BWBB LOG ERROR INICIO
		Log.e(LOG_TAG,"CameraActivity.onActivityResult -INICIO");
		try {
		//BWBB LOG ERROR FIN
	    super.onActivityResult(requestCode, resultCode, data);

	    if (resultCode == RESULT_OK) {
	    	if(width!=-1 || height!=-1 || black_and_white){
	    		new ImageResizeTask().execute(filename,width,height,black_and_white,crop,crop_width,crop_height);

	    	}
	    	else{
	    		Intent intent=new Intent();
		    	intent.putExtra("id",id); 
	            intent.putExtra("filename",new File(filename).getName());
	            intent.putExtra("rotation",rotation);
				Log.e(LOG_TAG,"Se produjo un error al guardar la imagen");
	            setResult(RESULT_OK,intent);
	            finish(); 
	    	}
            
        } 
    	else if (resultCode == RESULT_CANCELED) {
    		Intent intent=new Intent();
            setResult(RESULT_CANCELED,intent);         
            finish(); 
        } 
        else {
        	Toast.makeText(this,"Se produjo un error capturando imagen", Toast.LENGTH_LONG).show();
			Log.e(LOG_TAG,"Se produjo un error capturando imagen");
            FirebaseCrash.log(LOG_TAG+"Se produjo un error capturando imagen");
        	Intent intent=new Intent();
            setResult(RESULT_CANCELED,intent);         
            finish(); 
        }

        //BWBB LOG ERROR INICIO
		}catch (Exception e){
			Log.e(LOG_TAG,"CameraActivity.onActivityResult error metodo",e);
			Intent intent=new Intent();
			setResult( Activity.RESULT_CANCELED,intent);
			finish();
		}
		Log.e(LOG_TAG,"CameraActivity.onActivityResult -FIN");
		//BWBB LOG ERROR FIN
	}

	// volver a comentar

	public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
		//BWBB LOG ERROR INICIO
		Log.e(LOG_TAG,"CameraActivity.setCameraDisplayOrientation -INICIO");
		try {
		//BWBB LOG ERROR FIN
		android.hardware.Camera.CameraInfo info =
				new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
			case Surface.ROTATION_0: degrees = 0; break;
			case Surface.ROTATION_90: degrees = 90; break;
			case Surface.ROTATION_180: degrees = 180; break;
			case Surface.ROTATION_270: degrees = 270; break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360;  // compensate the mirror
		} else {  // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);

		//BWBB LOG ERROR INICIO
		}catch (Exception e){
			Log.e(LOG_TAG,"CameraActivity.setCameraDisplayOrientation error metodo",e);
		}
		Log.e(LOG_TAG,"CameraActivity.setCameraDisplayOrientation -FIN");
		//BWBB LOG ERROR FIN
	}
	

	private class ImageResizeTask extends AsyncTask<Object, Void, Boolean> {
		ProgressDialog progressDialog;
		
		@Override
		protected  void onPreExecute()
		{
			progressDialog = ProgressDialog.show(CameraActivity.this, "Procesando","Procesando imagen...",true);
		}
		@Override
		protected Boolean doInBackground(Object... params) {
			//BWBB LOG ERROR INICIO
			Log.e(LOG_TAG,"CameraActivity.doInBackground -INICIO");
			//BWBB LOG ERROR FIN
			try {
				String filename       = (String)  params[0];
				int   newWidth        = (Integer) params[1];
				int   newHeight       = (Integer) params[2];
                Boolean blackAndWhite = (Boolean) params[3];
                Boolean crop          = (Boolean) params[4];
                int   cropWidth       = (Integer) params[5];
                int   cropHeight      = (Integer) params[6];
				
				ExifInterface oldExif = new ExifInterface(filename);
				String exifOrientation = oldExif.getAttribute( ExifInterface.TAG_ORIENTATION);

				
				Bitmap in= BitmapFactory.decodeFile(filename);
				if(in!=null){	
					int   actualWidth  = in.getWidth();
					int   actualHeight = in.getHeight();
					
					double ratio = (double)actualWidth/(double)actualHeight;

                    if(crop){
                        if(cropWidth < cropHeight){
                            newWidth  = Math.round((cropHeight * actualWidth)/actualHeight);
                            newHeight = cropHeight;
                        }
                        else{
                            newWidth  = cropWidth;
                            newHeight = Math.round((cropWidth * actualHeight)/actualWidth);
                        }
                    }
                    else{
                        if(newWidth==-1){
                            newWidth = (int) Math.round(ratio * (double)newHeight);
                        }
                        else if(newHeight==-1){
                            newHeight = (int) Math.round((double) newWidth / ratio);
                        }
                    }


					//Bitmap out = Bitmap.createScaledBitmap(in,newWidth,newHeight, false);
					
			        float scaleWidth = ((float) newWidth) / actualWidth;
			        float scaleHeight = ((float) newHeight) / actualHeight;
			       	Matrix matrix = new Matrix();

			        matrix.postScale(scaleWidth, scaleHeight);

					if(exifOrientation.equals("Rotate 0 CW") || exifOrientation.equals("1")){
						matrix.postRotate(0);
						int aux = newWidth;
						newWidth = newHeight;
						newHeight = aux;

					} else if(exifOrientation.equals("Rotate 90 CW")){
			        	matrix.postRotate(90);
                        int aux = newWidth;
                        newWidth = newHeight;
                        newHeight = aux;

			        }else if(exifOrientation.equals("8")){
						matrix.postRotate(270);
						int aux = newWidth;
						newWidth = newHeight;
						newHeight = aux;

					}
			        else if(exifOrientation.equals("Rotate 180 CW") ||exifOrientation.equals("3")){
			        	matrix.postRotate(180);
						int aux = newWidth;
						newWidth = newHeight;
						newHeight = aux;

					}
			        else if(exifOrientation.equals("Rotate 270 CW") || exifOrientation.equals("6")){

						matrix.postRotate(90);
                        int aux = newWidth;
                        newWidth = newHeight;
                        newHeight = aux;
			        }
			        
			        Bitmap out  = Bitmap.createBitmap(in, 0, 0,actualWidth,actualHeight, matrix, true);

                    if(crop){
                        int x = Math.round((newWidth - cropWidth)/2);
                        int y = Math.round((newHeight - cropHeight)/2);

                        Log.d(LOG_TAG,"x:"+x+" y:"+y+" w:"+newWidth+" h:"+newHeight+" cw:"+cropWidth+" ch:"+cropHeight);

                        if(x < 0){
                            cropWidth = cropWidth + x;
                            x=0;
                        }

                        if(y < 0){
                            cropHeight = cropHeight + y;
                            y=0;
                        }
                        out  = Bitmap.createBitmap(out,x,y,cropWidth,cropHeight);
                    }

                    if(blackAndWhite){
                        out = ConvertToGrey(out);
                    }

			        
					File file = new File(filename);
		            FileOutputStream fOut;
		            
	                fOut = new FileOutputStream(file);
					//BWBB Comprimir IMG INICIO
	                out.compress( Bitmap.CompressFormat.JPEG, 80, fOut);
					//BWBB Comprimir IMG FIN
	                fOut.flush();
					//BWBB Comprimir IMG INICIO
					Log.i(LOG_TAG,"Tamaño IMG sin comprimir en bytes: "+fOut.getChannel().size());
					//BWBB Comprimir IMG FIN
	                fOut.close();
	                in.recycle();
	                out.recycle();

					//BWBB Comprimir IMG INICIO
					Log.i(LOG_TAG,"Tamaño IMG comprimido en bytes(in): "+ BitmapCompat.getAllocationByteCount(in));
					Log.i(LOG_TAG,"Tamaño IMG comprimido en bytes (out): "+ BitmapCompat.getAllocationByteCount(out));
					//BWBB Comprimir IMG FIN

	                ExifInterface newExif = new ExifInterface(filename);
	                if (exifOrientation != null) {
	                   newExif.setAttribute( ExifInterface.TAG_ORIENTATION, exifOrientation);
	                }
	                newExif.saveAttributes();
	                
	                return true;
				}
            }
            catch (Exception e) {
            	Log.e(LOG_TAG,"Error al escalar imagen",e);
            }
			Log.e(LOG_TAG,"CameraActivity.doInBackground -FIN");
			return false;
		}
		@Override
		protected void onPostExecute(Boolean result) {

			Log.e(LOG_TAG,"CameraActivity.onPostExecute -INICIO");
			try {
			progressDialog.dismiss();
			
			if(result){
				Intent intent=new Intent();
		    	intent.putExtra("id",id); 
	            intent.putExtra("filename",new File(filename).getName());
	            intent.putExtra("rotation",rotation);
	            setResult(RESULT_OK,intent);         
	            finish();
			}
			else{
				Toast.makeText(CameraActivity.this, "Error al procesar imagen", Toast.LENGTH_LONG).show();
                FirebaseCrash.log(LOG_TAG+"onPostExecute: Error al procesar imagen");
                Log.e(LOG_TAG,"onPostExecute: Error al procesar imagen");
				Intent intent=new Intent();
	            setResult(RESULT_CANCELED,intent);         
	            finish();
			}
			//BWBB LOG ERROR INICIO
			}catch (Exception e){
				Log.e(LOG_TAG,"CameraActivity.onPostExecute error metodo",e);
			}
			Log.e(LOG_TAG,"CameraActivity.onPostExecute -FIN");
			//BWBB LOG ERROR FIN
		}


        public Bitmap ConvertToGrey(Bitmap b) {
            Bitmap grayscaleBitmap = Bitmap.createBitmap(b.getWidth(),
                    b.getHeight(), Bitmap.Config.RGB_565);

			//BWBB LOG ERROR INICIO
			Log.e(LOG_TAG,"CameraActivity.ConvertToGrey -INICIO");
			try {
			//BWBB LOG ERROR FIN

			Canvas c = new Canvas(grayscaleBitmap);
            Paint p = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(cm);
            p.setColorFilter(filter);
            c.drawBitmap(b, 0, 0, p);
			}catch (Exception e){
				Log.e(LOG_TAG,"CameraActivity.ConvertToGrey error metodo",e);
			}
			Log.e(LOG_TAG,"CameraActivity.ConvertToGrey -FIN");
			return grayscaleBitmap;
        }
	}
}
