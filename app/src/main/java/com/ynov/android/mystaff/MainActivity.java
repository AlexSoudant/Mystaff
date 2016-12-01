package com.ynov.android.mystaff;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ynov.android.mystaff.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mStaff_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStaff_list = (TextView) findViewById(R.id.Staff_list);

        loadSlackData();

    }
    private void loadSlackData() {
        new SlackQueryTask().execute();
    }

    public class SlackQueryTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            String channel = params[0];
            URL slackRequestUrl = NetworkUtils.buildUrl(channel);
            try {
                String jsonSlackResponse = NetworkUtils.getReponseFromHttpUrl(slackRequestUrl);

                String[] simpleJsonSlackData = OpenSlackJsonUtils
                        .getSimpleSlackStringsFromJson(MainActivity.this, jsonSlackResponse);

                return simpleJsonSlackData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String slackSearchResults) {
            if (slackSearchResults != null && !slackSearchResults.equals("")){
                mStaff_list.setText(slackSearchResults);
            }
        }
    }
}