package co.edu.udea.compumovil.gr05_20171.lab4.data;

import android.support.v7.widget.RecyclerView;
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
    public EventsAdapter(List<Events> listEvents) {
        this.listEvents = listEvents
        ;
    }

    List<Events> listEvents;

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler, parent, false);
        EventsViewHolder holder = new EventsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        Events events= listEvents.get(position);
        holder.nombre.setText(events.getNombre());
        holder.genero.setText(events.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView genero;
        TextView mililitros;

        public EventsViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.textViewPerfume);
            genero = (TextView) itemView.findViewById(R.id.textViewgenero);

        }
    }
}