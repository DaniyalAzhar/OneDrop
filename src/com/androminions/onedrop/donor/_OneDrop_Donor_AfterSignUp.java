package com.androminions.onedrop.donor;

import com.androminions.onedropdemo.R;
import com.example.onedrop._OneDrop_CoverPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class _OneDrop_Donor_AfterSignUp extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aftersignup);
		Button back=(Button)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(_OneDrop_Donor_AfterSignUp.this,_OneDrop_CoverPage.class));
			}
		});
	}
	

	
}
