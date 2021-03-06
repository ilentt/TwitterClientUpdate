package com.ilenlab.ilentt.twitterclient.network;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 *
 * This is the object responsible for communicating with a REST API.
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes:
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 *
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 *
 */
public class TwitterClient extends OAuthBaseClient {

    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "mawHs7IQLnMUHMLmYIg3jKQZM";       // Change this
    public static final String REST_CONSUMER_SECRET = "SU6ryrwJrE9WBP594n1VSuFL2hXz9GFRFXkXCN5SkFPVeERCsw"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://iTwitterClient"; // Change this (here and in manifest)

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getHomeTimeline(long since_id, long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("count", 25);
        if(max_id > 0) {
            requestParams.put("max_id", max_id);
        } else {
            requestParams.put("since_id", since_id);
        }

        // Execute the request
        getClient().get(apiUrl, requestParams, handler);
    }
        /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
         * 	  i.e getApiUrl("statuses/home_timeline.json");
         * 2. Define the parameters to pass to the request (query or body)
         *    i.e RequestParams params = new RequestParams("foo", "bar");
         * 3. Define the request method and make a call to the client
         *    i.e client.get(apiUrl, params, handler);
         *    i.e client.post(apiUrl, params, handler);
         */

    // to post a tweet
    public void postTweet(String myTweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", myTweet);
        // execute the request
        getClient().post(apiUrl, params, handler);
    }

    // to get the logged-in user's account info
    public void getMyUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        // execute the request
        getClient().get(apiUrl, params, handler);
    }

    public void getOtherUserInfo(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("user_id", screenName);
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(long since_id, long max_id, String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        params.put("count", 25);
        if (max_id > 0) {
            // add to the list tweets older than the currently displayed tweets
            params.put("max_id", max_id);
        } else {
            // start with the newest tweets
            params.put("since_id", since_id);
        }
        // execute the request
        getClient().get(apiUrl, params, handler);
    }

    public void getMentionsTimeline(long since_id, long max_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (max_id > 0) {
            // add to the list tweets older than the currently displayed tweets
            params.put("max_id", max_id);
        } else {
            // start with the newest tweets
            params.put("since_id", since_id);
        }
        // execute the request
        getClient().get(apiUrl, params, handler);
    }
}
