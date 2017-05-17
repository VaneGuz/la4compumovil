package co.edu.udea.compumovil.gr03_20171.lab2activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udea.compumovil.gr03_20171.lab2activities.Activities.SettingsActivity;
import co.edu.udea.compumovil.gr03_20171.lab2activities.Events.EventsActivityRecycler;
import co.edu.udea.compumovil.gr03_20171.lab2activities.Fragments.AboutFragment;
import co.edu.udea.compumovil.gr03_20171.lab2activities.Service.AppSingleton;

public class EventsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private SearchView searchView;
    private TextView nameUser, email;
    private TextView bienvenido;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        nameUser = (TextView) findViewById(R.id.tvNameUser);
        email = (TextView) findViewById(R.id.tvEmail);
        bienvenido = (TextView) findViewById(R.id.bienvenido);
        bienvenido.setText("BIENVENIDO");

        //String nombre = getIntent().getStringExtra("usuario");
        // String correo = getIntent().getStringExtra("email");

        //nameUser.setText(nombre);
        //email.setText(correo);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = preferences.edit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        nameUser = (TextView) header.findViewById(R.id.tvNameUser);
        email = (TextView) header.findViewById(R.id.tvEmail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String nombre = bundle.getString("usuario");
            String correo = bundle.getString("email");
            editor.putString("pref_username", nombre);
            editor.putString("pref_email", correo);
            editor.apply();
            nameUser.setText(nombre);
            email.setText(correo);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void activateSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, EventsActivity.class)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.events, menu);
        activateSearchView(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_eventos:
                bienvenido.setText("");
                Intent j = new Intent(EventsActivity.this, EventsActivityRecycler.class);
                startActivity(j);
                break;
            case R.id.nav_info:
                bienvenido.setText("");
                fragment = new AboutFragment();
                break;
            case R.id.nav_perfil:
                bienvenido.setText("");
                fragment = new FragPerfil();
                break;
            case R.id.nav_sesion:
                bienvenido.setText("");
                editor.putBoolean(getString(R.string.userlogged), false);
                editor.apply();
                finish();
                break;
            case R.id.nav_settings:
                bienvenido.setText("");
                Intent i = new Intent(EventsActivity.this, SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.nav_update:
                Intent k = new Intent(EventsActivity.this, EventsActivityRecycler.class);
                startActivity(k);

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void editar(View view) {
//        android.app.Fragment frag1 = getFragmentManager().findFragmentById(R.id.content_frame);
//        frag1.getView().findViewById(R.id.btnActualizar).setEnabled(true);
//        frag1.getView().findViewById(R.id.etShowName).setEnabled(true);
//        frag1.getView().findViewById(R.id.etShowEmail).setEnabled(true);
//        frag1.getView().findViewById(R.id.etShowAge).setEnabled(true);
//
//        Toast.makeText(EventsActivity.this, "", Toast.LENGTH_SHORT).show();
//        //name.setEnabled(true);
//        // email.setEnabled(true);
//        // age.setEnabled(true);
//        // btnActualizar.setEnabled(true);
//    }
//
//    public void actualizar(View view) {
//
//        android.app.Fragment frag1 = getFragmentManager().findFragmentById(R.id.content_frame);
//
//        EditText name = (EditText) frag1.getView().findViewById(R.id.etShowName);
//        EditText email = (EditText) frag1.getView().findViewById(R.id.etShowEmail);
//        EditText age = (EditText) frag1.getView().findViewById(R.id.etShowAge);
//        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
//        String mResponse = m.getString("usuario", null);
//       // obtenerId();
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("usuario", name);
//            jsonObject.put("contrasena", m.getString("contrasena", ""));
//            jsonObject.put("correo", email);
//            jsonObject.put("edad", age);
//            jsonObject.put("foto", "ninguna");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Toast.makeText(EventsActivity.this, "", Toast.LENGTH_SHORT).show();
//
//        String REQUEST_TAG = "com.androidtutorialpoint.volleyJsonObjectRequest";
//
//
//        JsonObjectRequest jsonObjectReq = new JsonObjectRequest( Request.Method.PUT, URL_UPDATE, jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i(TAG, "USUARIO ACTUALIZADO");
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i(TAG, "Error: " + error.getMessage());
//
//            }
//        });
//
//        // Adding JsonObject request to request queue
//        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
//        frag1.getView().findViewById(R.id.btnActualizar).setEnabled(false);
//        frag1.getView().findViewById(R.id.etShowName).setEnabled(false);
//        frag1.getView().findViewById(R.id.etShowEmail).setEnabled(false);
//        frag1.getView().findViewById(R.id.etShowAge).setEnabled(false);
//        frag1.getView().findViewById(R.id.btnActualizar).setEnabled(false);
//    }
//
//
//    public void obtenerId() {
//        final SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
//        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(URL_LOGIN,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            JSONArray jsonArray = response;
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                Log.i(TAG, jsonObject.getString("usuario"));
//                                String mResponse = m.getString("usuario", null);
//                                if (jsonObject.getString("usuario").equals(mResponse)) {
//                                    sharedResponse("id", jsonObject.getString("id"));
////                                  sharedResponse("foto",jsonObject.getString("correo"))
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.i("ERROOR", "Error: " + error.getMessage());
//
//            }
//        });
//
//        // Adding JsonObject request to request queue
//        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, TAG);
//    }
//
//    private void sharedResponse(String value, String response) {
//        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = m.edit();
//        editor.putString(value, response);
//        editor.commit();
//    }

}
