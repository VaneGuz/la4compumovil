package co.edu.udea.compumovil.gr05_20171.lab4.data;

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

import java.util.ArrayList;
import java.util.List;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = (EditText) findViewById(R.id.nombrePerfume);
        mililitros = (EditText) findViewById(R.id.mililitrosPerfume);
        genero = (EditText) findViewById(R.id.generoPerfume);
        rv = (RecyclerView) findViewById(R.id.recycler);
        final EventsAdapter adapter;
        rv.setLayoutManager(new LinearLayoutManager(this));
        listEvents = new ArrayList<>();
        adapter = new EventsAdapter(listEvents);
        rv.setAdapter(adapter);


        database = FirebaseDatabase.getInstance();
        eventsRef = database.getReference(FirebaseReferences.PERFUME_REFERENCE);


        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("LOG", dataSnapshot.toString());
                // String prueba = dataSnapshot.child("nombre").getValue(String.class);
                listEvents.removeAll(listEvents);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()
                        ) {
                    Events events= snapshot.getValue(Events.class);
                    listEvents.add(events);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LOG", "ERRRROOOOR");
            }
        });
        Log.i("LOG", "entroooooo2");

    }


    public void crearEvento(View view) {
        Events events= new Events();
        events.setNombre(nombre.getText().toString());
        events.setDescripcion(genero.getText().toString());

        eventsRef.push().setValue(events);


    }
}
