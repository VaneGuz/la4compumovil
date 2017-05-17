package co.edu.udea.compumovil.gr03_20171.lab2activities;

import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import co.edu.udea.compumovil.gr03_20171.lab2activities.Service.AppSingleton;
import data.DatabaseHelper;


/**
 * Created by admin on 15/03/2017.
 */

public class FragPerfil extends Fragment implements View.OnClickListener {
    private EditText name;
    private EditText age;
    private EditText email;
    private Button subirImg;
    private ImageView imgView;
    private String nombre;
    private Button btnActualizar;
    private Button btnEditar;

    private static final int SELECT_PICTURE = 100;
    private final String TAG = "LOG ";
    private final String USUARIO = "usuario", CONTRASENA = "contrasena", CORREO = "correo", EDAD = "edad";
    private final String URL_LOGIN = "http://192.168.1.12:3000/api/Logins";
    private final String URL_UPDATE = "http://192.168.1.12:3000/api/Logins";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = (EditText) getActivity().findViewById(R.id.etShowName);
        age = (EditText) getActivity().findViewById(R.id.etShowAge);
        email = (EditText) getActivity().findViewById(R.id.etShowEmail);
        subirImg = (Button) getActivity().findViewById(R.id.btnSubirImagen);
        imgView = (ImageView) getActivity().findViewById(R.id.imvShowImage);
        btnActualizar= (Button) getActivity().findViewById(R.id.btnActualizar);
        btnEditar= (Button) getActivity().findViewById(R.id.btnEditar);
        btnActualizar.setEnabled(false);

        nombre = getActivity().getIntent().getStringExtra("usuario");
        String edad = getActivity().getIntent().getStringExtra("edad");
        String correo = getActivity().getIntent().getStringExtra("email");

//        imgView.setBackground();
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
//                getResources(),R.drawable.ic_account_circle_black_24dp),size.x,size.y,true);

        //android:background="@drawable/ic_account_circle_black_24dp"

        name.setText(nombre);
        age.setText(edad);
        email.setText(correo);


        subirImg.setOnClickListener(this);

        btnEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                name.setEnabled(true);
                email.setEnabled(true);
                age.setEnabled(true);
                btnActualizar.setEnabled(true);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                name.setEnabled(false);
                email.setEnabled(false);
                age.setEnabled(false);
                btnActualizar.setEnabled(false);
                obtenerId();
                String usuario = name.getText().toString();
                String correoElectronico= email.getText().toString();
                String anos= age.getText().toString();
                SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(getContext());
                sharedResponse(USUARIO,usuario );
                sharedResponse(EDAD,anos);
                sharedResponse(CORREO,correoElectronico);


                   JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("usuario", usuario);
                    jsonObject.put("contrasena", m.getString("contrasena", ""));
                    jsonObject.put("correo", correoElectronico);
                    jsonObject.put("edad", anos);
                    jsonObject.put("foto", "ninguna");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequest";


                JsonObjectRequest jsonObjectReq = new JsonObjectRequest( Request.Method.PUT, URL_UPDATE+"/"+m.getString("id",""), jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i(TAG, "USUARIO ACTUALIZADO");

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "Error: " + error.getMessage());

                    }
                });

                // Adding JsonObject request to request queue
                AppSingleton.getInstance(getContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);

            }
        });



        getActivity().setTitle("Perfil");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frag_profile, container, false);
    }

    @Override
    public void onClick(View v) {
        openImageChooser();
    }

    // Choose an image from Gallery
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {

                    // Saving to Database...
                    if (saveImageInDB(selectedImageUri)) {
                        Toast.makeText(getActivity(), "Imagen guardada", Toast.LENGTH_SHORT).show();
                        //imgView.setImageURI(selectedImageUri);
                        loadImageFromDB();
                    }
                }
            }
        }
    }


    private boolean loadImageFromDB() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        try {
            byte[] bytes = db.getImageBD(nombre);

            // Show Image from DB in ImageView
            imgView.setImageBitmap(Utils.getImage(bytes));

            return true;
        } catch (Exception e) {
            Log.e("EventsActivity", "<loadImageFromDB> Error : " + e.getLocalizedMessage());
            return false;
        }

    }

    private boolean saveImageInDB(Uri selectedImageUri) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        try {
            InputStream iStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
            byte[] inputData = Utils.getBytes(iStream);
            int resp = db.insertImage(nombre,inputData);

            Log.d("Respuesta imagen", String.valueOf(resp));

            return true;
        } catch (Exception ioe) {
            Log.e("EventActivity", "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            return false;
        }
//
    }


    public void obtenerId() {
        final SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(getContext());
        Log.i(TAG, "ENTRO OBTENER ID" + m.getString("usuario", null));
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(URL_LOGIN,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = response;
                            String mResponse = m.getString("usuario", null);
                            Log.i(TAG, "usuario en preference" + m.getString("usuario", null));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Log.i(TAG, "USUARIO en object"+ jsonObject.getString("usuario"));
                                if (jsonObject.getString("usuario").equals(mResponse)) {
                                    sharedResponse("id", jsonObject.getString("id"));
                                    Log.i(TAG, "ID: "+ jsonObject.getString("id"));
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
        AppSingleton.getInstance(getContext()).addToRequestQueue(jsonArrayReq, TAG);
    }

    private void sharedResponse(String value, String response) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = m.edit();
        editor.putString(value, response);
        Log.i(TAG, "USUARIO ACTUALIZADO");

        editor.commit();
    }
}

