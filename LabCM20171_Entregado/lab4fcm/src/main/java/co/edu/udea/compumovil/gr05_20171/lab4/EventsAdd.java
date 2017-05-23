package co.edu.udea.compumovil.gr05_20171.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import co.edu.udea.compumovil.gr05_20171.lab4.data.EventsActivity;


/**
 * Created by Michael Garcia on 20/03/2017.
 */

public class EventsAdd extends AppCompatActivity {

    private TextView nombre;
    private TextView descripcion;
    private TextView ubicacion;
    private TextView puntuacion;
    private TextView responsable;
    private TextView fecha;
    private TextView infoGeneral;
    private TextView foto;
    final int RESULT_OK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_events);


    }

    public void crearEvento(View view) {

        Intent intent = new Intent(view.getContext(), EventsActivity.class);

        Bundle datos = new Bundle();
        nombre = (TextView) findViewById(R.id.nombre);
        descripcion = (TextView) findViewById(R.id.descripcion);
        ubicacion = (TextView) findViewById(R.id.ubicacion);
        puntuacion = (TextView) findViewById(R.id.puntuacion);
        responsable = (TextView) findViewById(R.id.responsable);
        fecha = (TextView) findViewById(R.id.fecha);
        infoGeneral = (TextView) findViewById(R.id.infoGeneral);

        datos.putString("nombre", nombre.getText().toString());
        datos.putString("descripcion", descripcion.getText().toString());
        datos.putString("ubicacion", ubicacion.getText().toString());
        datos.putString("puntuacion", puntuacion.getText().toString());
        datos.putString("responsable", responsable.getText().toString());
        datos.putString("fecha", fecha.getText().toString());
        datos.putString("infoGeneral", infoGeneral.getText().toString());


        intent.putExtras(datos);
        setResult(RESULT_OK, intent);
        finish();

    }


}
