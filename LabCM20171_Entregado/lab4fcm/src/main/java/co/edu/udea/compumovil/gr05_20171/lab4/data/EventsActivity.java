package co.edu.udea.compumovil.gr05_20171.lab4.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import co.edu.udea.compumovil.gr05_20171.lab4.EventsAdd;
import co.edu.udea.compumovil.gr05_20171.lab4.R;
import co.edu.udea.compumovil.gr05_20171.lab4.firebase.FirebaseReferences;

/**
 * Created by Michael Garcia on 21/05/2017.
 */

public class EventsActivity extends AppCompatActivity {
    RecyclerView rv;
    EditText nombre;
    EditText mililitros;
    EditText genero;
    FirebaseDatabase database;
    DatabaseReference eventsRef;
    List<Events> listEvents;
    EventsAdapter adapter;
 final int RESULT_OK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_recycler);
        nombre = (EditText) findViewById(R.id.nombrePerfume);
        mililitros = (EditText) findViewById(R.id.mililitrosPerfume);
        genero = (EditText) findViewById(R.id.generoPerfume);
        rv = (RecyclerView) findViewById(R.id.recycler);

        rv.setLayoutManager(new LinearLayoutManager(this));
        listEvents = new ArrayList<>();
        adapter = new EventsAdapter(getApplicationContext(), listEvents);
        rv.setAdapter(adapter);


        database = FirebaseDatabase.getInstance();
        eventsRef = database.getReference(FirebaseReferences.EVENT_REFERENCE);

        actualizarElementos();

        adapter.setOnItemClickListener(new EventsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("TAG", "onItemClick position: " + position);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.i("TAG", "onItemLongClick pos = " + position);
            }
        });


        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Hello Snackbar", Snackbar.LENGTH_LONG).show();
                Intent in = new Intent(view.getContext(), EventsAdd.class);
                startActivityForResult(in, RESULT_OK);
            }
        });
        Log.i("LOG", "entroooooo2");
    }

    public void actualizarElementos() {
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("LOG", dataSnapshot.toString());
                // String prueba = dataSnapshot.child("nombre").getValue(String.class);
                listEvents.removeAll(listEvents);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()
                        ) {
                    Events events = snapshot.getValue(Events.class);
                    listEvents.add(events);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LOG", "ERRRROOOOR");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        Log.i("VALUES RETURN ", "hola ");
        if (requestCode == RESULT_OK) {
            Events evento = new Events();

            Log.i("HOLI", "RESULT_OK");
            evento.setNombre(data.getExtras().getString("nombre"));
            evento.setDescripcion(data.getExtras().getString("descripcion"));
            evento.setPuntuacion(data.getExtras().getLong("puntuacion"));
            evento.setResponsable(data.getExtras().getString("responsable"));
            evento.setFecha(data.getExtras().getString("fecha"));
            evento.setUbicacion(data.getExtras().getString("ubicacion"));
            evento.setInfoGeneral(data.getExtras().getString("infoGeneral"));


            eventsRef.push().setValue(evento);

            Log.i("VALUES RETURN ", data.getExtras().getString("nombre"));
            actualizarElementos();

        } else {
            Log.i("LONG DE PRUEBA", "CANCELÓ");
        }

    }
}
