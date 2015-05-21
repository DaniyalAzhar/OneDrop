package com.androminions.onedrop.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
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

public class HTTPPlace extends AsyncTask<Object,Void,String>{
	public String jsonResult="";
	public boolean responseOK=false;
	
	private Context ctx=null;
	private String url;
	private List<NameValuePair> myParams;
	
	public HTTPPlace(Context ctx){
		this.ctx=ctx;
	}
	public HTTPPlace(){
		
	}
	private ProgressDialog dialog;
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if(ctx!=null){
			 dialog =new ProgressDialog(ctx);
			 dialog.setMessage("Getting Location.. Please wait");
				dialog.show();
		}
	}
	
	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stub

		String place=(String)params[0];
		String uri = "http://maps.google.com/maps/api/geocode/json?address=" +URLEncoder.encode(place) + "&sensor=false";
		HttpGet httpGet = new HttpGet(uri);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		jsonResult=stringBuilder.toString();
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

}
