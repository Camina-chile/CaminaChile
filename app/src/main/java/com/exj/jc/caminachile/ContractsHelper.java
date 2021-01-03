
package com.exj.jc.caminachile;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import java.io.File;
/**

 */

public class ContractsHelper {


    /**
     *
     * @param applicationContext
     * @param newfile
     * @return
     */
    public static Intent activarCamaraFoT(Context applicationContext, File newfile){

        //Recuperar Variables de session
        SharedPreferences prefs = applicationContext.getSharedPreferences(Constantes.S_A_SESSION,   Context.MODE_PRIVATE);
        String activarFT = prefs.getString(Constantes.S_ACTIVAR_FT, "");
        String formFTSeleccionado = prefs.getString(Constantes.S_FORM_FT_SELECT, "");

        //Activar camara frontal
        if(Constantes.S_SI.equals(activarFT) && Constantes.S_SI.equals(formFTSeleccionado)){
            Intent cameraIntentFT= new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntentFT.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
            cameraIntentFT.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            cameraIntentFT.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);

            //Cambiar valor de la variable seesion para que tome los otros formS
            /*SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Constantes.S_FORM_FT_SELECT, Constantes.S_NO);
            editor.commit();*/

            return cameraIntentFT;

         //Activar Camara Trasera
        }else{
            Intent cameraIntent= new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK);
            cameraIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 0);
            cameraIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", false);
            return cameraIntent;
        }

    }

    /**
     * Valida y despliega mensaje indicando si equipo no tiene camara frontal.
     * @param applicationContext
     * @param dialog
     */
    public static void validarCamaraFront(Context applicationContext, Dialog dialog){
        if(false == checkCameraHardwareFront(applicationContext)){
            dialog.setCancelable(true);
            //dialog.setTitle(applicationContext.getString(R.string.services));
            dialog.setTitle("Error Camara");
        }
    }

    /**
     * Validar si el dispositivo tiene camara frontal
     * @param context
     * @return
     */
    public static boolean checkCameraHardwareFront(Context context) {
        boolean resp=false;
        if (context.getPackageManager().hasSystemFeature( PackageManager.FEATURE_CAMERA_FRONT)){
            resp= true;
        }
        return resp;
    }
}
