package co.edu.udea.compumovil.gr03_20171.lab2activities.EventsService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import co.edu.udea.compumovil.gr03_20171.lab2activities.Events.EventsContract;
import co.edu.udea.compumovil.gr03_20171.lab2activities.Events.EventsDatabaseHelper;
import co.edu.udea.compumovil.gr03_20171.lab2activities.Events.EventsDetail;
import co.edu.udea.compumovil.gr03_20171.lab2activities.R;
import co.edu.udea.compumovil.gr03_20171.lab2activities.Service.AppSingleton;
import data.Events;

import static android.content.ContentValues.TAG;

/**
 * Created by Michael Garcia on 16/04/2017.
 */

public class EventsAdapterService extends RecyclerView.Adapter<EventsAdapterService.EventsViewHolder> {

    private final Context contexto;

    Intent intent;
    //    private Cursor items;
    private List<Events> listaEventos;
    private static EventsAdapterService.ClickListener clickListener;
    private EventsDatabaseHelper eventsDatabaseHelper;
    ////NEW
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;
    private static final String JSON_ARRAY_REQUEST_URL = "http://192.168.1.12:3000/api/Eventos";


    public class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        // each data item is just a string in this case
        public TextView evento;
        public TextView descripcion;
        public TextView puntuacion;
        public TextView responsable;
        Activity myMainActivity;


        public EventsViewHolder(View v) {
            super(v);
            Log.i("HOLI", "pase por el constructor de EventsViewHolder");
            evento = (TextView) v.findViewById(R.id.evento);
            descripcion = (TextView) v.findViewById(R.id.descripcion);
            puntuacion = (TextView) v.findViewById(R.id.puntuacion);
            responsable = (TextView) v.findViewById(R.id.responsable);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listaEventos.get(getAdapterPosition());
            // items.moveToPosition(getAdapterPosition());
            mostraDetalle(listaEventos.get(getAdapterPosition()), v);
            clickListener.onItemClick(getAdapterPosition(), v);
            Log.i("ENTROO ", "onclick");
        }

        @Override
        public boolean onLongClick(View v) {
            Log.i("ENTROO ", "ONLONGCLICK  ENTROOOO");
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    private void mostraDetalle(Events e, View view) {

        Log.i("ENTROO Descripcion ", "ENTROOOOOOOO ");
        //  Log.i("ENTROO Descripcion ", aux.getString(items.getColumnIndex(EventsContract.EventsEntry.DESCRIPCION)));

        Intent intent = new Intent(view.getContext(), EventsDetail.class);
        Bundle dato = new Bundle();
        dato.putString("nombre", e.getNombre());
        dato.putString("descripcion", e.getDescripcion());
        dato.putString("puntuacion", e.getPuntuacion());
        dato.putString("responsable", e.getResponsable());
        dato.putString("fecha", e.getFecha());
        dato.putString("ubicacion", e.getUbicacion());
        dato.putString("infoGeneral", e.getInfoGeneral());
        dato.putString("foto", e.getFoto());
        intent.putExtras(dato);
        contexto.startActivity(intent);

    }

    private void llenarLista(List<Events> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public void consultarEventos() {
         // Nueva petición JSONObject
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(JSON_ARRAY_REQUEST_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject objeto = response.getJSONObject(i);
                                Log.i(TAG, objeto.getString("nombre"));


                                Events e = new Events(
                                        objeto.getString("nombre"),
                                        objeto.getString("descripcion"),
                                        objeto.getString("puntuacion"),
                                        objeto.getString("responsable"),
                                        objeto.getString("fecha"),
                                        objeto.getString("ubicacion"),
                                        objeto.getString("infoGeneral"),"foto"

                                );


                                listaEventos.add(e);

                            } catch (JSONException e) {
                                Log.e(TAG, "Error de parsing: " + e.getMessage());
                            }
                        }
                        notifyDataSetChanged();
                        llenarLista(listaEventos);
                        Log.i(TAG, "LA PETICION AL SERVICE FINALIZADA");
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("ERROOR", "Error: " + error.getMessage());

            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(contexto).addToRequestQueue(jsonArrayReq, "com.androidtutorialpoint.volleyJsonArrayRequest");
        // Añadir petición a la co
        // la
        //requestQueue.add(jsArrayRequest);
    }

public  void consultarnuevoEvento(){

    int id = listaEventos.size()+1;

    // Nueva petición JSONObject
    JsonObjectRequest jsonArrayReq = new JsonObjectRequest(JSON_ARRAY_REQUEST_URL+"/"+id,null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Events e = new Events(
                                response.getString("nombre"),
                                response.getString("descripcion"),
                                response.getString("puntuacion"),
                                response.getString("responsable"),
                                response.getString("fecha"),
                                response.getString("ubicacion"),
                                response.getString("infoGeneral"),"foto"
                        );
                        listaEventos.add(e);

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                    notifyDataSetChanged();
                    llenarLista(listaEventos);
                    Log.i(TAG, "LA PETICION AL SERVICE FINALIZADA");
                }

            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            llenarLista(listaEventos);
            Log.i("ERROOR", "Error: " + error.getMessage());

        }
    });
    // Adding String request to request queue
    AppSingleton.getInstance(contexto).addToRequestQueue(jsonArrayReq, "com.androidtutorialpoint.volleyJsonArrayRequest");


}
    // Provide a suitable constructor (depends on the kind of dataset)
    public EventsAdapterService(Context contexto) {
        super();
        Log.i("HOLI", "pase por el constructor");
        this.contexto = contexto;
        consultarEventos();
        listaEventos = new ArrayList<>();
//        this.eventsDatabaseHelper = eventsDatabaseHelper;
    }

    @Override
    public int getItemCount() {
//        items = eventsDatabaseHelper.getAllEvents();
        Log.i("HOLI", "pase por el GETITEMCOUNT");

        if (listaEventos != null && listaEventos.size() >= 1) {
            Log.i("HOLI", "ITEMS DE LA LISTA : " + String.valueOf(listaEventos.size()));
            return listaEventos.size();
        }
        ;
        return 0;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventsAdapterService.EventsViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        Log.i("HOLI", "pase por onCreateViewHolder");
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_events, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new EventsAdapterService.EventsViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EventsAdapterService.EventsViewHolder holder, int position) {
        // items.moveToPosition(position);
        Events e = listaEventos.get(position);

        String s;

        Log.i("HOLI", "pase por bind view holder");

        //s = items.getString(items.getColumnIndex(EventsContract.EventsEntry.NOMBRE));
        s = e.getNombre();

        holder.evento.setText(s);

//        s = items.getString(items.getColumnIndex(EventsContract.EventsEntry.DESCRIPCION));
//        holder.descripcion.setText(s);
//
//        s = items.getString(items.getColumnIndex(EventsContract.EventsEntry.PUNTUACION));
//        holder.puntuacion.setText(s);

    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(EventsAdapterService.ClickListener clickListener) {
        Log.i("ENTROO ", "entro a setitemclick en EventsAdapter");
        EventsAdapterService.clickListener = clickListener;
    }




    /*  private String obtenerIdAlquiler(int posicion, View view) {
      intent = new Intent(view.getContext(), EventsDetail.class);
        Bundle dato = new Bundle();
        items.moveToPosition(posicion);
        dato.putString("nombre",items.getString(items.getColumnIndex(EventsContract.EventsEntry.NOMBRE)));
        dato.putString("descripcion",items.getString(items.getColumnIndex(EventsContract.EventsEntry.DESCRIPCION)));
        dato.putString("puntuacion",items.getString(items.getColumnIndex(EventsContract.EventsEntry.PUNTUACION)));
        dato.putString("responsable",items.getString(items.getColumnIndex(EventsContract.EventsEntry.RESPONSABLE)));
        dato.putString("fecha",items.getString(items.getColumnIndex(EventsContract.EventsEntry.FECHA)));
        dato.putString("ubicacion",items.getString(items.getColumnIndex(EventsContract.EventsEntry.UBICACION)));
        dato.putString("infoGeneral",items.getString(items.getColumnIndex(EventsContract.EventsEntry.INFOGENERAL)));
        dato.putString("foto",items.getString(items.getColumnIndex(EventsContract.EventsEntry.FOTO)));
        intent.putExtras(dato);
        contexto.startActivity(intent);

        items.moveToPosition(posicion);

        if (items != null) {
            if (items.moveToPosition(posicion)) {
                return items.getString(items.getColumnIndex(EventsContract.EventsEntry.NOMBRE));
            }
        }

        return null;
    } */


}
