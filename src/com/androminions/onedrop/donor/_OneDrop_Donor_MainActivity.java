package com.androminions.onedrop.donor;

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
import org.apache.http.entity.SerializableEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androminions.onedrop.internet.HTTPRequest;
import com.androminions.onedropdemo.R;
import com.example.onedrop._OneDrop_CoverPage;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class _OneDrop_Donor_MainActivity extends Activity {
	
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;

	String username;

	LinearLayout mDrawerView;

	ImageView bloodGroup;
	TextView name;
	TextView location;
	Switch availability;


	_OneDrop_Donor_POJO donor;


	// used to store app title
	private CharSequence mTitle;
	// slide menu items
	private String[] navMenuTitles=new String[4];
	private TypedArray navMenuIcons;

	private ArrayList<_OneDrop_DonorMenu_NavDrawerItem> navDrawerItems;
	private _OneDrop_DonorMenu_NavDrawerListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.donor_menudrawer);
		mDrawerView=(LinearLayout)findViewById(R.id.linearLayout);
		bloodGroup=(ImageView)findViewById(R.id.myBloodGroup);
		name=(TextView)findViewById(R.id.donorMenuName);
		location=(TextView)findViewById(R.id.donorMenuLocation);
		availability=(Switch)findViewById(R.id.switchForActionBar);
		donor=getDonorInfo();			

		availability.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				int availability;
				if(isChecked){
					donor.availability=1;
				}
				else{
					donor.availability=0;
				}

			}
		});

		availability.setChecked(donor.getAvailability()==1?true:false);
		name.setText(donor.getFullName());
		bloodGroup.setImageResource(returnImageSource(donor.getBloodGroup()));
		location.setText(donor.getArea().toString());

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);


		navDrawerItems = new ArrayList<_OneDrop_DonorMenu_NavDrawerItem>();

		navDrawerItems.add(new _OneDrop_DonorMenu_NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new _OneDrop_DonorMenu_NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new _OneDrop_DonorMenu_NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new _OneDrop_DonorMenu_NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		adapter = new _OneDrop_DonorMenu_NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);


		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.closeDrawer(mDrawerView);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(1);
		}



	}
	public _OneDrop_Donor_POJO getDonorInfo(){
		_OneDrop_Donor_POJO donor=new _OneDrop_Donor_POJO();
		String jsonResult=getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).getString("DonorInfo", "");
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


	
	private class SlideMenuClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerView);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}


	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		Bundle bundle=new Bundle();
		switch (position) {
		case 0:
			fragment = new _OneDrop_Donor_Fragment_PI();
			break;
		case 1:
			fragment = new _OneDrop_Donor_Fragment_NewCurrNotifications();
			break;
		case 2:
			fragment = new _OneDrop_Donor_Fragment_Events();
			break;
		case 3:
			sharedpreferences=getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
			Editor editor=sharedpreferences.edit();
			 editor.putString("Email", "");
		      editor.putString("DonorInfo", "");
		      editor.commit();
			startActivity(new Intent(_OneDrop_Donor_MainActivity.this,_OneDrop_CoverPage.class));			
			break;

		default:
			break;
		}


		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerView);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
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
