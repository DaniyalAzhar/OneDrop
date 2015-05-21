package com.androminions.onedrop.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class HTTPRequest extends AsyncTask<Object,Void,String>{
	public String jsonResult="";
	public boolean responseOK=false;
	
	private Context ctx=null;
	private String url;
	private List<NameValuePair> myParams;
	
	public HTTPRequest(Context ctx){
		this.ctx=ctx;
	}
	public HTTPRequest(){
		
	}
	private ProgressDialog dialog;
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if(ctx!=null){
			 dialog =new ProgressDialog(ctx);
			 dialog.setMessage("Getting Results.. Please wait");
				dialog.show();
		}
	}
	
	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stub
		
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 2000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		
		url=(String)params[0];
		myParams=(List<NameValuePair>)params[1];
		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		HttpPost httppost = new HttpPost(url);
		try {
			
			httppost.setEntity(new UrlEncodedFormEntity(myParams));
			HttpResponse response = httpclient.execute(httppost);
			StatusLine stLine=response.getStatusLine();
			if(stLine.getStatusCode()==HttpStatus.SC_OK){
				responseOK=true;
				
			}
			else{
				if(ctx!=null){
				Toast.makeText(ctx, "Internet Connectivity Problem", Toast.LENGTH_SHORT).show();
				}
			}
			jsonResult = inputStreamToString(
					response.getEntity().getContent()).toString();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResult;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stubµ
		this.jsonResult=result;
		if(ctx!=null){
			dialog.dismiss();
		}
	}
	private StringBuilder inputStreamToString(InputStream is) {
		String rLine = "";
		StringBuilder answer = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
			}
		}
		catch (IOException e) {
			e.printStackTrace();

		}
		return answer;
	}

}
