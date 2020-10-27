package com.login_signup_screendesign_demo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.login_signup_screendesign_demo.models.Response;
import com.login_signup_screendesign_demo.models.User;
import com.login_signup_screendesign_demo.utils.Utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.HttpException;
import rx.subscriptions.CompositeSubscription;

public class SignUp_Fragment extends Fragment implements OnClickListener {
	private static View view;
	private static EditText fullName, emailId, mobileNumber, location,
			password, confirmPassword;
	private static TextView login;
	private static Button signUpButton;
	private static CheckBox terms_conditions;
	private CompositeSubscription mSubscriptions;
	private ProgressBar mProgressbar;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.signup_layout, container, false);
		mSubscriptions = new CompositeSubscription();
		initViews(view);
		setListeners();
		return view;
	}

	// Initialize all views
	private void initViews(View v) {
		fullName = (EditText) v.findViewById(R.id.fullName);
		emailId = (EditText) v.findViewById(R.id.userEmailId);
		mobileNumber = (EditText) v.findViewById(R.id.mobileNumber);
		location = (EditText) v.findViewById(R.id.location);
		password = (EditText) v.findViewById(R.id.password);
		confirmPassword = (EditText) v.findViewById(R.id.confirmPassword);
		signUpButton = (Button) v.findViewById(R.id.signUpBtn);
		login = (TextView) v.findViewById(R.id.already_user);
		terms_conditions = (CheckBox) v.findViewById(R.id.terms_conditions);

		// Setting text selector over textviews
		@SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			login.setTextColor(csl);
			terms_conditions.setTextColor(csl);
		} catch (Exception e) {
		}
	}

	// Set Listeners
	private void setListeners() {
		signUpButton.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signUpBtn:

			// Call checkValidation method
			checkValidation();
			break;

		case R.id.already_user:

			// Replace login fragment
			new MainActivity().replaceLoginFragment();
			break;
		}

	}

	// Check Validation Method
	private void checkValidation() {

		// Get all edittext texts
		String getFullName = fullName.getText().toString();
		String getEmailId = emailId.getText().toString();
		String getMobileNumber = mobileNumber.getText().toString();
		String getLocation = location.getText().toString();
		String getPassword = password.getText().toString();
		String getConfirmPassword = confirmPassword.getText().toString();

		// Pattern match for email id
		Pattern p = Pattern.compile(Utils.regEx);
		Matcher m = p.matcher(getEmailId);

		// Check if all strings are null or not
		if (getFullName.equals("") || getFullName.length() == 0
				|| getEmailId.equals("") || getEmailId.length() == 0
				|| getMobileNumber.equals("") || getMobileNumber.length() == 0
				|| getLocation.equals("") || getLocation.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0
				|| getConfirmPassword.equals("")
				|| getConfirmPassword.length() == 0)

			new CustomToast().Show_Toast(getActivity(), view,
					"아이디와 비밀번호를 입력해 주세요.");

		// Check if email id valid or not
		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					"존재하지 않는 아이디 입니다.");

		// Check if both password should be equal
		else if (!getConfirmPassword.equals(getPassword))
			new CustomToast().Show_Toast(getActivity(), view,
					"비밀번호가 일치하지 않습니다.");

		// Make sure user should check Terms and Conditions checkbox
		else if (!terms_conditions.isChecked())
			new CustomToast().Show_Toast(getActivity(), view,
					"이용약관 및 개인 정보 보호 정책에 동의해주세요");

		// Else do signup or do your stuff
		else {
			User user = new User(getFullName, getEmailId, getPassword, getMobileNumber, getLocation);
			registerProcess(user);
			Toast.makeText(getActivity(), "회원가입 완료", Toast.LENGTH_SHORT)
					.show();
		}
	}
	private void registerProcess(User user) {

	/*	mSubscriptions.add(NetworkUtil.getRetrofit().register(user)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(this::handleResponse,this::handleError));*/
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void handleResponse(Response response){
		mProgressbar.setVisibility(View.GONE);
		new MainActivity().replaceLoginFragment();
	}

	private void handleError(Throwable error) {

		mProgressbar.setVisibility(View.GONE);

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
}
