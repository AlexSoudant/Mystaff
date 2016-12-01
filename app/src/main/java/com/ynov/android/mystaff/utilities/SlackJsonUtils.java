package com.ynov.android.mystaff.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by admin on 01/12/16.
 */

public class SlackJsonUtils {

    public static String getSimpleSlackStringsFromJson(String slackJsonStr)
            throws JSONException {

        final String S_MESSAGE_CODE = "cod";
        /* String array to hold the staff list in JSON format from Slack API */
        String[] parsedSlackData = null;

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


        String members;

        members = slackJson.getString("members");

        return members;
    }






}
