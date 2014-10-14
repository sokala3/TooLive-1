package com.toolive;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.toolive.library.HttpManager;
import com.toolive.library.HttpManager.Request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        Button loginButton = (Button) findViewById(R.id.btnLogin);    
        
        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(isOnline())
				{
					// Switching to Register screen
					Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
					startActivity(i);
				}
				else
				{
					Toast.makeText(LoginActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
				}
			}
		});
        
        loginButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) 
            {
            	final String url = ((TooLive)getApplication()).SERVER_URL + ((TooLive)getApplication()).LOGIN;
            	
            	if(isOnline())
            	{
	            	String username = ((EditText)findViewById(R.id.loginUsername)).getText().toString();
	            	String password = ((EditText)findViewById(R.id.loginPassword)).getText().toString();
	            	
	            	if(username.isEmpty() || password.isEmpty())
	            	{
	            		Toast.makeText(LoginActivity.this, "One or more fields are empty", Toast.LENGTH_LONG).show();
	            	}
	            	else
	            	{
		            	List<NameValuePair> params = new LinkedList<NameValuePair>();
		            	params.add(new BasicNameValuePair("username", username));
		            	params.add(new BasicNameValuePair("password", password));
		            	
		            	HttpTaskPost task = new HttpTaskPost();
		            	task.execute(url, params);
	            	}
            	}
            	else
            	{
            		Toast.makeText(LoginActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            	}
            }	
        });
    }
    
    protected boolean isOnline()
    {
    	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo netInfo = cm.getActiveNetworkInfo();
    	
    	if(netInfo != null && netInfo.isConnectedOrConnecting())
    	{
    		return true;
    	}
    	
    	return false;
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
			try {
				JSONObject obj = new JSONObject(result);
				
				boolean error = obj.getBoolean("error");
				
				if(!error)
				{
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(LoginActivity.this, getResources().getString(R.string.invalid_credentials), Toast.LENGTH_LONG).show();
				}
			} 
			catch (JSONException e) 
			{
			}
		}
	}
}