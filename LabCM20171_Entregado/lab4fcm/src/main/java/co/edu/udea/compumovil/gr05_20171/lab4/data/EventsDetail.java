package co.edu.udea.compumovil.gr05_20171.lab4.data;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import co.edu.udea.compumovil.gr05_20171.lab4.R;


/**
 * Created by Michael Garcia on 19/03/2017.
 */

public class EventsDetail extends AppCompatActivity {
    private TextView nombre;
    private TextView descripcion;
    private TextView ubicacion;
    private TextView puntuacion;
    private TextView responsable;
    private TextView fecha;
    private TextView infoGeneral;
    private TextView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_events);

        nombre = (TextView) findViewById(R.id.nombre);
        descripcion = (TextView)findViewById(R.id.descripcion);
        puntuacion = (TextView) findViewById(R.id.puntuacion);
        responsable = (TextView) findViewById(R.id.responsable);
        fecha = (TextView) findViewById(R.id.fecha);
        ubicacion = (TextView)findViewById(R.id.ubicacion);
        infoGeneral = (TextView) findViewById(R.id.infoGeneral);


        Bundle dato = getIntent().getExtras();
        nombre.setText(dato.getString("nombre"));
        descripcion.setText(dato.getString("descripcion"));
        puntuacion.setText(dato.getString("puntuacion"));
        responsable.setText(dato.getString("responsable"));
        fecha.setText(dato.getString("fecha"));
        ubicacion.setText(dato.getString("ubicacion"));
        infoGeneral.setText(dato.getString("infoGeneral"));

    }

    public void borrarEvento(View view){

    }


}
