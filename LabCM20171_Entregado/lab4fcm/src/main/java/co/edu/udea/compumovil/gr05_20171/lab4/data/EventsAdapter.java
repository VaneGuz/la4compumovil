package co.edu.udea.compumovil.gr05_20171.lab4.data;

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

import co.edu.udea.compumovil.gr05_20171.lab4.R;

/**
 * Created by Michael Garcia on 21/05/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
    public EventsAdapter(Context contexto, List<Events> listEvents) {
        this.listEvents = listEvents;
        this.contexto = contexto;
    }
    private final Context contexto;
    List<Events> listEvents;
    private static ClickListener clickListener;

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_events, parent, false);
        EventsViewHolder holder = new EventsViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        Events events= listEvents.get(position);
        holder.nombre.setText(events.getNombre());
        holder.genero.setText(events.getDescripcion());
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView nombre;
        TextView genero;
        TextView mililitros;

        public EventsViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.tvEvento);
            genero = (TextView) itemView.findViewById(R.id.tvDescripcion);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override    //paso2
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
            mostrarDetalle(getAdapterPosition(), v);
            Log.i("ENTROO ", "onclick");
        }

        @Override
        public boolean onLongClick(View v) {
            Log.i("ENTROO ", "ONLONGCLICK  ENTROOOO");
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        Log.i("ENTROO ", "entro a setitemclick en EventsAdapter");
        EventsAdapter.clickListener = clickListener;
    }

    private  void mostrarDetalle(int position, View view) {
        Log.i("ENTROO Descripcion ", "ENTROOOOOOOO ");

       Intent intent = new Intent(view.getContext(), EventsDetail.class);
         Bundle dato = new Bundle();
          dato.putString("nombre", listEvents.get(position).getNombre() );
        dato.putString("descripcion", listEvents.get(position).getDescripcion());
        dato.putLong("puntuacion", listEvents.get(position).getPuntuacion());
        dato.putString("responsable", listEvents.get(position).getResponsable());
        dato.putString("fecha",listEvents.get(position).getFecha());
        dato.putString("ubicacion",listEvents.get(position).getUbicacion());
        dato.putString("infoGeneral", listEvents.get(position).getInfoGeneral());
        dato.putString("foto", listEvents.get(position).getFoto());
        intent.putExtras(dato);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   contexto.startActivity(intent);

    }

}