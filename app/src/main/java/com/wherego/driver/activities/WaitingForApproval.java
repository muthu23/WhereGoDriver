package com.wherego.driver.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wherego.driver.App;
import com.wherego.driver.DriverMainActivity;
import com.wherego.driver.R;
import com.wherego.driver.helpers.SharedHelper;
import com.wherego.driver.helpers.URLHelper;

import java.util.HashMap;

public class WaitingForApproval extends AppCompatActivity {
    public Handler statusHandler = new Handler();
    Button logoutBtn;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_waiting_for_approval);
        token = SharedHelper.getKey(WaitingForApproval.this, "access_token");
        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(view -> {
            SharedHelper.clearSharedPreferences(WaitingForApproval.this);
            Intent mainIntent = new Intent(WaitingForApproval.this,
                    LoginActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            finishAffinity();
        });

        statusHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkStatus();
                statusHandler.postDelayed(this, 10000);
            }
        }, 10000);

    }

    @Override
    public void onBackPressed() {

    }

    private void checkStatus() {
        String url = URLHelper.BASE + "api/provider/trip";
        final JsonObjectRequest jsonObjectRequest = new
                JsonObjectRequest(Request.Method.GET,
                        url,
                        null,
                        response -> {
                            Log.e("CheckStatus", "" + response.toString());
                            if (response.optString("account_status").equals("approved")) {
//                                statusHandler.removeMessages(0);
                                startActivity(new Intent(WaitingForApproval.this,
                                        DriverMainActivity.class));
                            }
                        }, error -> {
                    Log.v("Error", error.toString());

                    if (error instanceof NoConnectionError) {
                        displayMessage(getString(R.string.oops_connect_your_internet));
                    } else if (error instanceof NetworkError) {
                        displayMessage(getString(R.string.oops_connect_your_internet));
                    } else if (error instanceof TimeoutError) {
                        checkStatus();
                    }


                }) {
                    @Override
                    public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("X-Requested-With", "XMLHttpRequest");
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };
        App.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    public void displayMessage(String toastString) {
        Toast.makeText(WaitingForApproval.this, toastString, Toast.LENGTH_SHORT).show();
    }

}
