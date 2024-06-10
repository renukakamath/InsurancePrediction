package com.example.insuranceprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewdamagedetails extends AppCompatActivity implements JsonResponse {
    ListView l1;
    String[] vehicleimage,vehicleimage1,vehicleimage2,vehicleimage3, price, date,statu,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdamagedetails);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = (ListView) findViewById(R.id.lvview);

//        l1.setOnItemClickListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewdamagedetails.this;
        String q = "/agent_view_damagereqs?login_id="+sh.getString("log_id","" )+"&did="+MyDamagerequest.did;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {


            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                vehicleimage = new String[ja1.length()];

                vehicleimage1 = new String[ja1.length()];


                vehicleimage2 = new String[ja1.length()];
                vehicleimage3 = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    vehicleimage[i] = ja1.getJSONObject(i).getString("vehicleimage");

                    vehicleimage1[i] = ja1.getJSONObject(i).getString("vehicleimage1");

                    vehicleimage2[i] = ja1.getJSONObject(i).getString("vehicleimage2");
                    vehicleimage3[i] = ja1.getJSONObject(i).getString("vehicleimage3");



//                    value[i] = "vehicleimage: " + vehicleimage[i] + "\nprice: " + price[i] + "\nstatu: " + statu[i] + "\ndate: " + date[i] ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);

                Custimage2 a=new Custimage2(this,vehicleimage,vehicleimage1,vehicleimage2,vehicleimage3);
                l1.setAdapter(a);
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}