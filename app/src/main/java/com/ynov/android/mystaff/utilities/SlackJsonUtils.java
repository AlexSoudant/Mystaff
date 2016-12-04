package com.ynov.android.mystaff.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by admin on 01/12/16.
 */

public class SlackJsonUtils {

    public static String[] getSimpleSlackStringsFromJson(String slackJsonStr, String channel)
            throws JSONException {

        final String S_MESSAGE_CODE = "cod";

        JSONObject slackJson = new JSONObject(slackJsonStr);

        /*is there an error? */
        if (slackJson.has(S_MESSAGE_CODE)) {
            int errorCode = slackJson.getInt(S_MESSAGE_CODE);
            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* channel invalid*/
                    return null;
                /* Slack server down */
                default:
                    return null;
            }
        }

        JSONArray slackArray = slackJson.getJSONArray("channels");
        JSONArray staffListArray = new JSONArray();
        JSONObject slackChannel = new JSONObject();
        String members_list = "";

        for (int i = 0; i < slackArray.length(); i++) {

            slackChannel = slackArray.getJSONObject(i);

            if(slackChannel.getString("name").equals(channel) ){

                staffListArray = slackChannel.getJSONArray("members");

            }

        }

        String[] staffList = new String[staffListArray.length()];

        for (int j = 0; j < staffListArray.length(); j++){
            staffList[j] = staffListArray.getJSONObject(j).toString();
        }

        //staffList = new String[]{"aa","aa","aa","aa"};


        return new String[]{staffListArray.toString()};
        //return staffList;
    }
}
