package com.example.insuranceprediction;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewPolicyDetails extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] policy, amount, no_ofdays,policy_id,value;
    SharedPreferences sh;

    public static String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_policy_details);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = (ListView) findViewById(R.id.lvview);

        l1.setOnItemClickListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewPolicyDetails.this;
        String q = "/agent_view_policy";
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
                policy = new String[ja1.length()];

                amount = new String[ja1.length()];
                no_ofdays = new String[ja1.length()];

                policy_id = new String[ja1.length()];
//                    date = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    policy[i] = ja1.getJSONObject(i).getString("policy");

                    amount[i] = ja1.getJSONObject(i).getString("amount");
                    no_ofdays[i] = ja1.getJSONObject(i).getString("no_ofdays");
                    policy_id[i] = ja1.getJSONObject(i).getString("policy_id");



                    value[i] = "policy: " + policy[i] + "\namount: " + amount[i] + "\nno of days: " + no_ofdays[i];

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pid=policy_id[i];

        final CharSequence[] items = {"Request policy"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPolicyDetails.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Request policy")) {
                    startActivity(new Intent(getApplicationContext(), Requestpolicy.class));
                }



            }

        });
        builder.show();
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Agenthome.class);
        startActivity(b);
    }
    }
