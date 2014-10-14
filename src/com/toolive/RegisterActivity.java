package com.toolive;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.toolive.library.HttpManager;
import com.toolive.library.HttpManager.Request;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        Button registerButton = (Button) findViewById(R.id.btnRegister);
        
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// Switching to Login Screen/closing register screen
				finish();
			}
		});
        
        final String url = ((TooLive)getApplication()).SERVER_URL + ((TooLive)getApplication()).REGISTER;
        
        registerButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) 
            {
            	String fullname = ((EditText)findViewById(R.id.reg_fullname)).getText().toString();
            	String email = ((EditText)findViewById(R.id.reg_email)).getText().toString();
            	String username = ((EditText)findViewById(R.id.reg_username)).getText().toString();
            	String password = ((EditText)findViewById(R.id.reg_password)).getText().toString();
            	String cPassword = ((EditText)findViewById(R.id.reg_comfirm_password)).getText().toString();
            	
            	if(fullname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || cPassword.isEmpty())
            	{
            		Toast.makeText(RegisterActivity.this, getResources().getString(R.string.missing_fields), Toast.LENGTH_LONG).show();
            	}
            	else
            	{
	            	if(cPassword.equals(password))
	            	{
	            		List<NameValuePair> params = new LinkedList<NameValuePair>();
	                	params.add(new BasicNameValuePair("username", username));
	                	params.add(new BasicNameValuePair("password", password));
	                	params.add(new BasicNameValuePair("name", fullname));
	                	params.add(new BasicNameValuePair("email", email));
	                	
	                	HttpTaskPost task = new HttpTaskPost();
	                	task.execute(url, params);
	            	}
	            	else
	            	{
	            		Toast.makeText(RegisterActivity.this, getResources().getString(R.string.password_mismatch), Toast.LENGTH_LONG).show();
	            	}
            	}
            }
        });
    }
    
    private class HttpTaskPost extends AsyncTask<Object, String, String>
	{
		@SuppressWarnings("unchecked")
		@Override
		protected String doInBackground(Object... params) {
			return HttpManager.getContent((String)params[0], Request.POST, (List<NameValuePair>)params[1]);
		}	
		
		@Override
		protected void onPostExecute(String result) 
		{
			Log.e("Register Result", result);
		}
	}
}