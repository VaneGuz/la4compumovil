package co.edu.udea.compumovil.gr03_20171.lab2activities.Events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import data.Events;

/**
 * Created by admin on 1/03/2017.
 */

public class EventsDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eventosDept.db";
    SQLiteDatabase db;
    Cursor cursor;


    public EventsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("HOLI", "pase por el constructor de databasehelper ");
        // mockData(getWritableDatabase());
        addItem(getWritableDatabase());

    }

    private void addItem(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS '" + EventsContract.EventsEntry.TABLE_NAME + "'");

        String query = "CREATE TABLE " + EventsContract.EventsEntry.TABLE_NAME + " ("
                + EventsContract.EventsEntry.NOMBRE + " TEXT PRIMARY KEY NOT NULL,"
                + EventsContract.EventsEntry.DESCRIPCION + " TEXT ,"
                + EventsContract.EventsEntry.PUNTUACION + " TEXT ,"
                + EventsContract.EventsEntry.RESPONSABLE + " TEXT ,"
                + EventsContract.EventsEntry.FECHA + " TEXT ,"
                + EventsContract.EventsEntry.UBICACION + " TEXT ,"
                + EventsContract.EventsEntry.INFOGENERAL + " TEXT,"
                + EventsContract.EventsEntry.FOTO + " TEXT" + ")";
        Log.i("HOLI", "pase por string databasehelper y voy a crear la table");

        db.execSQL(query);

        // Contenedor de valores
        ContentValues values = new ContentValues();
        values.put(EventsContract.EventsEntry.NOMBRE, "Obra de teatro");
        values.put(EventsContract.EventsEntry.DESCRIPCION,"Obra de shakespeare la Odisea");
        values.put(EventsContract.EventsEntry.PUNTUACION, "87");
        values.put(EventsContract.EventsEntry.RESPONSABLE, "Alba Castaño Arboleda");
        values.put(EventsContract.EventsEntry.FECHA, "12/12/2017");
        values.put(EventsContract.EventsEntry.UBICACION, "Teatro Matacandelas");
        values.put(EventsContract.EventsEntry.INFOGENERAL, "Una divertida obra de teatro para disfrutar en familia");
        values.put(EventsContract.EventsEntry.FOTO, "fotico");
        Log.i("HOLI", "pase por execsql para crear la table");
        Log.i("HOLI", "EJECUTO EL INSERT1");
        db.insert(
                EventsContract.EventsEntry.TABLE_NAME,
                null,
                values);

        values = new ContentValues();
        values.put(EventsContract.EventsEntry.NOMBRE, "Voleibol Playa");
        values.put(EventsContract.EventsEntry.DESCRIPCION,"Competencia de voleibol entre la liga española de la Universidad Pontificia");
        values.put(EventsContract.EventsEntry.PUNTUACION, "45");
        values.put(EventsContract.EventsEntry.RESPONSABLE, "Daniel Agudelo Velasquez");
        values.put(EventsContract.EventsEntry.FECHA, "12/03/2017");
        values.put(EventsContract.EventsEntry.UBICACION, "Cartagena");
        values.put(EventsContract.EventsEntry.INFOGENERAL, "La competencia se llevará en un lapso de tiempo de un mes en el hotel Imperial");
        values.put(EventsContract.EventsEntry.FOTO, "fotico");
        Log.i("HOLI", "pase por execsql para crear la table");

        db.insert(
                EventsContract.EventsEntry.TABLE_NAME,
                null,
                values);

        values = new ContentValues();
        values.put(EventsContract.EventsEntry.NOMBRE, "Concierto Filarmonica");
        values.put(EventsContract.EventsEntry.DESCRIPCION,"Con un director invitado de Alemania de la filarmonica de Berlín");
        values.put(EventsContract.EventsEntry.PUNTUACION, "93");
        values.put(EventsContract.EventsEntry.RESPONSABLE, "Victoriano Valencia");
        values.put(EventsContract.EventsEntry.FECHA, "04/03/2018");
        values.put(EventsContract.EventsEntry.UBICACION, "Bogotá");
        values.put(EventsContract.EventsEntry.INFOGENERAL, "El concierto se llevará a cabo para la recaudación de fondos de la fundación Pies Descalzos");
        values.put(EventsContract.EventsEntry.FOTO, "fotico");
        Log.i("HOLI", "pase por execsql para crear la table");

        db.insert(
                EventsContract.EventsEntry.TABLE_NAME,
                null,
                values);

        values = new ContentValues();
        values.put(EventsContract.EventsEntry.NOMBRE, "Fiesta de fin de año");
        values.put(EventsContract.EventsEntry.DESCRIPCION,"Fiesta para compartir en familia");
        values.put(EventsContract.EventsEntry.PUNTUACION, "60");
        values.put(EventsContract.EventsEntry.RESPONSABLE, "Jorge Barón Gutierrez");
        values.put(EventsContract.EventsEntry.FECHA, "31/12/2018");
        values.put(EventsContract.EventsEntry.UBICACION, "Centro comercial Santa Fe");
        values.put(EventsContract.EventsEntry.INFOGENERAL, "Se contará con artistas invitados, comida y bebida al gratín");
        values.put(EventsContract.EventsEntry.FOTO, "fotico");
        Log.i("HOLI", "pase por execsql para crear la table");


        db.insert(
                EventsContract.EventsEntry.TABLE_NAME,
                null,
                values);


        values = new ContentValues();
        values.put(EventsContract.EventsEntry.NOMBRE, "Festival de cine");
        values.put(EventsContract.EventsEntry.DESCRIPCION,"El cinearte tomará las calles de Medellín");
        values.put(EventsContract.EventsEntry.PUNTUACION, "43");
        values.put(EventsContract.EventsEntry.RESPONSABLE, "Claudia Alejandra Tangarife");
        values.put(EventsContract.EventsEntry.FECHA, "01/04/2017");
        values.put(EventsContract.EventsEntry.UBICACION, "Medellín");
        values.put(EventsContract.EventsEntry.INFOGENERAL, "Todos están invitados a pasar un tiempo cultural con los máximos exponentes del cinearte de todos los tiempos");
        values.put(EventsContract.EventsEntry.FOTO, "fotico");
        Log.i("HOLI", "pase por execsql para crear la table");


        db.insert(
                EventsContract.EventsEntry.TABLE_NAME,
                null,
                values);
        Log.i("HOLI", "EJECUTO EL INSERT");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("HOLI", "pase por onCreate de databasehelper y cree la table");
        db.execSQL("DROP TABLE IF EXISTS '" + EventsContract.EventsEntry.TABLE_NAME + "'");

        String query = "CREATE TABLE " + EventsContract.EventsEntry.TABLE_NAME + " ("
                + EventsContract.EventsEntry.NOMBRE + " TEXT PRIMARY KEY NOT NULL,"
                + EventsContract.EventsEntry.DESCRIPCION + " TEXT ,"
                + EventsContract.EventsEntry.PUNTUACION + " TEXT ,"
                + EventsContract.EventsEntry.RESPONSABLE + " TEXT ,"
                + EventsContract.EventsEntry.FECHA + " TEXT ,"
                + EventsContract.EventsEntry.UBICACION + " TEXT ,"
                + EventsContract.EventsEntry.INFOGENERAL + " TEXT,"
                + EventsContract.EventsEntry.FOTO + " TEXT" + ")";
        Log.i("HOLI", "pase por string databasehelper y voy a crear la table");

        db.execSQL(query);

        // Contenedor de valores
        ContentValues values = new ContentValues();
        values.put(EventsContract.EventsEntry.NOMBRE, "softbol");
        values.put(EventsContract.EventsEntry.DESCRIPCION,"En la playa");
        values.put(EventsContract.EventsEntry.PUNTUACION, "12");
        values.put(EventsContract.EventsEntry.RESPONSABLE, "carlos");
        values.put(EventsContract.EventsEntry.FECHA, "12/12/2017");
        values.put(EventsContract.EventsEntry.UBICACION, "cartagena");
        values.put(EventsContract.EventsEntry.INFOGENERAL, "ninguna");
        values.put(EventsContract.EventsEntry.FOTO, "fotico");
        Log.i("HOLI", "pase por execsql para crear la table");
        Log.i("HOLI", "EJECUTO EL INSERT1");
        db.insert(
                EventsContract.EventsEntry.TABLE_NAME,
                null,
                values);

        values = new ContentValues();
        values.put(EventsContract.EventsEntry.NOMBRE, "FUTBOL");
        values.put(EventsContract.EventsEntry.DESCRIPCION,"playa");
        values.put(EventsContract.EventsEntry.PUNTUACION, "182");
        values.put(EventsContract.EventsEntry.RESPONSABLE, "cCarlos");
        values.put(EventsContract.EventsEntry.FECHA, "12/12/2017");
        values.put(EventsContract.EventsEntry.UBICACION, "cartagena");
        values.put(EventsContract.EventsEntry.INFOGENERAL, "Nninguna");
        values.put(EventsContract.EventsEntry.FOTO, "fotico");
        Log.i("HOLI", "pase por execsql para crear la table");

        db.insert(
                EventsContract.EventsEntry.TABLE_NAME,
                null,
                values);
        Log.i("HOLI", "EJECUTO EL INSERT");
        // mockData(db);
    }

//    private void mockData(SQLiteDatabase db) {
//        Log.i("HOLI", "pase por string databasehelper y voy a crear la table");
//
//        mockEvents(db, new Events("gimnasio", "al aire ", "20", "Claudia", "02/05/2017", "bello", "para todos los interesados", "foto"));
//        mockEvents(db, new Events("voley", "al aire libre", "20", "Gabriela", "02/05/2017", "bello", "para todos los interesados", "foto"));
//        mockEvents(db, new Events("basquet", "al ibre", "20", "Daniela", "02/05/2017", "bello", "para todos los interesados", "foto"));
//        mockEvents(db, new Events("futbol", "al libre", "20", "Claudia", "02/05/2017", "bello", "para todos los interesados", "foto"));
//    }
//
//    public long mockEvents(SQLiteDatabase db, Events events) {
//        return db.insert(
//                EventsContract.EventsEntry.TABLE_NAME,
//                null,
//                events.toContentValues());
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ EventsContract.EventsEntry.TABLE_NAME);
        onCreate(db);
    }

    public long insertEvents(Events ev) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EventsContract.EventsEntry.NOMBRE, ev.getNombre());
        values.put(EventsContract.EventsEntry.DESCRIPCION, ev.getDescripcion());
        values.put(EventsContract.EventsEntry.PUNTUACION, ev.getPuntuacion());
        values.put(EventsContract.EventsEntry.RESPONSABLE, ev.getResponsable());
        values.put(EventsContract.EventsEntry.FECHA, ev.getFecha());
        values.put(EventsContract.EventsEntry.UBICACION, ev.getUbicacion());
        values.put(EventsContract.EventsEntry.INFOGENERAL, ev.getInfoGeneral());
        values.put(EventsContract.EventsEntry.FOTO, ev.getFoto());

        long resp = db.insert(EventsContract.EventsEntry.TABLE_NAME, null, values);

        db.close();
        Log.i("VALUES ", values.get("nombre").toString());
        return resp;
    }

    public Cursor getAllEvents() {
        return getReadableDatabase()
                .query(
                        EventsContract.EventsEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getLawyerById(String eventId) {
        Cursor c = getReadableDatabase().query(
                EventsContract.EventsEntry.TABLE_NAME,
                null,
                EventsContract.EventsEntry.NOMBRE + " LIKE ?",
                new String[]{eventId},
                null,
                null,
                null);
        if (c != null)
            c.moveToFirst();
        return c;
    }




}
