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

        String[] staffList = new String[slackArray.length()];

        for (int i = 0; i < slackArray.length(); i++) {
            if(slackArray.getJSONObject(i).getString("name")== channel ){
                staffList[i] = slackArray.getJSONObject(i).getString("members");
            }


        }

        return staffList;
    }
}
