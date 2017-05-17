package co.edu.udea.compumovil.gr03_20171.lab2activities.Events;

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

import java.util.List;

import co.edu.udea.compumovil.gr03_20171.lab2activities.R;
import data.Events;


/**
 * Created by Michael Garcia on 18/03/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private final Context contexto;
    private List<Events> eventos;
    Intent intent;
    private Cursor items;
    private static ClickListener clickListener;
    private EventsDatabaseHelper eventsDatabaseHelper;

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
            items.moveToPosition(getAdapterPosition());
            mostraDetalle(items, v);
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

    private void mostraDetalle(Cursor aux, View view) {

        Log.i("ENTROO Descripcion ", "ENTROOOOOOOO ");
        Log.i("ENTROO Descripcion ",aux.getString(items.getColumnIndex(EventsContract.EventsEntry.DESCRIPCION)) );

        Intent intent = new Intent(view.getContext(), EventsDetail.class);
        Bundle dato = new Bundle();
        dato.putString("nombre", aux.getString(aux.getColumnIndex(EventsContract.EventsEntry.NOMBRE)));
        dato.putString("descripcion", aux.getString(aux.getColumnIndex(EventsContract.EventsEntry.DESCRIPCION)));
        dato.putString("puntuacion", aux.getString(aux.getColumnIndex(EventsContract.EventsEntry.PUNTUACION)));
        dato.putString("responsable", aux.getString(aux.getColumnIndex(EventsContract.EventsEntry.RESPONSABLE)));
        dato.putString("fecha", aux.getString(aux.getColumnIndex(EventsContract.EventsEntry.FECHA)));
        dato.putString("ubicacion", aux.getString(aux.getColumnIndex(EventsContract.EventsEntry.UBICACION)));
        dato.putString("infoGeneral", aux.getString(aux.getColumnIndex(EventsContract.EventsEntry.INFOGENERAL)));
        dato.putString("foto", aux.getString(aux.getColumnIndex(EventsContract.EventsEntry.FOTO)));
        intent.putExtras(dato);
        contexto.startActivity(intent);

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventsAdapter(Context contexto, EventsDatabaseHelper eventsDatabaseHelper) {
        super();
        Log.i("HOLI", "pase por el constructor");
        this.contexto = contexto;
        this.eventsDatabaseHelper = eventsDatabaseHelper;
    }

    @Override
    public int getItemCount() {
        items = eventsDatabaseHelper.getAllEvents();
        Log.i("HOLI", "pase por el GETITEMCOUNT");
        if (items != null) {
            Log.i("HOLI", "ENTRE AL GETITEMCOUNT");
            return items.getCount();
        }
        ;
        return 0;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        Log.i("HOLI", "pase por onCreateViewHolder");
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_events, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new EventsViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;

        Log.i("HOLI", "pase por bind view holder");
        s = items.getString(items.getColumnIndex(EventsContract.EventsEntry.NOMBRE));
        holder.evento.setText(s);

        s = items.getString(items.getColumnIndex(EventsContract.EventsEntry.DESCRIPCION));
        holder.descripcion.setText(s);

        s = items.getString(items.getColumnIndex(EventsContract.EventsEntry.PUNTUACION));
        holder.puntuacion.setText(s);
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        Log.i("ENTROO ", "entro a setitemclick en EventsAdapter");
        EventsAdapter.clickListener = clickListener;
    }
    private String obtenerIdAlquiler(int posicion, View view) {
       /* intent = new Intent(view.getContext(), EventsDetail.class);
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
        */

        items.moveToPosition(posicion);

        if (items != null) {
            if (items.moveToPosition(posicion)) {
                return items.getString(items.getColumnIndex(EventsContract.EventsEntry.NOMBRE));
            }
        }
        return null;
    }

}
