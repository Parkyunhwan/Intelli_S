package com.login_signup_screendesign_demo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.login_signup_screendesign_demo.models.Response;
import com.login_signup_screendesign_demo.utils.Constants;
import com.login_signup_screendesign_demo.utils.Utils;

import java.io.IOException;

import retrofit2.HttpException;
import rx.subscriptions.CompositeSubscription;

//import com.login_signup_screendesign_demo.api.LoginServiceInterface;
//import com.login_signup_screendesign_demo.api.apiURL;

public class Login_Fragment extends Fragment  {
	private static View view;

	private static EditText emailid, password;
	private static Button loginButton;
	private static TextView forgotPassword, signUp;
	private static CheckBox show_hide_password;
	private static LinearLayout loginLayout;
	private static Animation shakeAnimation;
	private static FragmentManager fragmentManager;
	private ProgressBar mProgressBar;
	private CompositeSubscription mSubscriptions;
	private SharedPreferences mSharedPreferences;

	public Login_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_layout, container, false);
		mSubscriptions = new CompositeSubscription();
		initViews(view);
		initSharedPreferences();
		return view;
	}

	private void initSharedPreferences(){
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
	}

	// Initiate Views
	private void initViews(View v) {
		fragmentManager = getActivity().getSupportFragmentManager();

		emailid = (EditText) v.findViewById(R.id.login_emailid);
		password = (EditText) v.findViewById(R.id.login_password);
		loginButton = (Button) v.findViewById(R.id.loginBtn);
		forgotPassword = (TextView) v.findViewById(R.id.forgot_password);
		signUp = (TextView) v.findViewById(R.id.createAccount);
		show_hide_password = (CheckBox)v
				.findViewById(R.id.show_hide_password);
		loginLayout = (LinearLayout) v.findViewById(R.id.login_layout);


		// Load ShakeAnimation
		shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);

		// Setting text selector over textviews
		@SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			forgotPassword.setTextColor(csl);
			show_hide_password.setTextColor(csl);
			signUp.setTextColor(csl);
		} catch (Exception e) {
		}
		// Set Listeners
		loginButton.setOnClickListener(view->login());
		forgotPassword.setOnClickListener(view->forgotpassword());
		signUp.setOnClickListener(view->signup());
		show_hide_password
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton button,
												 boolean isChecked) {

						// If it is checkec then show password else hide
						// password
						if (isChecked) {

							show_hide_password.setText(R.string.hide_pwd);// change
							// checkbox
							// text

							password.setInputType(InputType.TYPE_CLASS_TEXT);
							password.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());// show password
						} else {
							show_hide_password.setText(R.string.show_pwd);// change
							// checkbox
							// text

							password.setInputType(InputType.TYPE_CLASS_TEXT
									| InputType.TYPE_TEXT_VARIATION_PASSWORD);
							password.setTransformationMethod(PasswordTransformationMethod
									.getInstance());// hide password

						}

					}
				});
	}

	private void login(){
		String email = emailid.getText().toString();
		String pssword = password.getText().toString();
		loginProcess(email,pssword);
		/*int err =0;
		if(!validateEmail(email)){
			err++;
		//	new CustomToast().Show_Toast(getActivity(), view,
		//			"아이디를 입력해 주세요.");
		}
		else if (!validateFields(pssword)) {
			err++;
		//	new CustomToast().Show_Toast(getActivity(), view,
		//			"비밀번호를 입력해 주세요.");
		}
		else if(err ==0){
			loginProcess(email,pssword);
		}*/
		/*else{
		//	new CustomToast().Show_Toast(getActivity(), view,
		//			"아이디와 비밀번호를 입력해 주세요.");
		}*/


	}

	private void loginProcess(String email, String password)
	{

		Intent intent = new Intent(getActivity(), MenuActivity.class);
		startActivity(intent);

		/*	mSubscriptions.add(NetworkUtil.getRetrofit(email, password).login()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(this::handleResponse,this::handleError));
   */
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void handleResponse(Response response){

		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(Constants.TOKEN,response.getToken());
		editor.putString(Constants.EMAIL, response.getMessage());
		editor.apply();
		//Intent intent = new Intent(getActivity(), ProfileActivity.class);
		//startActivity(intent);



	}

	private void handleError(Throwable error) {

		mProgressBar.setVisibility(View.GONE);

		if (error instanceof HttpException) {

			Gson gson = new GsonBuilder().create();

			try {

				String errorBody = ((HttpException) error).response().errorBody().string();
				Response response = gson.fromJson(errorBody,Response.class);
				new CustomToast().Show_Toast(getActivity(), view,
						response.getMessage());

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

			new CustomToast().Show_Toast(getActivity(), view,
					"Network Error");
		}
	}




	private void forgotpassword(){
		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
				.replace(R.id.frameContainer,
						new ForgotPassword_Fragment(),
						Utils.ForgotPassword_Fragment).commit();
	}

	private void signup(){
		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
				.replace(R.id.frameContainer, new SignUp_Fragment(),
						Utils.SignUp_Fragment).commit();
	}



	@Override
	public void onDestroy() {
		super.onDestroy();
		mSubscriptions.unsubscribe();
	}

}

