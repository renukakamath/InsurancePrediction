package com.example.insuranceprediction;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Custimage2 extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;


	private String[] vehicleimage;
	private String[] vehicleimage1;


	private String[] vehicleimage2;
	private String[] vehicleimage3;


	 public Custimage2(Activity context, String[] vehicleimage, String[] vehicleimage1, String[] vehicleimage2, String[] vehicleimage3  ) {
	        //constructor of this class to get the values from main_activity_class

		 super(context, R.layout.cust_images2, vehicleimage);
	        this.context = context;
		 this.vehicleimage = vehicleimage;

		 	this.vehicleimage1 = vehicleimage1;
		 this.vehicleimage2 = vehicleimage2;

		 this.vehicleimage3 = vehicleimage3;

	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
			//override getView() method

			LayoutInflater inflater = context.getLayoutInflater();
			View listViewItem = inflater.inflate(R.layout.cust_images2, null, true);
			//cust_list_view is xml file of layout created in step no.2

			ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
			ImageView im1 = (ImageView) listViewItem.findViewById(R.id.imageView2);
			ImageView im2 = (ImageView) listViewItem.findViewById(R.id.imageView3);
			ImageView im3 = (ImageView) listViewItem.findViewById(R.id.imageView4);

//			TextView t1=(TextView)listViewItem.findViewById(R.id.textView3);

//			TextView t2=(TextView)listViewItem.findViewById(R.id.textView3);
//			t1.setText("price : "+price[position]+"\nstatus : "+statu[position]+"\ndate : "+date[position]);
//			t2.setText(caption[position]);
			sh=PreferenceManager.getDefaultSharedPreferences(getContext());

			String pth = "http://"+sh.getString("ip", "")+"/"+vehicleimage[position];


			String pth1 = "http://"+sh.getString("ip", "")+"/"+vehicleimage1[position];
			String pth2 = "http://"+sh.getString("ip", "")+"/"+vehicleimage2[position];
			String pth3 = "http://"+sh.getString("ip", "")+"/"+vehicleimage3[position];


			pth = pth.replace("~", "");
			Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

			Log.d("-------------", pth);
			Picasso.with(context)
					.load(pth)
					.placeholder(R.drawable.ic_launcher_background)
					.error(R.drawable.ic_launcher_background).into(im);

			pth1 = pth1.replace("~", "");
			Toast.makeText(context, pth1, Toast.LENGTH_LONG).show();

			Log.d("-------------", pth1);
			Picasso.with(context)
					.load(pth1)
					.placeholder(R.drawable.ic_launcher_background)
					.error(R.drawable.ic_launcher_background).into(im1);

			pth2 = pth2.replace("~", "");
			Toast.makeText(context, pth2, Toast.LENGTH_LONG).show();

			Log.d("-------------", pth2);
			Picasso.with(context)
					.load(pth2)
					.placeholder(R.drawable.ic_launcher_background)
					.error(R.drawable.ic_launcher_background).into(im2);

			pth3 = pth3.replace("~", "");
			Toast.makeText(context, pth3, Toast.LENGTH_LONG).show();

			Log.d("-------------", pth3);
			Picasso.with(context)
					.load(pth3)
					.placeholder(R.drawable.ic_launcher_background)
					.error(R.drawable.ic_launcher_background).into(im3);

			return  listViewItem;
		}

		private TextView setText(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}