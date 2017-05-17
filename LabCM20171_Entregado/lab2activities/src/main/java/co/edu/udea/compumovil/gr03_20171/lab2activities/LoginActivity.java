package co.edu.udea.compumovil.gr03_20171.lab2activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr03_20171.lab2activities.Events.EventsActivityRecycler;
import co.edu.udea.compumovil.gr03_20171.lab2activities.Service.AppSingleton;
import co.edu.udea.compumovil.gr03_20171.lab2activities.Service.LoginService;
import data.DatabaseHelper;
import data.Users;

public class LoginActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText contraseña;
    private final String USUARIO = "usuario", CONTRASENA = "contrasena", CORREO = "correo", EDAD = "edad";
    private static final String JSON_ARRAY_REQUEST_URL = "http://192.168.1.12:3000/api/Logins";
    private LoginService loginService = null;
    private String TAG = "LOOOOG  ";
    DatabaseHelper helper = new DatabaseHelper(this);
    ArrayList<String> infoUs;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = (EditText) findViewById(R.id.etUserLogin);
        contraseña = (EditText) findViewById(R.id.etPasswordLogin);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


    }

    public void onClickLogin(View v) {
        switch (v.getId()) {
            case R.id.btnIniciarSesion:
                String user = usuario.getText().toString();
                String pass = contraseña.getText().toString();
                loginProcess(user, pass);
                break;
            case R.id.tvRegistrar:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void loginProcess(String user, String pass) {

//        String password = helper.searchPass(user);
        obtenerContrasena(user, pass, JSON_ARRAY_REQUEST_URL, getApplicationContext());
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        String mResponse = m.getString("contrasena", null);
        if (mResponse != null && pass.equals(mResponse)) {
            Toast.makeText(LoginActivity.this, "Contraseña correcta", Toast.LENGTH_SHORT).show();

            editor = sharedPref.edit();

            infoUs = helper.searchInfoUser(user);
            editor.putBoolean(getString(R.string.userlogged), true);
            editor.apply();

            Intent intent = new Intent(this, EventsActivity.class);
            intent.putExtra("usuario", user);
            intent.putExtra("edad", m.getString(EDAD,null));
              intent.putExtra("email",m.getString(CORREO,null));

            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        String user, pass;
        Boolean isUserLogged = sharedPref.getBoolean(getString(R.string.userlogged), false);
        user = sharedPref.getString(USUARIO, "");
        pass = sharedPref.getString(CONTRASENA, "");

        if (isUserLogged) {
            // Do something for the logged user
            loginProcess(user, pass);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void sharedResponse(String value, String response) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString(value, response);
        editor.commit();
    }

    public void obtenerContrasena(final String user, final String pass, String url, Context context) {
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = response;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.i(TAG, jsonObject.getString("usuario"));
                                if (jsonObject.getString("usuario").equals(user) && jsonObject.getString("contrasena").equals(pass)) {
                                    sharedResponse(USUARIO, jsonObject.getString("usuario"));
                                    sharedResponse(CONTRASENA, jsonObject.getString("contrasena"));
                                    sharedResponse(CORREO, jsonObject.getString("correo"));
                                    sharedResponse(EDAD, jsonObject.getString("edad"));
//                                  sharedResponse("foto",jsonObject.getString("correo"))
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("ERROOR", "Error: " + error.getMessage());

            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(context).addToRequestQueue(jsonArrayReq, TAG);
    }

}
