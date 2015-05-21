package com.androminions.onedrop.patient;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.androminions.onedrop.internet.HTTPRequest;
import com.androminions.onedrop.internet.ParseHTTPResponse;
import com.androminions.onedropdemo.R;
import com.example.onedrop.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class _OneDrop_Patient_SubmitRequest extends Activity{


	TextView patientCode;
	String jsonResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onedrop_patient_submitreq);
		Button submit=(Button)findViewById(R.id.requiredInfo_FindDonor);
		Button chkCode=(Button)findViewById(R.id.requiredInfo_bUpdates);
		patientCode=(Button)findViewById(R.id.requiredInfo_PatientCode);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				startActivity(new Intent(_OneDrop_Patient_SubmitRequest.this,_OneDrop_Patient_MainActivity.class));
			}
		});
		chkCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkPatientCode(patientCode.getText().toString())){
					startActivity(new Intent(_OneDrop_Patient_SubmitRequest.this,_OneDrop_Patient_MainActivity.class));
				}
			}
		});
	}

	public boolean checkPatientCode(String code){
		String url="http://www.androminions.com/OneDropServerFiles/Patient/patient_get_info.php?patientCode="+URLEncoder.encode(code);
		HTTPRequest req=new HTTPRequest();
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("patientCode", code));
		req.execute(url,params);
		try{
			req.get();
			if(new ParseHTTPResponse(req.jsonResult,req.responseOK).ParseJSONResult()){
				return true;
			}
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();					}
		return false;
	}

}
