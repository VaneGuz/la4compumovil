package co.edu.udea.compumovil.gr03_20171.lab2activities.Events;

import android.provider.BaseColumns;

/**
 * Created by Michael Garcia on 19/03/2017.
 */


public class EventsContract {

    public static abstract class EventsEntry implements BaseColumns {
        public static final String TABLE_NAME ="evento";

        public static final String NOMBRE= "nombre";
        public static final String DESCRIPCION = "descripcion";
        public static final String PUNTUACION= "puntuacion";
        public static final String RESPONSABLE = "responsable";
        public static final String FECHA= "fecha";
        public static final String UBICACION= "ubicacion";
        public static final String INFOGENERAL= "infoGeneral";
        public static final String FOTO= "foto";
    }
}