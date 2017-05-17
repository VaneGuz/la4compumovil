package co.edu.udea.compumovil.gr03_20171.lab2activities.Events;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udea.compumovil.gr03_20171.lab2activities.EventsService.EventsAdapterService;
import co.edu.udea.compumovil.gr03_20171.lab2activities.R;
import co.edu.udea.compumovil.gr03_20171.lab2activities.Service.AppSingleton;
import data.Events;


/**
 * Created by Michael Garcia on 18/03/2017.
 */

public class EventsActivityRecycler extends AppCompatActivity {
    static final int RESULT_OK = 1;  // The request code
    private static final String JSON_OBJECT_REQUEST_URL = "http://192.168.1.12:3000/api/Eventos";

    private static final String TAG = "MENSAJE";

    private RecyclerView mRecyclerView;
    private EventsAdapterService mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    private EventsDatabaseHelper eventsDBHelper;

    private Toolbar toolbar;
    private SearchView searchView;
    private TextView nameUser, email;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_events);

        mRecyclerView = (RecyclerView) findViewById(R.id.reciclador);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        eventsDBHelper = new EventsDatabaseHelper(this);

        mAdapter = new EventsAdapterService(this);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new EventsAdapterService.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                    Log.i(TAG, "onItemClick position: " + position);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.i(TAG, "onItemLongClick pos = " + position);
            }
        });


        //BOTON FLOTANTE
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Hello Snackbar", Snackbar.LENGTH_LONG).show();
                Intent in = new Intent(view.getContext(), EventsAdd.class);


                startActivityForResult(in, RESULT_OK);
            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == RESULT_OK) {
            Events evento;
            Log.i("HOLI", "RESULT_OK");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("nombre",data.getExtras().getString("nombre"));
                jsonObject.put("descripcion",data.getExtras().getString("descripcion"));
                jsonObject.put("puntuacion",data.getExtras().getString("puntuacion"));
                jsonObject.put("responsable",data.getExtras().getString("responsable"));
                jsonObject.put("fecha",data.getExtras().getString("fecha"));
                jsonObject.put("ubicacion",data.getExtras().getString("ubicacion"));
                jsonObject.put("infoGeneral",data.getExtras().getString("infoGeneral"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            eventoNuevo(jsonObject);
            Log.i("VALUES RETURN ", data.getExtras().getString("nombre"));

            mAdapter.consultarnuevoEvento();
//            Log.i("LONG DE PRUEBA", prueba.toString());
        }else{
            Log.i("LONG DE PRUEBA", "CANCELÓ");
        }

    }

    private void eventoNuevo(JSONObject jsonObject) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequest";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(JSON_OBJECT_REQUEST_URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "USUARIO CREADO");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Error: " + error.getMessage());

            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, "");



    }


}