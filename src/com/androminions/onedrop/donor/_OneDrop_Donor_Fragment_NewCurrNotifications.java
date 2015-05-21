package com.androminions.onedrop.donor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androminions.onedrop.internet.HTTPRequest;
import com.androminions.onedrop.internet.ParseHTTPResponse;
import com.androminions.onedrop.patient._OneDrop_Patient_POJO;
import com.androminions.onedropdemo.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class _OneDrop_Donor_Fragment_NewCurrNotifications extends Fragment{


	ArrayList<_OneDrop_Patient_POJO> patients;
	ListView patientsList;

	_OneDrop_Donor_POJO donor;


	MyCustomAdapter adapter=null;
	int start=0;
	int limit=10;
	boolean loadingMore=false;
	boolean userScrolled=false;

	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_currnotifications, container, false);
		donor=getDonorInfo();


		return rootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		donor=getDonorInfo();

		patientsList=(ListView)getActivity().findViewById(R.id.listAllPatients);
		patients=new ArrayList<_OneDrop_Patient_POJO>();

		adapter=new MyCustomAdapter(getActivity(),R.layout.row_patient_view,patients);
		patientsList.setAdapter(adapter);
		patientsList.setTextFilterEnabled(true);	
		patientsList.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(scrollState==SCROLL_STATE_IDLE){
					if(patientsList.getLastVisiblePosition()>=(patientsList.getCount()-1)){
						displayResults();
					}					
				}				
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {				
			}

		});
		displayResults();
	}

	public _OneDrop_Donor_POJO getDonorInfo(){
		_OneDrop_Donor_POJO donor=new _OneDrop_Donor_POJO();
		String jsonResult=getActivity().getSharedPreferences(MyPREFERENCES, getActivity().MODE_PRIVATE).getString("DonorInfo", "");
		try{
			JSONObject json =new JSONObject(jsonResult);
			JSONArray donorInfo = json.getJSONArray("donorInfo");
			for(int i=0;i<donorInfo.length();i++){

				JSONObject obj=donorInfo.getJSONObject(i);


				donor.setEmail(obj.getString("email"));
				donor.setPassword(obj.getString("password"));
				donor.setFullName(obj.getString("name"));
				donor.setContact(obj.getString("contact"));
				donor.setBloodGroup(obj.getString("bloodGroup"));
				donor.setArea(obj.getString("place"));
				donor.setLatitude(Double.parseDouble(obj.getString("latitude")));
				donor.setLongitude(Double.parseDouble(obj.getString("longitude")));


			}

		}catch(JSONException e){
			e.printStackTrace();
		}


		return donor;
	}
	public void displayResults(){		

		HTTPRequest getResults=new HTTPRequest();
		String url="http://www.androminions.com/OneDropServerFiles/Donor/donor_get_patient_requests.php?"+
				"email="+URLEncoder.encode(donor.getEmail())+"&"+
				"start="+URLEncoder.encode(String.valueOf(start))+"&"+
				"bloodGroup="+URLEncoder.encode(donor.getBloodGroup())+"&"+
				"latitude="+URLEncoder.encode(donor.getLatitude()+"")+"&"+
				"longitude="+URLEncoder.encode(donor.getLongitude()+"");
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", donor.getEmail()));
		params.add(new BasicNameValuePair("start", String.valueOf(start)));
		params.add(new BasicNameValuePair("bloodGroup", donor.getBloodGroup()));
		params.add(new BasicNameValuePair("latitude", donor.getLatitude()+""));
		params.add(new BasicNameValuePair("longitude", donor.getLongitude()+""));
		getResults.execute(url,params);

		try {
			getResults.get();
			if(new ParseHTTPResponse(getResults.jsonResult,getResults.responseOK).ParseJSONResult()){
				setListView(getResults.jsonResult);
			}
			else{
				Toast.makeText(getActivity(), "No Connection", Toast.LENGTH_SHORT).show();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*public boolean sendPatientRequest(int patientCode){
		String url="http://www.androminions.com/OneDropServerFiles/Donor/donor_send_request.php";
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("patientCode", patientCode+""));
		params.add(new BasicNameValuePair("donorEmail", donor.getEmail()));

		URLExecution2 getResults=new URLExecution2();
		getResults.execute(url,params);

		try {
			getResults.get();
			return new _OneDrop_ParseJSONResult(getResults.jsonResult).getSuccess();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}*/
	public void setListView(String jsonResult){
		System.out.println("JSONResult: \n"+jsonResult);
		try{
			JSONObject json =new JSONObject(jsonResult);
			JSONArray donorInfo = json.getJSONArray("patients");
			for(int i=0;i<donorInfo.length();i++){
				start++;
				JSONObject obj=donorInfo.getJSONObject(i);					

				_OneDrop_Patient_POJO patient=new _OneDrop_Patient_POJO();

				patient.setPlace(obj.getString("place"));
				patient.setPatientCode(Integer.parseInt(obj.getString("patientCode")));
				patient.setPatientName(obj.getString("patientName"));
				patient.setPatientBloodGroup(obj.getString("patientBloodGroup"));
				patient.setPatientContact(obj.getString("patientContact"));
				patient.setPatientAge(Integer.parseInt(obj.getString("patientAge")));
				patient.setPatientGender(obj.getString("patientGender"));

				patients.add(patient);
				adapter.add(patient);

			}
			adapter.notifyDataSetChanged();
			loadingMore=false;

		}catch(JSONException e){
			e.printStackTrace();
		}


	}

	private class MyCustomAdapter extends ArrayAdapter<_OneDrop_Patient_POJO> {

		private ArrayList<_OneDrop_Patient_POJO> patients;
		View view;

		public MyCustomAdapter(Context context, int textViewResourceId,
				ArrayList<_OneDrop_Patient_POJO> patients) {
			super(context, textViewResourceId, patients);
			this.patients = new ArrayList<_OneDrop_Patient_POJO>();
			this.patients.addAll(patients);
		}

		private  class ViewHolder{
			TextView name;
			TextView age;
			TextView gender;
			ImageView bloodGroup;
			TextView location;

			Button accept;



		}

		public void add(_OneDrop_Patient_POJO patient){
			Log.v("AddView", patient.getPatientName());
			this.patients.add(patient);
		}

		@Override

		public View getView(int position, View convertView, ViewGroup parent) {

			view=convertView;
			final ViewHolder holder ;
			Log.v("ConvertView", String.valueOf(position));
			if (this.view == null) {

				LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				this.view = vi.inflate(R.layout.row_patient_view, null);

				holder = new ViewHolder();
				holder.name = (TextView)this.view.findViewById(R.id.tvPatientName);
				holder.bloodGroup= (ImageView) this.view.findViewById(R.id.tvPatientBloodGroup);
				holder.age = (TextView) this.view.findViewById(R.id.tvPatientAge);
				holder.gender=(TextView) this.view.findViewById(R.id.tvPatientGender);
				holder.location=(TextView) this.view.findViewById(R.id.tvPatientLocation);
				holder.accept=(Button)this.view.findViewById(R.id.bStatus);
				this.view
				.setTag(holder);

			} else {
				holder = (ViewHolder) this.view.getTag();
			}



			final int pos=position;

			final _OneDrop_Patient_POJO patient = this.patients.get(position);
			holder.name.setText(patient.getPatientName());
			holder.age.setText(patient.getPatientAge()+"");
			holder.gender.setText(patient.getPatientGender());
			holder.location.setText(patient.getPlace());
			holder.bloodGroup.setImageResource(returnImageSource(patient.getPatientBloodGroup()));
			holder.accept.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Builder builder = new AlertDialog.Builder(getActivity());
					AlertDialog dialog = builder.create();	
					dialog.setTitle("Accept Donation Request");
					dialog.setMessage("Are you sure you want to accept request of "+patient.getPatientName()+". Accepting the request allow patient to view your contact number");
					dialog.setButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							/*if(sendPatientRequest(patient.getPatientCode())){								
								Toast.makeText(getActivity()," Request Sent", Toast.LENGTH_SHORT).show();
								patients.remove(pos);
								adapter.remove(patient);
								//notifyDataSetChanged();
							}
							else{
								dialog.dismiss();
								Toast.makeText(getActivity()," Error accepting patient request. Please Try Later", Toast.LENGTH_SHORT).show();
							}*/
						}

					});
					dialog.setCancelable(true);
					dialog.setIcon(R.drawable.icon);
					dialog.show();



				}
			});

			return view;


		}
		public int returnImageSource(String bloodGroup){
			int src=R.drawable.ic_launcher;
			switch(bloodGroup){
			case "A+":
				src=R.drawable.apositive;
				break;				
			case "A-":
				src=R.drawable.anegative;
				break;
			case "B+":
				src=R.drawable.bpositive;
				break;
			case "B-":
				src=R.drawable.bnegative;
				break;
			case "AB+":
				src=R.drawable.abpositive;
				break;
			case "AB-":
				src=R.drawable.abnegative;
				break;
			case "O+":
				src=R.drawable.opositive;
				break;
			case "O-":
				src=R.drawable.onegative;
				break;
			default:
				break;
			}
			return src;
		}

	}




}
