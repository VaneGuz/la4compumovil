package co.edu.udea.compumovil.gr03_20171.lab2activities.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.Users;

/**
 * Created by Michael Garcia on 15/04/2017.
 */

public class LoginService {

    public LoginService() {
    }
    private String TAG = "LOOOOG  ";

    public void volleyJsonArrayRequest(String user, String pass, String url, Context context) {

        final Users userObj = null;

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG,"entroooooooooooooooooooo");

                        try {
                            JSONArray jsonArray = response;
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Log.i(TAG,jsonObject.getString("usuario"));
                                Log.i(TAG,jsonObject.getString("contrasena"));


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("ERROoooooOOOOR", "Error: " + error.getMessage());

            }
        });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(context).addToRequestQueue(jsonArrayReq, TAG);



    }
}
