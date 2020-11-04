package com.login_signup_screendesign_demo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.login_signup_screendesign_demo.utils.Utils;

public class MainActivity extends AppCompatActivity {
	private static FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkPermissions(MainActivity.this, this);
		fragmentManager = getSupportFragmentManager();
		//FCM token
		FirebaseApp.initializeApp(this);
		try{
			String token = FirebaseInstanceId.getInstance().getToken();
			Log.d("MsgService", "device token : " + token);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		// If savedinstnacestate is null then replace login fragment
		if (savedInstanceState == null) {
			fragmentManager
					.beginTransaction()
					.replace(R.id.frameContainer, new Login_Fragment(),
							Utils.Login_Fragment).commit();
		}

		// On close icon click finish activity
		findViewById(R.id.close_activity).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						finish();

					}
				});

	}

	// Replace Login Fragment with animation
	protected void replaceLoginFragment() {
		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
				.replace(R.id.frameContainer, new Login_Fragment(),
						Utils.Login_Fragment).commit();
	}

	@Override
	public void onBackPressed() {

		// Find the tag of signup and forgot password fragment
		Fragment SignUp_Fragment = fragmentManager
				.findFragmentByTag(Utils.SignUp_Fragment);
		Fragment ForgotPassword_Fragment = fragmentManager
				.findFragmentByTag(Utils.ForgotPassword_Fragment);

		// Check if both are null or not
		// If both are not null then replace login fragment else do backpressed
		// task

		if (SignUp_Fragment != null)
			replaceLoginFragment();
		else if (ForgotPassword_Fragment != null)
			replaceLoginFragment();
		else
			super.onBackPressed();
	}

	public void onPasswordReset(String message) {

		showSnackBarMessage(message);
	}

	private void showSnackBarMessage(String message) {



	}



	public static void checkPermissions(Activity activity, Context context){
		int PERMISSION_ALL = 1;
		String[] PERMISSIONS = {
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.BLUETOOTH,
				Manifest.permission.BLUETOOTH_ADMIN,
				Manifest.permission.BLUETOOTH_PRIVILEGED,
		};

		if(!hasPermissions(context, PERMISSIONS)){
			ActivityCompat.requestPermissions( activity, PERMISSIONS, PERMISSION_ALL);
		}
	}

	public static boolean hasPermissions(Context context, String... permissions) {
		if (context != null && permissions != null) {
			for (String permission : permissions) {
				if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
					return false;
				}
			}
		}
		return true;
	}


}
