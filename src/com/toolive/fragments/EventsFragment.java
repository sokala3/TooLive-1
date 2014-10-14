package com.toolive.fragments;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.toolive.R;
import com.toolive.TooLive;
import com.toolive.library.HttpManager;
import com.toolive.library.HttpManager.Request;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventsFragment extends Fragment {
	private ListView myListView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        
        myListView = (ListView)rootView.findViewById(R.id.cat_listview);
        
        final String url = ((TooLive)getActivity().getApplication()).SERVER_URL + ((TooLive)getActivity().getApplication()).CATEGORY;
    	
        HttpTaskGet task = new HttpTaskGet();
        task.execute(url);
        
        
        return rootView;
    }
    
    private class HttpTaskGet extends AsyncTask<String, String, String>
	{
		@Override
		protected String doInBackground(String... params) {
			return HttpManager.getContent((String)params[0], Request.GET);
		}	
		
		@Override
		protected void onPostExecute(String result) 
		{
			try {
				List<String> categories = new LinkedList<>();
				JSONObject obj = new JSONObject(result);
				
				boolean error = obj.getBoolean("error");
				
				if(!error)
				{
					JSONArray jArray = obj.getJSONArray("categories");
				
					for(int count = 0; count < jArray.length(); count++)
					{
						obj = jArray.getJSONObject(count);
						categories.add(obj.getString("name"));
					}
					
					myListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.categories_list_item, R.id.label, categories));
				}
			} 
			catch (JSONException e) {}
		}
	}
}
