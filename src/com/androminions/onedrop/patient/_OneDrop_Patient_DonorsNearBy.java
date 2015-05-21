/**
 * 
 */
package com.androminions.onedrop.patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import com.androminions.onedrop.donor._OneDrop_Donor_POJO;
import com.androminions.onedropdemo.R;
import com.example.onedrop._OneDrop_CoverPage;

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
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

/**
 * @author Saad
 *
 */
public class _OneDrop_Patient_DonorsNearBy extends Fragment{

	_OneDrop_Patient_POJO patient;
	ArrayList<_OneDrop_Donor_POJO> donorsNearBy;
	ListView donorsList;

	TextView mainPagePatientCode;

	Button discard;
	MyCustomAdapter adapter=null;
	int start=0;
	int limit=10;
	boolean loadingMore=false;
	boolean userScrolled=false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.patient_mainpage, container, false);

		patient=new _OneDrop_Patient_POJO();		
		patient.setPlace("H-12, NUST, Islamabad");
		patient.setPatientCode(243);
		patient.setPatientName("Ali Khan");
		patient.setPatientBloodGroup("B+");
		patient.setPatientContact("090078601");
		patient.setPatientAge(38);
		patient.setPatientGender("Male");
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		patient=new _OneDrop_Patient_POJO();		
		patient.setPlace("H-12, NUST, Islamabad");
		patient.setPatientCode(243);
		patient.setPatientName("Ali Khan");
		patient.setPatientBloodGroup("B+");
		patient.setPatientContact("090078601");
		patient.setPatientAge(38);
		patient.setPatientGender("Male");
		System.out.println("Patient COde is "+patient.getPatientCode());
		System.out.println("Start is "+start);
		donorsList=(ListView)getActivity().findViewById(R.id.listAllDonors);
		mainPagePatientCode=(TextView)getActivity().findViewById(R.id.tvMainPagePatientCode);
		mainPagePatientCode.setText(patient.getPatientCode()+"");
		donorsNearBy=new ArrayList<_OneDrop_Donor_POJO>();

		discard=(Button)getActivity().findViewById(R.id.button1);
		discard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity().getApplicationContext(),_OneDrop_CoverPage.class));
			}
		});
		adapter=new MyCustomAdapter(getActivity(),R.layout.row_patient_view,donorsNearBy);
		donorsList.setAdapter(adapter);
		donorsList.setTextFilterEnabled(true);	
		donorsList.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(scrollState==SCROLL_STATE_IDLE){
					if(donorsList.getLastVisiblePosition()>=(donorsList.getCount()-1)){
						displayResults();
					}

				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
			}

		});
		displayResults();
	}

	public void displayResults(){		
		_OneDrop_Donor_POJO donor=new _OneDrop_Donor_POJO();

		donor.setFullName("Arshad Ali");
		donor.setBloodGroup("O+");
		donor.setArea("H-11, Islamabad");
		donor.setEmail("arshadali");
		donor.setContact("03433073727");


		donorsNearBy.add(donor);
		adapter.add(donor);

		_OneDrop_Donor_POJO donor2=new _OneDrop_Donor_POJO();

		donor2.setFullName("Faizan Anwar");
		donor2.setBloodGroup("B+");
		donor2.setArea("G-11, Islamabad");
		donor2.setEmail("faizananwar");
		donor2.setContact("03433073727");


		donorsNearBy.add(donor2);
		adapter.add(donor2);

		_OneDrop_Donor_POJO donor3=new _OneDrop_Donor_POJO();

		donor3.setFullName("Ayesha Ali");
		donor3.setBloodGroup("A+");
		donor3.setArea("E-11, Islamabad");
		donor3.setEmail("ayeshaali");
		donor3.setContact("03433073727");


		donorsNearBy.add(donor3);
		adapter.add(donor3);

		_OneDrop_Donor_POJO donor4=new _OneDrop_Donor_POJO();

		donor4.setFullName("Murad Ali");
		donor4.setBloodGroup("A-");
		donor4.setArea("F-11, Islamabad");
		donor4.setEmail("muradali");
		donor4.setContact("03433073727");


		donorsNearBy.add(donor4);
		adapter.add(donor4);
		adapter.notifyDataSetChanged();


	}








private class MyCustomAdapter extends ArrayAdapter<_OneDrop_Donor_POJO> {

	private ArrayList<_OneDrop_Donor_POJO> donors;
	View view;

	public MyCustomAdapter(Context context, int textViewResourceId,
			ArrayList<_OneDrop_Donor_POJO> donors) {
		super(context, textViewResourceId, donors);
		this.donors = new ArrayList<_OneDrop_Donor_POJO>();
		this.donors.addAll(donors);
	}

	private  class ViewHolder{
		TextView name;
		TextView age;
		TextView gender;
		ImageView bloodGroup;
		TextView location;
	}

	public void add(_OneDrop_Donor_POJO donor){
		Log.v("AddView", donor.getFullName());
		this.donors.add(donor);
	}

	@Override

	public View getView(int position, View convertView, ViewGroup parent) {

		view=convertView;
		final ViewHolder holder ;
		Log.v("ConvertView", String.valueOf(position));
		if (this.view == null) {

			LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			this.view = vi.inflate(R.layout.row_donor_view, null);

			holder = new ViewHolder();
			holder.name = (TextView)this.view.findViewById(R.id.tvDonorName);
			holder.bloodGroup= (ImageView) this.view.findViewById(R.id.tvDonorBloodGroup);
			holder.age = (TextView) this.view.findViewById(R.id.tvDonorAge);
			holder.gender=(TextView) this.view.findViewById(R.id.tvDonorGender);
			holder.location=(TextView) this.view.findViewById(R.id.tvDonorLocation);
			this.view
			.setTag(holder);

		} else {
			holder = (ViewHolder) this.view.getTag();
		}





		final _OneDrop_Donor_POJO donor = this.donors.get(position);
		holder.name.setText(donor.getFullName());
		holder.location.setText(donor.getArea());
		holder.bloodGroup.setImageResource(returnImageSource(donor.getBloodGroup()));

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
