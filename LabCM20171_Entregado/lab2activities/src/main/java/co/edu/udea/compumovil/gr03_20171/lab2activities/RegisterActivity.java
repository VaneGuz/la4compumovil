package co.edu.udea.compumovil.gr03_20171.lab2activities;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udea.compumovil.gr03_20171.lab2activities.Service.AppSingleton;
import data.DatabaseHelper;
import data.Users;

public class RegisterActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText edad;
    private EditText contraseña;
    private EditText confirmPass;
    private EditText correo;
    DatabaseHelper helper = new DatabaseHelper(this);
    private String TAG = "LOOOOG  ";
    private static final String JSON_OBJECT_REQUEST_URL = "http://192.168.1.12:3000/api/Logins";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usuario = (EditText) findViewById(R.id.etUserRegister);
        edad = (EditText) findViewById(R.id.etDateRegister);
        contraseña = (EditText) findViewById(R.id.etPasswordRegister);
        confirmPass = (EditText) findViewById(R.id.etPasswordConfirmer);
        correo = (EditText) findViewById(R.id.etEmail);
    }

    public void onClickRegister(View v) {
        switch (v.getId()) {
            case R.id.btnCrearCuenta:

                String user = usuario.getText().toString();
                String date = edad.getText().toString();
                String email = correo.getText().toString();
                String pass1 = contraseña.getText().toString();
                String pass2 = confirmPass.getText().toString();

                if (!pass1.equals(pass2)) {
                    Toast pass = Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT);
                    pass.show();
                } else if ((email.length() == 0) || (!validateEmail(email))) {
                    correo.setError("Email required!");
                } else {
//                    Users us = new Users();
//                    us.setUsuario(user);
//                    us.setCorreo(email);
//                    us.setContraseña(pass1);
//                    us.setEdad(date);
//                    us.setFoto("usuario");
//
//                    long resp = helper.insertUsers(us);

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("usuario", user);
                        jsonObject.put("contrasena", pass1);
                        jsonObject.put("correo", email);
                        jsonObject.put("edad", date);
                        jsonObject.put("foto", "ninguna");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    crearUsuario(JSON_OBJECT_REQUEST_URL,jsonObject);
                    long resp = 1;
                    Log.d("resp: ", String.valueOf(resp));
                    if (resp != -1) {
                        Toast.makeText(RegisterActivity.this, "Cuenta Creada Correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
                        usuario.setText("");
                    }

               }
                break;
            case R.id.btnCancelar:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void crearUsuario(String url,JSONObject jsonObject) {

        String REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequest";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, jsonObject,
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
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    public final static boolean validateEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
