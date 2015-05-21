package com.androminions.onedrop.donor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.androminions.onedrop.internet.HTTPRequest;
import com.androminions.onedrop.internet.ParseHTTPResponse;
import com.androminions.onedropdemo.R;
import com.example.onedrop._OneDrop_CoverPage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class _OneDrop_Donor_ForgotPassword extends Activity implements OnClickListener{
	
	TextView email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onedrop_donor_forgotpass);
		
		
		email=(TextView)findViewById(R.id.etEmailForgot);
		Button sendMail=(Button)findViewById(R.id.bSubmitEmail);
		sendMail.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(mailPassword(email.getText().toString())){
			Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this, "Email can not be sent", Toast.LENGTH_SHORT).show();
		}
		startActivity(new Intent(this,_OneDrop_Donor_SignIn.class));
	}
	public boolean mailPassword(String email){
		String url="http://www.androminions.com/OneDropServerFiles/Donor/donor_forget_password.php?email="+URLEncoder.encode(email);
		List<NameValuePair> myParams=new ArrayList<NameValuePair>();
		myParams.add(new BasicNameValuePair("email", email));
		
		HTTPRequest task=new HTTPRequest();
		task.execute(url,myParams);

		try {
			task.get();
			if(new ParseHTTPResponse(task.jsonResult,task.responseOK).ParseJSONResult()){				
				return true;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
		return false;
		
	}
	

}


