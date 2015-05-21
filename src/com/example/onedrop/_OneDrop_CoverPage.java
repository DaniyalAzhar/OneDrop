package com.example.onedrop;

import com.androminions.onedrop.donor._OneDrop_Donor_SignIn;
import com.androminions.onedrop.patient._OneDrop_Patient_SubmitRequest;
import com.androminions.onedropdemo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class _OneDrop_CoverPage extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onedrop_coverpage);
		Button donor=(Button)findViewById(R.id.bDonor);
		Button patient=(Button)findViewById(R.id.bPatient);
		donor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(_OneDrop_CoverPage.this,_OneDrop_Donor_SignIn.class));

			}
		});
		patient.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(_OneDrop_CoverPage.this,_OneDrop_Patient_SubmitRequest.class));

			}
		});
	}


}
