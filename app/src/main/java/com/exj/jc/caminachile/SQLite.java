package com.exj.jc.caminachile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SQLite extends SQLiteHelperCAMINACHILE {
    private static final String LOG_TAG = SQLite.class.getSimpleName();
    public static final String DATABASE_NAME = "caminachile.db";
    private static final int DATABASE_VERSION = 16;

    SQLiteDatabase db = this.getReadableDatabase();
    //private final SQLiteDatabase db = getWritableDatabase();


    public SQLite(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);

    }


    public boolean AddUser(String _nombre, String _nacionalidad, String _fono, String _correo, String _dni_pasaporte, String _clave) {
        Log.d(LOG_TAG,"AddUser");
        db = this.getReadableDatabase();
        try{
            //db = this.getReadableDatabase();
            db.beginTransaction();
            String  query;
            query = "INSERT INTO user " +
                    "(user_nombre,user_nationality,user_contact_number,user_mail,user_dni_pasaporte,user_clave) " +
                    "VALUES (" +
                    "'"+_nombre+"'," +
                    "'"+_nacionalidad+"'," +
                    "'"+_fono+"'," +
                    "'"+_correo+"'," +
                    "'"+_dni_pasaporte+"'," +
                    "'"+_clave+"')";
            db.execSQL(query);
            db.setTransactionSuccessful();
            db = this.getWritableDatabase();
            db.endTransaction();
            return true;
        } catch(Exception e){
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error insertando data de usuario",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return false;

    }

    public boolean addplaces_fb(String place_name, String lat, String lng, String id_place) {
        Log.d(LOG_TAG,"add_places_fb");
        db = this.getReadableDatabase();
        try{
            //db = this.getReadableDatabase();
            db.beginTransaction();
            String  query;
            query = "INSERT INTO places_fr " +
                    "(place_name_fb,place_lat_fb,place_lng_fb,placeid_fb) " +
                    "VALUES (" +
                    "'"+place_name+"'," +
                    "'"+lat+"'," +
                    "'"+lng+"'," +
                    "'"+id_place+"')";
            db.execSQL(query);
            db.setTransactionSuccessful();
            db = this.getWritableDatabase();
            db.endTransaction();
            return true;
        } catch(Exception e){
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error insertando data de places_fb",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return false;

    }

    public String GetLatFinal(String _title) {
        String lati = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT place_lat_fb FROM places_fr WHERE place_name_fb = '" + _title + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                lati = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de latitud de marcador",e);
        }
        return lati;

    }

    public String GetLntFinal(String _title) {
        String image = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT place_lng_fb FROM places_fr WHERE place_name_fb = '" + _title + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                image = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de latitud de marcador",e);
        }
        return image;
    }






    public String getUser(String _user) {

        String usr = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            String query = "SELECT user_nombre FROM user WHERE user_nombre ='" + _user + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                usr = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return usr;
    }


    public String getMail(String _mail) {

        String mail = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();


            String query = "SELECT user_mail FROM user WHERE user_mail ='" + _mail + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                mail = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return mail;
    }


    public String getPass(String _pass) {

        String passw = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT user_clave FROM user WHERE user_clave ='" + _pass + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                passw = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return passw;
    }

    public boolean AddImg(String region, String _provincia, String _comuna, String _lugar, String _comentario, ArrayList<HashMap<String, String>> _img, String _lng,String _prov,String _movie) {
        Log.d(LOG_TAG,"AddSitio");
        db = this.getReadableDatabase();
        try{
            //db = this.getReadableDatabase();
            db.beginTransaction();
            String  query;
            query = "INSERT INTO sitio_agregado " +
                    "(siag_region,siag_provincia,siag_comuna,siag_lugar,siag_valorizacion,siag_imagenes,siag_video,siag_provin,siag_movie) " +
                    "VALUES (" +
                    "'"+region+"'," +
                    "'"+_provincia+"'," +
                    "'"+_comuna+"'," +
                    "'"+_lugar+"'," +
                    "'"+_comentario+"'," +
                    "'"+_img+"'," +
                    "'"+_lng+"'," +
                    "'"+_prov+"'," +
                    "'"+_movie+"')";
            db.execSQL(query);
            db.setTransactionSuccessful();
            db = this.getWritableDatabase();
            db.endTransaction();
            return true;
        } catch(Exception e){
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error insertando data de sitio",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return false;

    }

    /*public List<String> getSites(){
        List<String> list1 = new ArrayList<String>();

        String query1 = "SELECT siag_provincia FROM sitio_agregado";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query1, null);

        if (cursor.moveToFirst()) {
            do {
                list1.add(cursor.getString(0));
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();


       return list1;

    }*/



    /*public ArrayList<HashMap<String, String>> getSites(){
        Log.d(LOG_TAG,"GetForms");
        ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
        db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            String query = "SELECT siag_provincia,siag_video FROM sitio_agregado";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    //HashMap<String, String> f = new HashMap<String, String>();
                    HashMap<String, String> f = new HashMap<String, String>();
                    for(int i=0;i<cursor.getColumnCount();i++){
                        f.put(cursor.getColumnName(i), cursor.getString(i));
                    }
                    res.put(cursor.getString(0)+":"+cursor.getString(1), f);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.endTransaction();
        } catch (Exception e) {

            Log.d(LOG_TAG,"Error al leer formularios",e);
        }finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return res;
    }*/

    public HashMap<String, HashMap<String, String>> getSites () {

        HashMap<String,HashMap<String, String>> res = new HashMap<String,HashMap<String, String>>();
        db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            String query = "SELECT siag_provincia,siag_video,siag_valorizacion,siag_lugar FROM sitio_agregado";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> f = new HashMap<String, String>();
                    for(int i=0;i<cursor.getColumnCount();i++){
                        f.put(cursor.getColumnName(i), cursor.getString(i));
                    }
                    res.put(cursor.getString(0)+":"+cursor.getString(1), f);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.endTransaction();
        } catch (Exception e) {

            Log.d(LOG_TAG,"Error al leer contratos",e);
        }finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return res;
    }


    public JSONArray GetSitesArray(){
        Log.d(LOG_TAG,"GetPendingAssignmentStatus");
        JSONArray array  = new JSONArray();
        db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            String query = "SELECT siag_provincia,siag_video,siag_valorizacion,siag_lugar FROM sitio_agregado";

            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    JSONObject obj  = new JSONObject();
                    for(int i=0;i<cursor.getColumnCount();i++){
                        obj.put(cursor.getColumnName(i), cursor.getString(i));
                    }
                    array.put(obj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.endTransaction();
        } catch (Exception e) {

            Log.d(LOG_TAG,"Error al leer servicios de contratos",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return array;
    }

    public String getLugar(String _getLat0) {

        String place = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT siag_lugar FROM sitio_agregado WHERE siag_id = '" + _getLat0 + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                place = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return place;
    }

    public int getRegion() {

        int res=0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT COUNT(region) FROM regiones ";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                res = cursor.getInt(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return res;

    }

    public HashMap<String, HashMap<String, String>> getId_coordinates() {

        HashMap<String,HashMap<String, String>> res = new HashMap<String,HashMap<String, String>>();
        db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            String query = "SELECT siag_valorizacion,siag_lugar FROM sitio_agregado";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> f = new HashMap<String, String>();
                    for(int i=0;i<cursor.getColumnCount();i++){
                        f.put(cursor.getColumnName(i), cursor.getString(i));
                    }
                    res.put(cursor.getString(0)+":"+cursor.getString(1), f);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.endTransaction();
        } catch (Exception e) {

            Log.d(LOG_TAG,"Error al leer contratos",e);
        }finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return res;

    }

    /*public long AddFormAnswer() {
        Log.d(LOG_TAG,"AddFormAnswer");
        long id = -1;
        db = this.getReadableDatabase();
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone( TimeZone.getTimeZone("UTC"));
            ContentValues values = new ContentValues();
            values.put("regi_nombre","ARAUCANIA");
            //values.put("user_id",userId);
            //values.put("foan_datetime_created", df.format(new Date()));
            //values.put("foan_status",formStatus);
            db.beginTransaction();
            id = db.insert("region", null, values);
            db.setTransactionSuccessful();
            db = this.getWritableDatabase();
            db.endTransaction();
            return id;
        } catch(Exception e){

            Log.d(LOG_TAG,"Error insertando formulario",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return id;
    }*/



    public boolean AddRegion() {
        Log.d(LOG_TAG,"AddGestionEstadoM");
        db = this.getReadableDatabase();
        try{
            //db = this.getReadableDatabase();
            db.beginTransaction();
            String  query;
            query = "INSERT INTO regiones (`id`,`region`,`abreviatura`,`capital`)\n" +
                    "VALUES (1,'Arica y Parinacota','AP','Arica'),\n" +
                    "\t(2,'Tarapacá','TA','Iquique'),\n" +
                    "\t(3,'Antofagasta','AN','Antofagasta'),\n" +
                    "\t(4,'Atacama','AT','Copiapó'),\n" +
                    "\t(5,'Coquimbo','CO','La Serena'),\n" +
                    "\t(6,'Valparaiso','VA','valparaíso'),\n" +
                    "\t(7,'Metropolitana de Santiago','RM','Santiago'),\n" +
                    "\t(8,'Libertador General Bernardo O Higgins','OH','Rancagua'),\n" +
                    "\t(9,'Maule','MA','Talca'),\n" +
                    "\t(10,'Ñuble','NB','Chillán'),\n" +
                    "\t(11,'Biobío','BI','Concepción'),\n" +
                    "\t(12,'La Araucanía','IAR','Temuco'),\n" +
                    "\t(13,'Los Ríos','LR','Valdivia'),\n" +
                    "\t(14,'Los Lagos','LL','Puerto Montt'),\n" +
                    "\t(15,'Aysén del General Carlos Ibáñez del Campo','AI','Coyhaique'),\n" +
                    "\t(16,'Magallanes y de la Antártica Chilena','MG','Punta Arenas');";
            db.execSQL(query);
            db.setTransactionSuccessful();
            db = this.getWritableDatabase();
            db.endTransaction();
            return true;
        } catch(Exception e){
            Log.d(LOG_TAG,"Error insertando data en gestion de estado",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return false;
    }


    public boolean AddProvincia() {
        Log.d(LOG_TAG,"AddGestionEstadoM");
        db = this.getReadableDatabase();
        try{
            //db = this.getReadableDatabase();
            db.beginTransaction();
            String  query;
            query = "INSERT INTO `provincias` (`id`,`provincia`,`region_id`)\n" +
                    "VALUES (1,'Arica',1),\n" +
                    "\t(2,'Parinacota',1),\n" +
                    "\t(3,'Iquique',2),\n" +
                    "\t(4,'El Tamarugal',2),\n" +
                    "\t(5,'Tocopilla',3),\n" +
                    "\t(6,'El Loa',3),\n" +
                    "\t(7,'Antofagasta',3),\n" +
                    "\t(8,'Chañaral',4),\n" +
                    "\t(9,'Copiapó',4),\n" +
                    "\t(10,'Huasco',4),\n" +
                    "\t(11,'Elqui',5),\n" +
                    "\t(12,'Limarí',5),\n" +
                    "\t(13,'Choapa',5),\n" +
                    " \t(14,'Petorca',6),\n" +
                    "\t(15,'Los Andes',6),\n" +
                    " \t(16,'San Felipe de Aconcagua',6),\n" +
                    " \t(17,'Quillota',6),\n" +
                    "\t(18,'Valparaiso',6),\n" +
                    "\t(19,'San Antonio',6),\n" +
                    "\t(20,'Isla de Pascua',6),\n" +
                    "\t(21,'Marga Marga',6),\n" +
                    "\t(22,'Chacabuco',7),\n" +
                    "\t(23,'Santiago',7),\n" +
                    "\t(24,'Cordillera',7),\n" +
                    "\t(25,'Maipo',7),\n" +
                    "\t(26,'Melipilla',7),\n" +
                    "\t(27,'Talagante',7),\n" +
                    "\t(28,'Cachapoal',8),\n" +
                    "\t(29,'Colchagua',8),\n" +
                    "\t(30,'Cardenal Caro',8),\n" +
                    "\t(31,'Curicó',9),\n" +
                    "\t(32,'Talca',9),\n" +
                    " \t(33,'Linares',9),\n" +
                    "\t(34,'Cauquenes',9),\n" +
                    "\t(35,'Diguillín',10),\n" +
                    "\t(36,'Itata',10),\n" +
                    "\t(37,'Punilla',10),\n" +
                    "\t(38,'Bio Bío',11),\n" +
                    "\t(39,'Concepción',11),\n" +
                    "\t(40,'Arauco',11),\n" +
                    "\t(41,'Malleco',12),\n" +
                    "\t(42,'Cautín',12),\n" +
                    "\t(43,'Valdivia',13),\n" +
                    "\t(44,'Ranco',13),\n" +
                    "\t(45,'Osorno',14),\n" +
                    "\t(46,'Llanquihue',14),\n" +
                    "\t(47,'Chiloé',14),\n" +
                    "\t(48,'Palena',14),\n" +
                    "\t(49,'Coyhaique',15),\n" +
                    "\t(50,'Aysén',15),\n" +
                    "\t(51,'General Carrera',15),\n" +
                    "\t(52,'Capitán Prat',15),\n" +
                    "\t(53,'Última Esperanza',16),\n" +
                    "\t(54,'Magallanes',16),\n" +
                    "\t(55,'Tierra del Fuego',16),\n" +
                    "\t(56,'Antártica Chilena',16)";
            db.execSQL(query);
            db.setTransactionSuccessful();
            db = this.getWritableDatabase();
            db.endTransaction();
            return true;
        } catch(Exception e){
            Log.d(LOG_TAG,"Error insertando data en gestion de estado",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return false;
    }

    public int getProvincia() {

        int res=0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT COUNT(provincia) FROM provincias ";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                res = cursor.getInt(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return res;

    }

    ////////////////

    public boolean AddComuna() {
        Log.d(LOG_TAG,"AddGestionEstadoM");
        db = this.getReadableDatabase();
        try{
            //db = this.getReadableDatabase();
            db.beginTransaction();
            String  query;
            query = "INSERT INTO `comunas` (`id`,`comuna`,`provincia_id`)\n" +
                    "VALUES (1,'Arica',1),\n" +
                    "\t(2,'Camarones',1),\n" +
                    "\t(3,'General Lagos',2),\n" +
                    "\t(4,'Putre',2),\n" +
                    "\t(5,'Alto Hospicio',3),\n" +
                    "\t(6,'Iquique',3),\n" +
                    "\t(7,'Camiña',4),\n" +
                    "\t(8,'Colchane',4),\n" +
                    "\t(9,'Huara',4),\n" +
                    "\t(10,'Pica',4),\n" +
                    "\t(11,'Pozo Almonte',4),\n" +
                    "  \t(12,'Tocopilla',5),\n" +
                    "  \t(13,'María Elena',5),\n" +
                    "\t(14,'Calama',6),\n" +
                    "\t(15,'Ollague',6),\n" +
                    "\t(16,'San Pedro de Atacama',6),\n" +
                    "  \t(17,'Antofagasta',7),\n" +
                    "\t(18,'Mejillones',7),\n" +
                    "\t(19,'Sierra Gorda',7),\n" +
                    "\t(20,'Taltal',7),\n" +
                    "\t(21,'Chañaral',8),\n" +
                    "\t(22,'Diego de Almagro',8),\n" +
                    "  \t(23,'Copiapó',9),\n" +
                    "\t(24,'Caldera',9),\n" +
                    "\t(25,'Tierra Amarilla',9),\n" +
                    "  \t(26,'Vallenar',10),\n" +
                    "\t(27,'Alto del Carmen',10),\n" +
                    "\t(28,'Freirina',10),\n" +
                    "\t(29,'Huasco',10),\n" +
                    "\t(30,'La Serena',11),\n" +
                    "  \t(31,'Coquimbo',11),\n" +
                    "  \t(32,'Andacollo',11),\n" +
                    "  \t(33,'La Higuera',11),\n" +
                    "  \t(34,'Paihuano',11),\n" +
                    "\t(35,'Vicuña',11),\n" +
                    "\t(36,'Ovalle',12),\n" +
                    "  \t(37,'Combarbalá',12),\n" +
                    "  \t(38,'Monte Patria',12),\n" +
                    "  \t(39,'Punitaqui',12),\n" +
                    "\t(40,'Río Hurtado',12),\n" +
                    "\t(41,'Illapel',13),\n" +
                    "\t(42,'Canela',13),\n" +
                    "\t(43,'Los Vilos',13),\n" +
                    "\t(44,'Salamanca',13),\n" +
                    "\t(45,'La Ligua',14),\n" +
                    "  \t(46,'Cabildo',14),\n" +
                    "\t(47,'Zapallar',14),\n" +
                    "  \t(48,'Papudo',14),\n" +
                    "\t(49,'Petorca',14),\n" +
                    "\t(50,'Los Andes',15),\n" +
                    "\t(51,'San Esteban',15),\n" +
                    "  \t(52,'Calle Larga',15),\n" +
                    "  \t(53,'Rinconada',15),\n" +
                    "\t(54,'San Felipe',16),\n" +
                    "  \t(55,'Llaillay',16),\n" +
                    "  \t(56,'Putaendo',16),\n" +
                    "\t(57,'Santa María',16),\n" +
                    "\t(58,'Catemu',16),\n" +
                    "\t(59,'Panquehue',16),\n" +
                    "  \t(60,'Quillota',17),\n" +
                    "  \t(61,'La Cruz',17),\n" +
                    "\t(62,'La Calera',17),\n" +
                    "\t(63,'Nogales',17),\n" +
                    "  \t(64,'Hijuelas',17),\n" +
                    "\t(65,'Valparaíso',18),\t\n" +
                    "  \t(66,'Viña del Mar',18),\n" +
                    "\t(67,'Concón',18),\n" +
                    " \t(68,'Quintero',18),\n" +
                    "  \t(69,'Puchuncaví',18),\n" +
                    "\t(70,'Casablanca',18),\n" +
                    "\t(71,'Juan Fernández',18),\n" +
                    "\t(72,'San Antonio',19),\n" +
                    "  \t(73,'Cartagena',19),\n" +
                    "\t(74,'El Tabo',19),\n" +
                    "\t(75,'El Quisco',19),\n" +
                    "\t(76,'Algarrobo',19),\n" +
                    "\t(77,'Santo Domingo',19),\n" +
                    "\t(78,'Isla de Pascua',20),\n" +
                    "\t(79,'Quilpué',21),\n" +
                    "\t(80,'Limache',21),\n" +
                    "\t(81,'Olmué',21),\n" +
                    "\t(82,'Villa Alemana',21),\n" +
                    "\t(83,'Colina',22),\n" +
                    "\t(84,'Lampa',22),\n" +
                    "\t(85,'Tiltil',22),\n" +
                    "\t(86,'Santiago',23),\n" +
                    "\t(87,'Vitacura',23),\n" +
                    "  \t(88,'San Ramón',23),\n" +
                    "\t(89,'San Miguel',23),\n" +
                    "\t(90,'San Joaquín',23),\n" +
                    "  \t(91,'Renca',23),\n" +
                    "\t(92,'Recoleta',23),\n" +
                    "  \t(93,'Quinta Normal',23),\n" +
                    "\t(94,'Quilicura',23),\n" +
                    "  \t(95,'Pudahuel',23),\n" +
                    "  \t(96,'Providencia',23),\n" +
                    "\t(97,'Peñalolén',23),\n" +
                    "  \t(98,'Pedro Aguirre Cerda',23),\n" +
                    "\t(99,'Ñuñoa',23),\n" +
                    "\t(100,'Maipú',23),\n" +
                    "\t(101,'Macul',23),\n" +
                    "\t(102,'Lo Prado',23),\n" +
                    "\t(103,'Lo Espejo',23),\n" +
                    "\t(104,'Lo Barnechea',23),\n" +
                    "\t(105,'Las Condes',23),\n" +
                    "\t(106,'La Reina',23),\n" +
                    "\t(107,'La Pintana',23),\n" +
                    "\t(108,'La Granja',23),\n" +
                    "\t(109,'La Florida',23),\n" +
                    "  \t(110,'La Cisterna',23),\n" +
                    "  \t(111,'Independencia',23),\n" +
                    "  \t(112,'Huechuraba',23),\n" +
                    "\t(113,'Estación Central',23),\n" +
                    "  \t(114,'El Bosque',23),\n" +
                    "  \t(115,'Conchalí',23),\n" +
                    "  \t(116,'Cerro Navia',23),\n" +
                    "  \t(117,'Cerrillos',23),\n" +
                    "\t(118,'Puente Alto',24),\n" +
                    "\t(119,'San José de Maipo',24),\n" +
                    "\t(120,'Pirque',24),\n" +
                    "\t(121,'San Bernardo',25),\n" +
                    "\t(122,'Buin',25),\n" +
                    "\t(123,'Paine',25),\n" +
                    "\t(124,'Calera de Tango',25),\n" +
                    "\t(125,'Melipilla',26),\n" +
                    "\t(126,'Alhué',26),\n" +
                    "\t(127,'Curacaví',26),\n" +
                    "\t(128,'María Pinto',26),\n" +
                    "\t(129,'San Pedro',26),\n" +
                    "\t(130,'Isla de Maipo',27),\n" +
                    "\t(131,'El Monte',27),\n" +
                    "\t(132,'Padre Hurtado',27),\n" +
                    "\t(133,'Peñaflor',27),\n" +
                    "\t(134,'Talagante',27),\n" +
                    "\t(135,'Codegua',28),\n" +
                    "\t(136,'Coínco',28),\n" +
                    "\t(137,'Coltauco',28),\n" +
                    "\t(138,'Doñihue',28),\n" +
                    "\t(139,'Graneros',28),\n" +
                    "\t(140,'Las Cabras',28),\n" +
                    "\t(141,'Machalí',28),\n" +
                    "\t(142,'Malloa',28),\n" +
                    "\t(143,'Mostazal',28),\n" +
                    "\t(144,'Olivar',28),\n" +
                    "\t(145,'Peumo',28),\n" +
                    "\t(146,'Pichidegua',28),\n" +
                    "\t(147,'Quinta de Tilcoco',28),\n" +
                    "\t(148,'Rancagua',28),\n" +
                    "\t(149,'Rengo',28),\n" +
                    "\t(150,'Requínoa',28),\n" +
                    "\t(151,'San Vicente de Tagua Tagua',28),\n" +
                    "\t(152,'Chépica',29),\n" +
                    "\t(153,'Chimbarongo',29),\n" +
                    "\t(154,'Lolol',29),\n" +
                    "\t(155,'Nancagua',29),\n" +
                    "\t(156,'Palmilla',29),\n" +
                    "\t(157,'Peralillo',29),\n" +
                    "\t(158,'Placilla',29),\n" +
                    "\t(159,'Pumanque',29),\n" +
                    "\t(160,'San Fernando',29),\n" +
                    "\t(161,'Santa Cruz',29),\n" +
                    "\t(162,'La Estrella',30),\n" +
                    "\t(163,'Litueche',30),\n" +
                    "\t(164,'Marchigüe',30),\n" +
                    "\t(165,'Navidad',30),\n" +
                    "\t(166,'Paredones',30),\n" +
                    "\t(167,'Pichilemu',30),\n" +
                    "\t(168,'Curicó',31),\n" +
                    "\t(169,'Hualañé',31),\n" +
                    "\t(170,'Licantén',31),\n" +
                    " \t(171,'Molina',31),\n" +
                    "\t(172,'Rauco',31),\n" +
                    "\t(173,'Romeral',31),\n" +
                    "\t(174,'Sagrada Familia',31),\n" +
                    "\t(175,'Teno',31),\n" +
                    "\t(176,'Vichuquén',31),\n" +
                    "\t(177,'Talca',32),\n" +
                    "\t(178,'San Clemente',32),\n" +
                    "\t(179,'Pelarco',32),\n" +
                    "\t(180,'Pencahue',32),\n" +
                    "\t(181,'Maule',32),\n" +
                    "\t(182,'San Rafael',32),\n" +
                    "\t(183,'Curepto',33),\n" +
                    "\t(184,'Constitución',32),\n" +
                    "\t(185,'Empedrado',32),\n" +
                    "\t(186,'Río Claro',32),\n" +
                    "\t(187,'Linares',33),\n" +
                    "\t(188,'San Javier',33),\n" +
                    "\t(189,'Parral',33),\n" +
                    "\t(190,'Villa Alegre',33),\n" +
                    "\t(191,'Longaví',33),\n" +
                    "\t(192,'Colbún',33),\n" +
                    "\t(193,'Retiro',33),\n" +
                    "\t(194,'Yerbas Buenas',33),\n" +
                    "\t(195,'Cauquenes',34),\n" +
                    "\t(196,'Chanco',34),\n" +
                    "\t(197,'Pelluhue',34),\n" +
                    "\t(198,'Bulnes',35),\n" +
                    "\t(199,'Chillán',35),\n" +
                    "\t(200,'Chillán Viejo',35),\n" +
                    "\t(201,'El Carmen',35),\n" +
                    "\t(202,'Pemuco',35),\n" +
                    "\t(203,'Pinto',35),\n" +
                    "\t(204,'Quillón',35),\n" +
                    "\t(205,'San Ignacio',35),\n" +
                    "\t(206,'Yungay',35),\n" +
                    "\t(207,'Cobquecura',36),\n" +
                    "\t(208,'Coelemu',36),\n" +
                    "\t(209,'Ninhue',36),\n" +
                    "\t(210,'Portezuelo',36),\n" +
                    "\t(211,'Quirihue',36),\n" +
                    "\t(212,'Ránquil',36),\n" +
                    "\t(213,'Treguaco',36),\n" +
                    "\t(214,'San Carlos',37),\n" +
                    "\t(215,'Coihueco',37),\n" +
                    "\t(216,'San Nicolás',37),\n" +
                    "\t(217,'Ñiquén',37),\n" +
                    "\t(218,'San Fabián',37),\n" +
                    "\t(219,'Alto Biobío',38),\n" +
                    "\t(220,'Antuco',38),\n" +
                    "\t(221,'Cabrero',38),\n" +
                    "\t(222,'Laja',38),\n" +
                    "\t(223,'Los Ángeles',38),\n" +
                    "\t(224,'Mulchén',38),\n" +
                    "\t(225,'Nacimiento',38),\n" +
                    "\t(226,'Negrete',38),\n" +
                    "\t(227,'Quilaco',38),\n" +
                    "\t(228,'Quilleco',38),\n" +
                    "\t(229,'San Rosendo',38),\n" +
                    "\t(230,'Santa Bárbara',38),\n" +
                    "\t(231,'Tucapel',38),\n" +
                    "\t(232,'Yumbel',38),\n" +
                    "\t(233,'Concepción',39),\n" +
                    "\t(234,'Coronel',39),\n" +
                    "\t(235,'Chiguayante',39),\n" +
                    "\t(236,'Florida',39),\n" +
                    "\t(237,'Hualpén',39),\n" +
                    "\t(238,'Hualqui',39),\n" +
                    "\t(239,'Lota',39),\n" +
                    "\t(240,'Penco',39),\n" +
                    "\t(241,'San Pedro de La Paz',39),\n" +
                    "\t(242,'Santa Juana',39),\n" +
                    "\t(243,'Talcahuano',39),\n" +
                    "\t(244,'Tomé',39),\n" +
                    "\t(245,'Arauco',40),\n" +
                    "\t(246,'Cañete',40),\n" +
                    "\t(247,'Contulmo',40),\n" +
                    "\t(248,'Curanilahue',40),\n" +
                    "\t(249,'Lebu',40),\n" +
                    "\t(250,'Los Álamos',40),\n" +
                    "\t(251,'Tirúa',40),\n" +
                    "\t(252,'Angol',41),\n" +
                    "\t(253,'Collipulli',41),\n" +
                    "\t(254,'Curacautín',41),\n" +
                    "\t(255,'Ercilla',41),\n" +
                    "\t(256,'Lonquimay',41),\n" +
                    "\t(257,'Los Sauces',41),\n" +
                    "\t(258,'Lumaco',41),\n" +
                    "\t(259,'Purén',41),\n" +
                    "\t(260,'Renaico',41),\n" +
                    "\t(261,'Traiguén',41),\n" +
                    "\t(262,'Victoria',41),\n" +
                    "\t(263,'Temuco',42),\n" +
                    "\t(264,'Carahue',42),\n" +
                    "\t(265,'Cholchol',42),\n" +
                    "\t(266,'Cunco',42),\n" +
                    "\t(267,'Curarrehue',42),\n" +
                    "\t(268,'Freire',42),\n" +
                    "\t(269,'Galvarino',42),\n" +
                    "\t(270,'Gorbea',42),\n" +
                    "\t(271,'Lautaro',42),\n" +
                    "\t(272,'Loncoche',42),\n" +
                    "\t(273,'Melipeuco',42),\n" +
                    "\t(274,'Nueva Imperial',42),\n" +
                    "\t(275,'Padre Las Casas',42),\n" +
                    "\t(276,'Perquenco',42),\n" +
                    "\t(277,'Pitrufquén',42),\n" +
                    "\t(278,'Pucón',42),\n" +
                    "\t(279,'Saavedra',42),\n" +
                    "\t(280,'Teodoro Schmidt',42),\n" +
                    "\t(281,'Toltén',42),\n" +
                    "\t(282,'Vilcún',42),\n" +
                    "\t(283,'Villarrica',42),\n" +
                    "\t(284,'Valdivia',43),\n" +
                    "\t(285,'Corral',43),\n" +
                    "\t(286,'Lanco',43),\n" +
                    "\t(287,'Los Lagos',43),\n" +
                    "\t(288,'Máfil',43),\n" +
                    "\t(289,'Mariquina',43),\n" +
                    "\t(290,'Paillaco',43),\n" +
                    "\t(291,'Panguipulli',43),\n" +
                    "\t(292,'La Unión',44),\n" +
                    "\t(293,'Futrono',44),\n" +
                    "\t(294,'Lago Ranco',44),\n" +
                    "\t(295,'Río Bueno',44),\n" +
                    "\t(297,'Osorno',45),\n" +
                    "\t(298,'Puerto Octay',45),\n" +
                    "\t(299,'Purranque',45),\n" +
                    "\t(300,'Puyehue',45),\n" +
                    "\t(301,'Río Negro',45),\n" +
                    "\t(302,'San Juan de la Costa',45),\n" +
                    "\t(303,'San Pablo',45),\n" +
                    "\t(304,'Calbuco',46),\n" +
                    "\t(305,'Cochamó',46),\n" +
                    "\t(306,'Fresia',46),\n" +
                    "\t(307,'Frutillar',46),\n" +
                    "\t(308,'Llanquihue',46),\n" +
                    "\t(309,'Los Muermos',46),\n" +
                    "\t(310,'Maullín',46),\n" +
                    "\t(311,'Puerto Montt',46),\n" +
                    "\t(312,'Puerto Varas',46),\n" +
                    "\t(313,'Ancud',47),\n" +
                    "\t(314,'Castro',47),\n" +
                    "\t(315,'Chonchi',47),\n" +
                    "\t(316,'Curaco de Vélez',47),\n" +
                    "\t(317,'Dalcahue',47),\n" +
                    "\t(318,'Puqueldón',47),\n" +
                    "\t(319,'Queilén',47),\n" +
                    "\t(320,'Quellón',47),\n" +
                    "\t(321,'Quemchi',47),\n" +
                    "\t(322,'Quinchao',47),\n" +
                    "\t(323,'Chaitén',48),\n" +
                    "\t(324,'Futaleufú',48),\n" +
                    "\t(325,'Hualaihué',48),\n" +
                    "\t(326,'Palena',48),\n" +
                    "\t(327,'Lago Verde',49),\n" +
                    "\t(328,'Coihaique',49),\n" +
                    "\t(329,'Aysén',50),\n" +
                    "\t(330,'Cisnes',50),\n" +
                    "\t(331,'Guaitecas',50),\n" +
                    "\t(332,'Río Ibáñez',51),\n" +
                    "\t(333,'Chile Chico',51),\n" +
                    "\t(334,'Cochrane',52),\n" +
                    "\t(335,'O Higgins',52),\n" +
                    "\t(336,'Tortel',52),\n" +
                    "\t(337,'Natales',53),\n" +
                    "\t(338,'Torres del Paine',53),\n" +
                    "\t(339,'Laguna Blanca',54),\n" +
                    "\t(340,'Punta Arenas',54),\n" +
                    "\t(341,'Río Verde',54),\n" +
                    "\t(342,'San Gregorio',54),\n" +
                    "\t(343,'Porvenir',55),\n" +
                    "\t(344,'Primavera',55),\n" +
                    "\t(345,'Timaukel',55),\n" +
                    "\t(346,'Cabo de Hornos',56),\n" +
                    "\t(347,'Antártica',56)";
            db.execSQL(query);
            db.setTransactionSuccessful();
            db = this.getWritableDatabase();
            db.endTransaction();
            return true;
        } catch(Exception e){
            Log.d(LOG_TAG,"Error insertando data en gestion de estado",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return false;
    }

    public int getComuna() {

        int res=0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT COUNT(comuna) FROM comunas ";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                res = cursor.getInt(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return res;

    }

    public List<String> getRegiones() {

        List<String> list = new ArrayList<String>();

        String query = "SELECT distinct region \n" +
                "FROM regiones ORDER BY id ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return list;

    }


    public String ID_region(String _idR) {

        String place = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT DISTINCT(id) FROM regiones WHERE region = '" + _idR + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                place = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return place;


    }


    public List<String> getProvincias(String _regionesI) {


        List<String> list1 = new ArrayList<String>();

        String query1 = "SELECT distinct provincia \n" +
                "FROM provincias \n" +
                "WHERE region_id= '" + _regionesI + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query1, null);

        if (cursor.moveToFirst()) {
            do {
                list1.add(cursor.getString(0));
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return list1;

    }

    public String ID_provincia(String id_prov) {
        String place = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT DISTINCT(id) FROM provincias WHERE provincia = '" + id_prov + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                place = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de provincia",e);
        }
        return place;
    }


    public List<String> getComunas(String _provincia) {

        List<String> list1 = new ArrayList<String>();

        /*String query1 = "SELECT distinct se.sube_nombre \n" +
                "FROM especialidad e INNER JOIN sub_especialidad se on se.espe_id = e.espe_id \n" +
                "WHERE e.cont_nombre='" + _contrato + "' AND e.espe_nombre='" + _especialidad + "'";*/

        String query1 = "SELECT distinct comuna \n" +
                "FROM comunas \n" +
                "WHERE provincia_id= '" + _provincia + "'";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query1, null);

        if (cursor.moveToFirst()) {
            do {
                list1.add(cursor.getString(0));
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return list1;


    }

    public JSONArray GetImagesArray(String _title_market){
        Log.d(LOG_TAG,"GetPendiImagesArray");
        JSONArray array  = new JSONArray();
        db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            String query = "SELECT siag_imagenes FROM sitio_agregado WHERE siag_lugar = '" + _title_market + "'";

            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    JSONObject obj  = new JSONObject();
                    for(int i=0;i<cursor.getColumnCount();i++){
                        obj.put(cursor.getColumnName(i), cursor.getString(i));
                    }
                    array.put(obj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.endTransaction();
        } catch (Exception e) {

            Log.d(LOG_TAG,"Error al leer lista de imagenes",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return array;
    }



    public String cadenaImage(String _title_market) {
        String image = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT siag_imagenes FROM sitio_agregado WHERE siag_lugar = '" + _title_market + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                image = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return image;
    }



    public String getRegionN(String _title_market) {
        String image = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT siag_region FROM sitio_agregado WHERE siag_lugar = '" + _title_market + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                image = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return image;
    }

    public String getProvinciaN(String _title_market) {
        String image = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT siag_provin FROM sitio_agregado WHERE siag_lugar = '" + _title_market + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                image = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return image;
    }

    public String getCiudadN(String _title_market) {
        String image = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT siag_comuna FROM sitio_agregado WHERE siag_lugar = '" + _title_market + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                image = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return image;
    }

    public String getLugarN(String _title_market) {
        String image = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT siag_lugar FROM sitio_agregado WHERE siag_lugar = '" + _title_market + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                image = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return image;
    }

    public String getDescripcionN(String _title_market) {
        String image = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT siag_valorizacion FROM sitio_agregado WHERE siag_lugar = '" + _title_market + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                image = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener usuario de login",e);
        }
        return image;
    }



    public ArrayList<Uri> getImagesArray(String title_market) {

        List<Uri> list1 = new ArrayList<Uri>();

        String query1 = "SELECT siag_imagenes FROM sitio_agregado WHERE siag_lugar ='" + title_market + "'";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query1, null);

        if (cursor.moveToFirst()) {
            do {
                list1.add( Uri.parse( cursor.getString(0) ) );
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();


        return (ArrayList<Uri>) list1;
    }

    public String getVideoPath(String _title) {

        String video = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT siag_movie FROM sitio_agregado WHERE siag_lugar = '" + _title + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                video = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener ruta del video del  marcador",e);
        }
        return video;

    }



    public int getcountIdplace(String idPlacefb) {
        Log.d(LOG_TAG,"getcountIdplaces");
        int res=0;
        db = this.getReadableDatabase();
        try {
            db.beginTransaction();
            String query = "SELECT count(placeid_fb) FROM places_fr WHERE placeid_fb="+idPlacefb;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                res = cursor.getInt(0);
            }
            cursor.close();
            db.endTransaction();
        } catch (Exception e) {

            Log.d(LOG_TAG,"Error al obtener cantidad de idplaces",e);
        } finally {
            if(db.inTransaction()) {
                db.endTransaction();
            }

        }
        return res;
    }

    public String getPlace(String idPlacefb) {

        String idp = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT placeid_fb FROM places_fr WHERE placeid_fb = '" + idPlacefb + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                idp = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al idp",e);
        }
        return idp;

    }


    public String getplacetodtails(String _title) {

        String idp = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT placeid_fb FROM places_fr WHERE place_name_fb = '" + _title + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                idp = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al idp",e);
        }
        return idp;

    }



    public boolean Add_type_market() {
        Log.d(LOG_TAG,"AddGestionEstadoM");
        db = this.getReadableDatabase();
        try{
            //db = this.getReadableDatabase();
            db.beginTransaction();
            String  query;
            query = "INSERT INTO tipo_recomendacion (`id`,`type_name`)\n" +
                    "VALUES (1,'Recreación'),\n" +
                    "\t(2,'Comida'),\n" +
                    "\t(3,'Alojamiento'),\n" +
                    "\t(4,'Todo')";/*,\n" +
                    "\t(5,'Otro'),\n" +
                    "\t(6,'Seleccione')";*/
            db.execSQL(query);
            db.setTransactionSuccessful();
            db = this.getWritableDatabase();
            db.endTransaction();
            return true;
        } catch(Exception e){
            Log.d(LOG_TAG,"Error insertando data en tipo de sitio",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return false;
    }

    public int getPLace_type() {

        int res=0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT COUNT(type_name) FROM tipo_recomendacion ";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                res = cursor.getInt(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al obtener cantidad de tipo de sitio",e);
        }
        return res;

    }

    public List<String> type_fb() {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct type_name \n" +
                "FROM tipo_recomendacion WHERE type_name NOT IN ('Todo','Otro') ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<String> type_fbfilter() {
        List<String> list = new ArrayList<String>();
        String query = "SELECT distinct type_name \n" +
                "FROM tipo_recomendacion WHERE type_name NOT IN ('Otro') ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean AddImages(File _imageCCH, Uri _contentUri, String _image_all, String _type_font) {
        Log.d(LOG_TAG,"AddImages");
        db = this.getReadableDatabase();
        try{
            //db = this.getReadableDatabase();
            db.beginTransaction();
            String  query;
            query = "INSERT INTO images_phat " +
                    "(image_rute_file,image_rute_storage,image_all,tipe_font_image) " +
                    "VALUES (" +
                    "'"+_imageCCH+"'," +
                    "'"+_contentUri+"'," +
                    "'"+_image_all+"'," +
                    "'"+_type_font+"')";
            db.execSQL(query);
            db.setTransactionSuccessful();
            db = this.getWritableDatabase();
            db.endTransaction();
            return true;
        } catch(Exception e){
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error insertando data de usuario",e);
        } finally{
            if(db.inTransaction()) {
                db.endTransaction();
            }
        }
        return false;
    }


    public String get_image_rute_file(String _title) {

        String type = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT image_rute_file FROM images_phat WHERE image_all = '" + _title + "' AND tipe_font_image='GALLERY' LIMIT 1";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                type = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al idp",e);
        }
        return type;

    }

    public String get_image_rute_storage(String _title) {

        String type = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT image_rute_storage FROM images_phat WHERE image_all = '" + _title + "' AND tipe_font_image='GALLERY' LIMIT 1";

            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                type = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al idp",e);
        }
        return type;

    }

    public String tipeFontImage(String _title) {

        String type = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT tipe_font_image FROM images_phat WHERE image_all = '" + _title + "'";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                type = cursor.getString(0);
            }

        } catch (Exception e) {
            //Crashlytics.logException(e);
            Log.d(LOG_TAG,"Error al idp",e);
        }
        return type;

    }

    /*public List<String> list_images_tofb1(String _place_final) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT * FROM images_phat WHERE image_all= '" + _place_final + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }*/


    public HashMap<String, HashMap<String, String>> list_images_tofb (String _place_final) {
        Log.d(LOG_TAG,"list_images_tofb");
        HashMap<String,HashMap<String, String>>  imgPhat = new HashMap<String,HashMap<String, String>>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String query = "SELECT image_rute_file," +
                    " image_rute_storage," +
                    " image_all," +
                    " tipe_font_image" +
                    " FROM images_phat WHERE image_all= '" + _place_final + "'";

            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> f = new HashMap<String, String>();
                    for(int i=0;i<cursor.getColumnCount();i++){
                        f.put(cursor.getColumnName(i), cursor.getString(i));
                    }
                    imgPhat.put(cursor.getString(0)+":"+cursor.getString(1), f);
                } while (cursor.moveToNext());
            }
            cursor.close();

        } catch (Exception e) {
           Log.d(LOG_TAG,"Error al leer contratos",e);
        }
        return imgPhat;
    }

}
