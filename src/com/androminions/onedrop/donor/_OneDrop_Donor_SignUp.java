package com.androminions.onedrop.donor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androminions.onedropdemo.R;
import com.androminions.onedrop.internet.HTTPPlace;
import com.androminions.onedrop.internet.HTTPRequest;
import com.androminions.onedrop.internet.ParseHTTPResponse;
import com.example.onedrop.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class _OneDrop_Donor_SignUp extends Activity{

	TextView email;
	TextView name;
	TextView password;
	TextView confirmPass;
	Spinner bloodGroup;
	TextView area;
	TextView contact;


	String stemail;
	String stname;
	String stpass;
	String stbloodgroup;
	String starea;
	String stcontact;

	Double latitude;
	Double longitude;
	String place;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onedrop_donor_signup);
		Button signUp=(Button)findViewById(R.id.bSignUp);


		email= (TextView) findViewById(R.id.etDonorEmail);
		name= (TextView) findViewById(R.id.etNewDonorName);
		password= (TextView) findViewById(R.id.etNewDonorPassword);
		confirmPass= (TextView) findViewById(R.id.etNewDonorConfirmPassword);
		bloodGroup= (Spinner) findViewById(R.id.newDonorBloodGroup);
		area = (TextView) findViewById(R.id.etNewDonorArea);
		contact= (TextView) findViewById(R.id.etNewDonorContact);

		signUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(validateInfo()){
					insertData();

				}
			}
		});
	}
	public boolean validateInfo(){
		stemail=email.getText().toString();
		stname=name.getText().toString();
		stpass=password.getText().toString();
		String stconfirm=confirmPass.getText().toString();
		stbloodgroup=String.valueOf(bloodGroup.getSelectedItem());
		starea=area.getText().toString();
		stcontact=contact.getText().toString();

		String tempo1=stemail.replaceAll("\\s","");
		String tempo2=stname.replaceAll("\\s","");
		String tempo3=stpass.replaceAll("\\s","");
		String tempo4=starea.replaceAll("\\s","");
		String tempo5=stconfirm.replaceAll("\\s","");
		String tempo6=stcontact.replaceAll("\\s","");
		if(tempo1.compareTo("")!=0 && tempo2.compareTo("")!=0 && tempo3.compareTo("")!=0 && tempo4.compareTo("")!=0 && tempo5.compareTo("")!=0 && tempo6.compareTo("")!=0){
			if(stpass.compareTo(stconfirm)==0){
				return true;
			}
			else{
				Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
			}
		}
		else{
			Toast.makeText(this, "None of the fields can be left empty", Toast.LENGTH_SHORT).show();
		}
		return false;
	}
	public void insertData(){
		if(getLocationLongitudeLatitude()){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			
			alertDialogBuilder.setTitle("Confirm Location");

			// set dialog message
			alertDialogBuilder
			.setMessage("Are you this is your current location:\n"+place)
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					HTTPRequest getResults=new HTTPRequest(_OneDrop_Donor_SignUp.this);
					String url="http://www.androminions.com/OneDropServerFiles/Donor/onedrop_donor_signup.php?"+
									"latitude="+URLEncoder.encode(latitude+"") +
									"&longitude="+URLEncoder.encode(longitude+"")+
									"&email="+URLEncoder.encode(stemail)+
									"&donorFullName="+URLEncoder.encode(stname)+
									"&donorPassword="+URLEncoder.encode(stpass)+
									"&donorBloodGroup="+URLEncoder.encode(stbloodgroup)+
									"&donorContact="+URLEncoder.encode(stcontact)+
									"&place="+URLEncoder.encode(place);
					System.out.println(url);
					List<NameValuePair> params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("latitude", latitude+""));
					params.add(new BasicNameValuePair("longitude", longitude+""));
					params.add(new BasicNameValuePair("donorFullName", stname));
					params.add(new BasicNameValuePair("email", stemail));
					params.add(new BasicNameValuePair("donorPassword", stpass));
					params.add(new BasicNameValuePair("donorBloodGroup", stbloodgroup));
					params.add(new BasicNameValuePair("place", place));
					params.add(new BasicNameValuePair("donorContact", stcontact));
					getResults.execute(url,params);
					try {
						getResults.get();
						if(new ParseHTTPResponse(getResults.jsonResult,getResults.responseOK).ParseJSONResult()){
							startActivity(new Intent(_OneDrop_Donor_SignUp.this,_OneDrop_Donor_AfterSignUp.class));
						}
					}catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();					}
				}
			})
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					dialog.cancel();
				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}		
		else{
			Toast.makeText(this, "Error Connecting Server", Toast.LENGTH_SHORT).show();
		}
		
		
}
public boolean getLocationLongitudeLatitude(){
	HTTPPlace getPlace=new HTTPPlace(this);
	getPlace.execute(starea);
	try{
		getPlace.get();
		JSONObject jsonObject = new JSONObject();
		jsonObject = new JSONObject(getPlace.jsonResult);

		double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location")
				.getDouble("lng");

		double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getJSONObject("geometry").getJSONObject("location")
				.getDouble("lat");
		place=((JSONArray)jsonObject.get("results")).getJSONObject(0)
				.getString("formatted_address");
		latitude=lat;
		longitude=lng;

		Log.d("latitude", "" + lat);
		Log.d("longitude", "" + lng);
		return true;
	}catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;

}
}
