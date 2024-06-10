package com.example.insuranceprediction;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddDamagerequest extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {
    ImageButton im1, im2,im3,im4;
    Button b1;
    EditText e1,e2;
    TextView t1,t2;
    Spinner s1;


    public static String pid;


    String log_id, description,fdate;

    private int mYear, mMonth, mDay, mHour, mMinute;
    //	public String encodedImage = "", path = "";
//	public String en1, en2;
//	public static String labels, pre;
    String[] policyrequest_id, policy, value,details;
    String typeid;
//	private final int GALLERY_CODE = 201, CAMERA_PIC_REQUEST = 301;

//	private Uri mImageCaptureUri;

//	String fln, ftype, fname, upLoadServerUri;

    //	byte[] byteArray, by1, by2;
    SharedPreferences sh;

    //	File f = null;
    int flag = 0;

//	private String imagename = "";
//	public static Bitmap image;

    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;
    byte[] byteArray = null,by1=null,by2 = null,by3 = null,by4 = null;

//	final Calendar calendar=Calendar.getInstance() ;
//	final int year = calendar.get(calendar.YEAR);
//	final int month =calendar.get(calendar.MONTH);
//	final  int day =calendar.get(calendar.DAY_OF_MONTH);

    private int year, month, day, hour, minute;



    private static final int CAMERA_CODE = 101, CROPING_CODE = 301,
            READ_EXTERNAL_STORAGE_PERMISSION = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_damagerequest);


//        t1 = (TextView) findViewById(R.id.tvservice_names);
//		t1.setText(View_services.service_names);
//        t2 = (TextView) findViewById(R.id.tvprovider);
//		t2.setText(Service_providers.provider_names);

        b1 = (Button) findViewById(R.id.btn);


        s1=(Spinner)findViewById(R.id.spinner);

        s1.setOnItemSelectedListener(this);
//		e1 = (EditText) findViewById(R.id.desc);
//        e2 = (EditText) findViewById(R.id.fdate);

//		e2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//
//				DatePickerDialog dialog = new DatePickerDialog(Addservice.this, new DatePickerDialog.OnDateSetListener() {
//
//					@Override
//					public void onDateSet(DatePicker view, int year,
//										  int monthOfYear, int dayOfMonth) {
//
//						e2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//
//					}
//				}, mYear, mMonth, mDay);
//				dialog.show();
//			}
//		});



//		e2.setOnClickListener(new View.OnClickListener() {
//			@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//			@Override
//			public void onClick(View view) {
//				showDatePicker();
//			}
//		});


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) AddDamagerequest.this;
        String q = "/dropdownpolice";
        q = q.replace(" ", "%20");
        JR.execute(q);

        im1 = (ImageButton) findViewById(R.id.img1);
        im2 = (ImageButton) findViewById(R.id.img2);
        im3 = (ImageButton) findViewById(R.id.img3);
        im4 = (ImageButton) findViewById(R.id.img4);


//		JsonReq jr = new JsonReq();
//		jr.json_response = (JsonResponse) Addservice.this;
//		String q = "/service/";
//		jr.execute(q);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sh = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());

                log_id = sh.getString("logid", "");
//				description = e1.getText().toString();
//				fdate = e2.getText().toString();
                try {
                    // SharedPreferences sh =
                    // PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    // String uid = sh.getString("uid", "");

                    String q = "http://" + Ipsettings.text + "/api/agent_damage_request/";

                    Toast.makeText(getApplicationContext(),
                                    "Byte Array:" + by1.length, Toast.LENGTH_LONG)
                            .show();
                    Toast.makeText(getApplicationContext(),
                                    "Byte Array:" + by2.length, Toast.LENGTH_LONG)
                            .show();

                    Map<String, byte[]> aa = new HashMap<String, byte[]>();

                    aa.put("image", by1);
//                    aa.put("description0", "Aadhar copy".getBytes());
//                    aa.put("description1", "Pan Card".getBytes());
//                    aa.put("description2", "Documents".getBytes());
//                    aa.put("description3", "Photo".getBytes());


                    aa.put("image1", by2);
                    aa.put("image2", by3);
                    aa.put("image3", by4);
                    aa.put("log_id", Login.logid.getBytes());
                    aa.put("pid", pid.getBytes());
//					Log.d(q, "");
                    FileUploadAsync fua = new FileUploadAsync(q);
                    fua.json_response = (JsonResponse) AddDamagerequest.this;
                    fua.execute(aa);
                    // String data = fua.getResponse();
                    // stopService(new
                    // Intent(getApplicationContext(),Capture.class));
                    // Log.d("response=======", data);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                                    "Exception upload : " + e, Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        im1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                flag = 1;
                selectImageOption();

            }
        });
        im2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                flag = 2;
                selectImageOption();

            }
        });
        im3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                flag = 3;
                selectImageOption();

            }
        });
        im4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                flag = 4;
                selectImageOption();

            }
        });

    }




    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void showDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Create DatePickerDialog and set minimum date to the current date
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddDamagerequest.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String tempDob = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        try {
                            e2.setText(sdf.format(sdf.parse(tempDob)));
                        } catch (Exception e) {
                            // Handle exception
                        }
                    }
                }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis()); // Set minimum date to current date
        datePickerDialog.show();
    }





    private void selectImageOption() {

		/*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(AddDamagerequest.this);
        builder.setTitle("Take Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
                path = FileUtils.getPath(this, uri);
//				Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                File fl = new File(path);
                int ln = (int) fl.length();

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                if(flag==1) {
                    by1 = bos.toByteArray();

//					Toast.makeText(getApplicationContext(), "by1 : " + by1, Toast.LENGTH_LONG).show();

                    Bitmap bit = BitmapFactory.decodeByteArray(by1, 0, by1.length);
                    im1.setImageBitmap(bit);

                    String str = Base64.encodeToString(by1, Base64.DEFAULT);
                    encodedImage = str;
                }
                if(flag==2) {
                    by2 = bos.toByteArray();

                    Bitmap bit = BitmapFactory.decodeByteArray(by2, 0, by2.length);
                    im2.setImageBitmap(bit);

                    String str = Base64.encodeToString(by2, Base64.DEFAULT);
                    encodedImage = str;
                }
                if(flag==3) {
                    by3 = bos.toByteArray();

                    Bitmap bit = BitmapFactory.decodeByteArray(by2, 0, by3.length);
                    im3.setImageBitmap(bit);

                    String str = Base64.encodeToString(by3, Base64.DEFAULT);
                    encodedImage = str;
                }
                if(flag==4) {
                    by4 = bos.toByteArray();

                    Bitmap bit = BitmapFactory.decodeByteArray(by2, 0, by4.length);
                    im4.setImageBitmap(bit);

                    String str = Base64.encodeToString(by4, Base64.DEFAULT);
                    encodedImage = str;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                if(flag==1) {
                    im1.setImageBitmap(thumbnail);
                    by1 = baos.toByteArray();

                    String str = Base64.encodeToString(by1, Base64.DEFAULT);
                    encodedImage = str;
                }
                else if(flag==2)
                {
                    im2.setImageBitmap(thumbnail);
                    by2 = baos.toByteArray();

                    String str = Base64.encodeToString(by2, Base64.DEFAULT);
                    encodedImage = str;
                }
                else if(flag==3)
                {
                    im3.setImageBitmap(thumbnail);
                    by3 = baos.toByteArray();

                    String str = Base64.encodeToString(by3, Base64.DEFAULT);
                    encodedImage = str;
                }
                else if(flag==4)
                {
                    im4.setImageBitmap(thumbnail);
                    by4 = baos.toByteArray();

                    String str = Base64.encodeToString(by4, Base64.DEFAULT);
                    encodedImage = str;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.addservice, menu);
//        return true;
//    }



    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method = jo.getString("method");

			 if(method.equalsIgnoreCase("viewproductspinner")) {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    policyrequest_id = new String[ja1.length()];

                    policy = new String[ja1.length()];
                    value = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        policyrequest_id[i] = ja1.getJSONObject(i).getString("policyrequest_id");
                        policy[i] = ja1.getJSONObject(i).getString("vechilenum");


                        value[i] = policy[i];


                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);
                    s1.setAdapter(ar);
                }

            }


            if (method.equalsIgnoreCase("upload_image")) {
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Added Successfully",
                            Toast.LENGTH_LONG).show();
                    //
                    startActivity(new Intent(getApplicationContext(), Agenthome.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed....",
                            Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "" + e,
                    Toast.LENGTH_LONG).show();
        }
    }

//	@Override
//	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//							   long arg3) {
//		typeid=service_type_id[arg2];
//
//
//	}
//
//	@Override
//	public void onNothingSelected(AdapterView<?> arg0) {
//		// TODO Auto-generated method stub
//
//	}

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Agenthome.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        pid=policyrequest_id[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}