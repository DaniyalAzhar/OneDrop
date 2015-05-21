package com.androminions.onedrop.donor;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androminions.onedrop.internet.HTTPRequest;
import com.androminions.onedrop.internet.ParseHTTPResponse;
import com.androminions.onedropdemo.R;
import com.example.onedrop.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class _OneDrop_Donor_SignIn extends Activity{
	
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;

	_OneDrop_Donor_POJO donor;
	TextView email;
	TextView password;
	String jsonResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onedrop_donor_signin);
		if(getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).getString("Email", "").compareTo("")!=0){
			startActivity(new Intent(_OneDrop_Donor_SignIn.this,_OneDrop_Donor_MainActivity.class));
		}
		System.out.println("Here");
		Button signIn=(Button)findViewById(R.id.bSignIn);
		Button signUp=(Button)findViewById(R.id.bSignUp);
		email=(TextView)findViewById(R.id.etName);
		password=(TextView)findViewById(R.id.etPassword);
		signIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String myEmail=email.getText().toString();
				String myPassword=password.getText().toString();
				if(myEmail.replaceAll("\\s+","").compareTo("")!=0 && myPassword.replaceAll("\\s+","").compareTo("")!=0){
					if(checkDonor()){
						Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
					      editor.putString("Email", email.getText().toString());
					      editor.putString("DonorInfo",jsonResult);
					      editor.commit(); 
						startActivity(new Intent(_OneDrop_Donor_SignIn.this,_OneDrop_Donor_MainActivity.class));
					}
				}
				else{
					Toast.makeText(getApplicationContext(), "None of the fields can be left empty", Toast.LENGTH_SHORT).show();
				}
				//startActivity(new Intent(_OneDrop_Donor_SignIn.this,_OneDrop_Donor_MainActivity.class));
			}
		});
		signUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(_OneDrop_Donor_SignIn.this,_OneDrop_Donor_SignUp.class));
			}
		});
		TextView forgotPass=(TextView)findViewById(R.id.tvForgotPass);
		forgotPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(_OneDrop_Donor_SignIn.this,_OneDrop_Donor_ForgotPassword.class));
			}
		});
	}
	public boolean checkDonor(){
		String id=email.getText().toString();
		String pass=password.getText().toString();
		
		HTTPRequest req=new HTTPRequest(this);
		String url="http://www.androminions.com/OneDropServerFiles/Donor/donor_get_info.php?email="+URLEncoder.encode(id)+"&password="+URLEncoder.encode(pass);
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("email", id));
		params.add(new BasicNameValuePair("password", pass));
		req.execute(url,params);
		try{
			req.get();
			if(new ParseHTTPResponse(req.jsonResult,req.responseOK).ParseJSONResult()){
				jsonResult=req.jsonResult;
				donor=getParsedInfo(jsonResult);
				
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
	public _OneDrop_Donor_POJO getParsedInfo(String jsonResult){

		_OneDrop_Donor_POJO donor=new _OneDrop_Donor_POJO();
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
