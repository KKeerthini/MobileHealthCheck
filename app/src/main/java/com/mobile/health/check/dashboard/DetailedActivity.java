package com.mobile.health.check.dashboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.health.check.R;
import com.mobile.health.check.common.SharedPref;
import com.mobile.health.check.common.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailedActivity extends Activity {

    SharedPref sharedPref;
    ProgressDialog progress;
    private int progressBarStatus = 0;
    private long fileSize = 0;
    private Handler progressBarHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(getApplicationContext());
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        setContentView(R.layout.activity_detailed);
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
        if (name.equalsIgnoreCase("person 2")) {
            sendGroupPush(getApplicationContext(), sharedPref.getString("token"), "VIN verification failed");
        }

    }

    public void sendGroupPush(Context context, String tokenlist, String message) {
        String msg = message;
        String title = context.getString(R.string.app_name);
        JSONArray regId = null;
        JSONObject objData = null;
        JSONObject data = null;
        JSONObject notif = null;
        try {
            regId = new JSONArray();
            // for (int i = 0; i < tokenlist.size(); i++) {
            regId.put(tokenlist);
            // }
            data = new JSONObject();
            data.put("message", message);
            notif = new JSONObject();
            notif.put("title", title);
            notif.put("text", msg);

            objData = new JSONObject();
            objData.put("registration_ids", regId);
            objData.put("data", data);
            objData.put("notification", notif);
            Log.e("!_@rj@_group_PASS:>", objData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send", objData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("!_@rj@_@@_SUCESS", response + "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("!_@rj@_@@_Errors--", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + Utilities.SERVER_KEY);
                params.put("Content-Type", "application/json");
                Log.e("!_@rj@_@@PUSH_headrers", "::> " + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }
}
