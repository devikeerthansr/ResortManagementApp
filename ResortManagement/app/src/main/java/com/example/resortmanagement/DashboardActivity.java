package com.example.resortmanagement;


import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.Fragment;
import androidx.lifecycle.ReportFragment;

import com.example.resortmanagement.constant.Constants;
import com.example.resortmanagement.fragments.AmenityFragment;
import com.example.resortmanagement.fragments.BookingFragment;
import com.example.resortmanagement.fragments.DashboardFragment;


public class DashboardActivity extends Activity {

	// Within which the entire activity is enclosed
	DrawerLayout mDrawerLayout;

	// ListView represents Navigation Drawer
	ListView mDrawerList;

	TextView mTxtTitle;
	boolean isOpen = false;

	// ActionBarDrawerToggle indicates the presence of Navigation Drawer in the
	// action bar
	ActionBarDrawerToggle mDrawerToggle;

	// Title of the action bar
	String mTitle = "";

	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	String[] menusArr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_dashboad);

		mTxtTitle = (TextView) findViewById(R.id.txt_title);

		// Getting reference to the DrawerLayout
		mDrawerLayout =  findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		menusArr = getResources().getStringArray(R.array.slidemenus);

		// Getting reference to the FragmentManager
		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();

		// Getting reference to the ActionBarDrawerToggle
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.drawer_open,
				R.string.drawer_close) {

			/** Called when drawer is closed */
			public void onDrawerClosed(View view) {
				isOpen = false;
			}

			/** Called when a drawer is opened */
			public void onDrawerOpened(View drawerView) {
				isOpen = true;
			}

		};

		// Setting DrawerToggle on DrawerLayout
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// Creating an ArrayAdapter to add items to the listview mDrawerList
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getBaseContext(), R.layout.drawer_list_item, getResources()
						.getStringArray(R.array.slidemenus));

		// Setting the adapter on mDrawerList
		mDrawerList.setAdapter(adapter);
		mDrawerList.setItemChecked(0, true);

		// setting default fragment
		mTitle = menusArr[0];
		Fragment rFragment = new DashboardFragment();
		fragmentTransaction.replace(R.id.content_frame, rFragment,Constants.DASHBOARD);
		fragmentTransaction.addToBackStack(Constants.DASHBOARD);
		setTile(mTitle);
		fragmentTransaction.commit();

		// Setting item click listener for the listview mDrawerList
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Getting reference to the FragmentManager

				if (position == 0) {
					if (!mTitle.equals(menusArr[0])) {
						mTitle = menusArr[position];
						setTile(mTitle);
					}
				} else if (position == 1) {
					if (!mTitle.equals(menusArr[1])) {
						mTitle = menusArr[position];
						setTile(mTitle);
					}
					Fragment rFragment = new BookingFragment();
					goToNewFragment(rFragment, Constants.BOOKING);
				} else if (position == 2) {
					if (!mTitle.equals(menusArr[2])) {
						mTitle = menusArr[position];
						setTile(mTitle);
					}
					Fragment rFragment = new AmenityFragment();
					goToNewFragment(rFragment,Constants.AMENITY);
				} else if (position == 3) {

				} else {
					setLogoutPopup().show();
				}

				// Closing the drawer
				mDrawerLayout.closeDrawer(mDrawerList);

			}
		});

		((Button) findViewById(R.id.btn_logomenu))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						handleDrawer();
					}
				});
	}

	// logout fn
	protected Dialog setLogoutPopup() {

		final Dialog dialog = new Dialog(this,
				android.R.style.Theme_Translucent_NoTitleBar);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.popup_logout_confirm);
		dialog.setCancelable(true);
		((View) dialog.findViewById(R.id.dummyview))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}

				});
		((View) dialog.findViewById(R.id.yesbtn))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// erase all
						finish();
						dialog.dismiss();
					}
				});
		((View) dialog.findViewById(R.id.nobtn))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (mTxtTitle.getText().equals(menusArr[0]))
							mDrawerList.setItemChecked(0, true);
						else
							mDrawerList.setItemChecked(1, true);
						dialog.dismiss();
					}
				});

		return dialog;

	}

	public void goToNewFragment(Fragment rFragment,String tag){
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, rFragment,tag);
		fragmentTransaction.addToBackStack(tag);
		setTile(tag);
		fragmentTransaction.commit();
	}
	// exit fn
		protected Dialog setExitPopup() {

			final Dialog dialog = new Dialog(this,
					android.R.style.Theme_Translucent_NoTitleBar);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			dialog.setContentView(R.layout.popup_logout_confirm);
			dialog.setCancelable(true);
			
			((TextView) dialog.findViewById(R.id.txt_message)).setText("Are you sure exit?");
			((View) dialog.findViewById(R.id.dummyview))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
						}

					});
			((View) dialog.findViewById(R.id.yesbtn))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							finish();
							dialog.dismiss();
						}
					});
			((View) dialog.findViewById(R.id.nobtn))
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							if (mTxtTitle.getText().equals(menusArr[0]))
								mDrawerList.setItemChecked(0, true);
							else
								mDrawerList.setItemChecked(1, true);
							dialog.dismiss();
						}
					});

			return dialog;

		}

	// setting title
	protected void setTile(String mTitle2) {
		mTxtTitle.setText(mTitle2);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	private void handleDrawer() {
		if (isOpen) {
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			mDrawerLayout.openDrawer(mDrawerList);
		}
	}

	@Override
	public void onBackPressed() {

		if (isOpen) {
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			if(fragmentManager.getBackStackEntryCount() > 0) {
				String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
				Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
				if (fragment instanceof DashboardFragment) {
					setExitPopup().show();
				} else {
					fragmentManager.popBackStack();
				}
			}
		}
	}




}
