package com.ynov.android.mystaff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ynov.android.mystaff.data.MystaffPreferences;
import com.ynov.android.mystaff.utilities.NetworkUtils;
import com.ynov.android.mystaff.utilities.SlackJsonUtils;

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
        String channel = MystaffPreferences.getDefaultSlackChannel();
        new SlackQueryTask().execute(channel);
    }

    public class SlackQueryTask extends AsyncTask<String,Void,Object[]>{
        @Override
        protected Object[] doInBackground(String... params) {
            String channel = params[0];
            URL slackRequestUrl = NetworkUtils.buildUrl(channel);
            try {
                String jsonSlackResponse = NetworkUtils.getReponseFromHttpUrl(slackRequestUrl);

                String[] simpleJsonSlackData = SlackJsonUtils
                        .getSimpleSlackStringsFromJson(jsonSlackResponse, channel);

                URL presenceRequestUrl = NetworkUtils.buildUrlPresence();

                String jsonPresenceResponse = NetworkUtils.getReponseFromHttpUrl(presenceRequestUrl);

                Object[] simpleJsonPresenceData = SlackJsonUtils
                            .getPresenceFromStaffList(jsonPresenceResponse, simpleJsonSlackData);

                return simpleJsonPresenceData;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

        }

        @Override
        protected void onPostExecute(Object[] slackSearchResults) {
            if (slackSearchResults != null && !slackSearchResults.equals("")){
                String[] membersRealNames = (String[])slackSearchResults[0];
                String[] membersPresence = (String[])slackSearchResults[1];
                for(int i = 0; i < membersRealNames.length; i++){
                        mStaff_list.append((membersRealNames[i])+ "\n" + (membersPresence[i]) + "\n\n\n");
                }

            }
        }


    }


}
