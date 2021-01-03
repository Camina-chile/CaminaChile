package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Almacenamiento local de datos
 */
public class LocalStorage {
    private static final String LOG_TAG = "LocalStorage";

    public static String Get(Context c,String key,String def){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getString(key,def);
    }

    public static Integer Get(Context c,String key,Integer def){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getInt(key, def);
    }

    public static long Get(Context c,String key,long def){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getLong(key, def);
    }

    public static JSONObject Get(Context c,String key,JSONObject def){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        String objString           = settings.getString(key, "");
        if(objString.isEmpty()){
            return def;
        }
        else{
            try {
                return new JSONObject(objString);
            }
            catch (JSONException e) {
                Log.e(LOG_TAG, "Error al parsear datos en localstorage", e);
            }
        }
        return def;
    }

    public static JSONArray Get(Context c,String key,JSONArray def){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        String objString           = settings.getString(key,"");
        if(objString.isEmpty()){
            return def;
        }
        else{
            try {
                return new JSONArray(objString);
            }
            catch (JSONException e) {
                Log.e(LOG_TAG, "Error al parsear datos en localstorage", e);
            }
        }
        return def;
    }

    public static Boolean Get(Context c,String key,Boolean def){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        return settings.getBoolean(key,def);
    }


    public static void Set(Context c,String key,String value){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void Set(Context c,String key,Integer value){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void Set(Context c,String key,long value){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void Set(Context c,String key,JSONObject value){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value.toString());
        editor.commit();
    }

    public static void Set(Context c,String key,JSONArray value){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value.toString());
        editor.commit();
    }

    public static void Set(Context c,String key,Boolean value){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

}
