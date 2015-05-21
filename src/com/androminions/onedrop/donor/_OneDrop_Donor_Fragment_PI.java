package com.androminions.onedrop.donor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androminions.onedropdemo.R;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class _OneDrop_Donor_Fragment_PI extends Fragment{

	TextView username;
	TextView name;
	TextView password;
	TextView bloodGroup;
	TextView area;
	TextView contact;
	
	Button edit;
	
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_personal, container, false);
		sharedpreferences=getActivity().getSharedPreferences(MyPREFERENCES, getActivity().MODE_PRIVATE);
		
		
		username=(TextView)rootView.findViewById(R.id.donor_info_username);
		name=(TextView)rootView.findViewById(R.id.donor_info_name);
		password=(TextView)rootView.findViewById(R.id.donor_info_password);
		bloodGroup=(TextView)rootView.findViewById(R.id.donor_info_bloodGroup);
		area=(TextView)rootView.findViewById(R.id.donor_info_area);
		contact=(TextView)rootView.findViewById(R.id.donor_info_contact);
		edit=(Button)rootView.findViewById(R.id.btnEditInfo);
		
		edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		_OneDrop_Donor_POJO donor=getDonorInfo();
		
		
		username.setText(donor.getEmail());
		name.setText(donor.getFullName());
		password.setText(donor.getPassword());
		bloodGroup.setText(donor.getBloodGroup());
		area.setText(donor.getArea());
		contact.setText(donor.getContact());
		return rootView;
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

}
