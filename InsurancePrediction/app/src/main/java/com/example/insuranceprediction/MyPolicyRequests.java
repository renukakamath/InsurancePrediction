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

public class MyPolicyRequests extends AppCompatActivity implements JsonResponse {
    ListView l1;
    String[] vechilenum, modelnum, enginenum,statu,date,value;
    SharedPreferences sh;

    public static String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_policy_requests);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = (ListView) findViewById(R.id.lvview);

//        l1.setOnItemClickListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) MyPolicyRequests.this;
        String q = "/agent_view_mypolicyreq?login_id="+sh.getString("log_id","" );
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
                vechilenum = new String[ja1.length()];

                modelnum = new String[ja1.length()];
                enginenum = new String[ja1.length()];

                statu = new String[ja1.length()];
                    date = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    vechilenum[i] = ja1.getJSONObject(i).getString("vechilenum");

                    modelnum[i] = ja1.getJSONObject(i).getString("modelnum");
                    enginenum[i] = ja1.getJSONObject(i).getString("enginenum");
                    statu[i] = ja1.getJSONObject(i).getString("status");
                    date[i] = ja1.getJSONObject(i).getString("date");



                    value[i] = "vechilenum: " + vechilenum[i] + "\nmodelnum: " + modelnum[i] + "\nenginenum: " + enginenum[i] + "\nstatus: " + statu[i] + "\ndate: " + date[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}