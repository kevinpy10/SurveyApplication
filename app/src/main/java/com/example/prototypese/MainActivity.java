package com.example.prototypese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class MainActivity extends AppCompatActivity {

	WebView wvForm;
	Button btnConfirm;
	LinearLayout llBottomSection;
	long reward;
	int user_id, survey_id, participant;
	private final String WEBVIEW_INTERFACE_NAME = "MyInterface";
	String FORM_URL;
	String FORM_TITLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wvForm = findViewById(R.id.wvForm);
        btnConfirm = findViewById(R.id.btnConfirm);
		llBottomSection = findViewById(R.id.llBottomSection);
		user_id = getIntent().getIntExtra("user_id", 0);
		survey_id = getIntent().getIntExtra("survey_id", 0);
		participant = getIntent().getIntExtra("participant", 0);
		reward = getIntent().getLongExtra("reward", 0);
//		FORM_URL = getIntent().getStringExtra("url");
		FORM_URL = "https://docs.google.com/forms/d/e/1FAIpQLSez9CjD4aD9vcg71xCenK0KVAQJ9svjzB7oulsEG6UaxgQ7jg/viewform";

		btnConfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Todo: Do process after user complete the survey here, ex: add balance
				SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
				editor.putString("FORM_TITLE", FORM_TITLE);
				editor.apply();

				Intent intent = new Intent(MainActivity.this, SurveyCompletedActivity.class);
				intent.putExtra("user_id", user_id);
				intent.putExtra("survey_id", survey_id);
				intent.putExtra("participant", participant);
				intent.putExtra("reward", reward);
				startActivity(intent);
				finish();
			}
		});

		/*
		WARNING !!! DO NOT TOUCH THESE LINES OF CODES
		-----------------------
		WebView Configuration
		 */
		wvForm.loadUrl(FORM_URL);
		wvForm.getSettings().setLoadsImagesAutomatically(true);
		wvForm.getSettings().setJavaScriptEnabled(true);
		wvForm.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				FORM_TITLE = view.getTitle();

				String js =
						"( " +
								"function() { " +
								"if(document.getElementsByClassName('freebirdFormviewerViewResponseConfirmContentContainer').length > 0) {" +
								WEBVIEW_INTERFACE_NAME + ".googleFormSubmitted();" +
								"}" +
								"}) " +
								"()";

				if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
					wvForm.evaluateJavascript(js, null);
				else
					wvForm.loadUrl("javascript:" + js);
			}
		});

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			wvForm.addJavascriptInterface(new WebViewInterface(MainActivity.this), WEBVIEW_INTERFACE_NAME);
		}
	}

	private class WebViewInterface {
		Context mContext;

		WebViewInterface(Context context)
		{
			mContext = context;
		}

		@JavascriptInterface
		public void googleFormSubmitted()
		{
			Log.i("MY_TAG", "googleFormSubmitted: success");

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Log.i("MY_TAG", "googleFormSubmitted: success update UI");
					llBottomSection.setVisibility(Button.VISIBLE);
				}
			});

		}
	}

}
