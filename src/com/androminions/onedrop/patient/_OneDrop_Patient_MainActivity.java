package com.androminions.onedrop.patient;

import java.util.concurrent.ExecutionException;

import com.androminions.onedropdemo.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class _OneDrop_Patient_MainActivity extends Activity{

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	LinearLayout mDrawerView;	
	ImageView bloodGroup;	
	TextView patientName;
	TextView patientCode;
	TextView patientLocation;
	TextView patientGender;
	TextView patientAge;
	TextView patientContact;
	TextView patientBloodGroup;

	_OneDrop_Patient_POJO patient;

	


	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	Menu myMenu;
	String buttonPressed="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		final Bundle savedinstancestate=savedInstanceState;	
		displayPage(savedInstanceState);

	}

	public void displayPage(Bundle savedInstanceState){
		_OneDrop_Patient_MainActivity.this.setContentView(R.layout.patient_menudrawer);

		patient=new _OneDrop_Patient_POJO();
		
		patient.setPlace("H-12, NUST, Islamabad");
		patient.setPatientCode(243);
		patient.setPatientName("Ali Khan");
		patient.setPatientBloodGroup("B+");
		patient.setPatientContact("090078601");
		patient.setPatientAge(38);
		patient.setPatientGender("Male");

		mDrawerView=(LinearLayout)findViewById(R.id.PatientLinearLayout);
		bloodGroup=(ImageView)findViewById(R.id.patient_menu_bloodGroup);
		patientName=(TextView)findViewById(R.id.patient_menu_patientName);
		patientCode=(TextView)findViewById(R.id.patient_menu_patientCode);
		patientLocation=(TextView)findViewById(R.id.patient_menu_patientLocation);
		patientGender=(TextView)findViewById(R.id.patient_menu_patientGender);
		patientAge=(TextView)findViewById(R.id.patient_menu_patientAge);
		patientContact=(TextView)findViewById(R.id.patient_menu_patientContact);
		patientBloodGroup=(TextView)findViewById(R.id.patient_menu_patientBloodGroup);



		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.patient_drawer_layout);

		_OneDrop_Patient_MainActivity.this.getActionBar().setDisplayHomeAsUpEnabled(true);
		_OneDrop_Patient_MainActivity.this.getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer,R.string.app_name,R.string.app_name) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();

			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.closeDrawer(mDrawerView);

		patientName.setText(patient.getPatientName());
		patientCode.setText(patient.getPatientCode()+"");
		patientLocation.setText(patient.getPlace());
		patientGender.setText(patient.getPatientGender());
		patientAge.setText(patient.getPatientAge()+"");
		patientContact.setText(patient.getPatientContact());
		patientBloodGroup.setText(patient.getPatientBloodGroup());

		bloodGroup.setImageResource(returnImageSource(patient.getPatientBloodGroup()));





		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main2, menu);
		myMenu=menu;
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
		case R.id.action_response:
			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
			MenuItem menuItem=myMenu.findItem(R.id.action_home);
			menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			displayView(1);
			return true;
		case R.id.action_home:
			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
			MenuItem menuItem2=myMenu.findItem(R.id.action_response);
			menuItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			displayView(0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerView);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private void displayView(int pageNo) {
		if(pageNo==0){
			Fragment fragment = new _OneDrop_Patient_DonorsNearBy();

			if (fragment != null) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
				setTitle("Home");

				// update selected item and title, then close the drawer
				mDrawerLayout.closeDrawer(mDrawerView);
			} else {
				// error in creating fragment
				Log.e("MainActivity", "Error in creating fragment");
			}

		}
		else{
			Fragment fragment = new _OneDrop_Patient_Responses();

			if (fragment != null) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
				setTitle("Responses");

				// update selected item and title, then close the drawer
				mDrawerLayout.closeDrawer(mDrawerView);
			} else {
				// error in creating fragment
				Log.e("MainActivity", "Error in creating fragment");
			}
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

}
