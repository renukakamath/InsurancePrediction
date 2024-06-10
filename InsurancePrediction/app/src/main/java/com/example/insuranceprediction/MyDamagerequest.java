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

public class MyDamagerequest extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] vehicleimage,vehicleimage1,vehicleimage2, price, date,statu,value,damagerequest_id;
    SharedPreferences sh;
    public static String did;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_damagerequest);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = (ListView) findViewById(R.id.lvview);

        l1.setOnItemClickListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) MyDamagerequest.this;
        String q = "/agent_view_damagereq?login_id="+sh.getString("log_id","" );
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

                price = new String[ja1.length()];


                statu = new String[ja1.length()];
                date = new String[ja1.length()];
                value = new String[ja1.length()];

                damagerequest_id= new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    vehicleimage[i] = ja1.getJSONObject(i).getString("vehicleimage");

                    price[i] = ja1.getJSONObject(i).getString("price");

                    statu[i] = ja1.getJSONObject(i).getString("status");
                    date[i] = ja1.getJSONObject(i).getString("date");

                    damagerequest_id[i] = ja1.getJSONObject(i).getString("damagerequest_id");

                    value[i] = "vehicleimage: " + vehicleimage[i] + "\nprice: " + price[i] + "\nstatu: " + statu[i] + "\ndate: " + date[i] ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);

                Custimage1 a=new Custimage1(this,vehicleimage,price,statu,date);
                l1.setAdapter(a);
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        did=damagerequest_id[i];

        final CharSequence[] items = {"Details"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MyDamagerequest.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Details")) {
                    startActivity(new Intent(getApplicationContext(), Viewdamagedetails.class));
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
