package com.login_signup_screendesign_demo;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.login_signup_screendesign_demo.models.Response;
import com.login_signup_screendesign_demo.utils.Utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.adapter.rxjava.HttpException;
import rx.subscriptions.CompositeSubscription;

public class ForgotPassword_Fragment extends Fragment implements OnClickListener{
		public interface Listener {

			void onPasswordReset(String message);
		}
	private static View view;
	private MainActivity mListener;
	private static EditText emailId;
	private static TextView submit, back;

	private CompositeSubscription mSubscriptions;
	public ForgotPassword_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.forgotpassword_layout, container,
				false);
		mSubscriptions = new CompositeSubscription();
		initViews();
		setListeners();
		return view;
	}

	//public void onAttach(Context context) {
	//	super.onAttach((Activity) context);
	//	mListener = (MainActivity)context;
//	}

	// Initialize the views
	private void initViews() {
		emailId = (EditText) view.findViewById(R.id.registered_emailid);
		submit = (TextView) view.findViewById(R.id.forgot_button);
		back = (TextView) view.findViewById(R.id.backToLoginBtn);

		// Setting text selector over textviews
		@SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			back.setTextColor(csl);
			submit.setTextColor(csl);

		} catch (Exception e) {
		}

	}

	// Set Listeners over buttons
	private void setListeners() {
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backToLoginBtn:

			// Replace Login Fragment on Back Presses
			new MainActivity().replaceLoginFragment();
			break;

		case R.id.forgot_button:

			// Call Submit button task
			resetPasswordInit();
			break;

		}

	}

	private void resetPasswordInit() {
		String getEmailId = emailId.getText().toString();

		// Pattern for email id validation
		Pattern p = Pattern.compile(Utils.regEx);

		// Match the pattern
		Matcher m = p.matcher(getEmailId);
		int err =0;
		// First check if email id is not null else show error toast
		if (getEmailId.equals("") || getEmailId.length() == 0) {

			err++;
			new CustomToast().Show_Toast(getActivity(), view,
					"이메일 아이디를 입력해 주세요.");
		}
		// Check if email id is valid or not
		else if (!m.find()) {
			err++;
			new CustomToast().Show_Toast(getActivity(), view,
					"존재하지 않는 아이디입니다.");
		}
		// Else submit email id and fetch passwod or do your stuff
		if(err==0) {
			Toast.makeText(getActivity(), "Get Forgot Password.",
					Toast.LENGTH_SHORT).show();
			resetPasswordInitProgress(emailId.toString());
		}
	}


	private void resetPasswordInitProgress(String email) {

	/*	mSubscriptions.add(NetworkUtil.getRetrofit().resetPasswordInit(email)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(this::handleResponse,this::handleError));*/
	}

	private void handleResponse(Response response) {

		new MainActivity().replaceLoginFragment();

	}

	private void handleError(Throwable error) {


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



	@Override
	public void onDestroy() {
		super.onDestroy();
		mSubscriptions.unsubscribe();
	}
		}