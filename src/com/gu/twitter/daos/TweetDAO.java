package com.gu.twitter.daos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.gu.twitter.model.Contributor;
import com.gu.twitter.model.Tweet;
import com.gu.twitter.network.HttpFetcher;


public class TweetDAO {
	
	private static final String TAG = "TweetDAO";
	private HttpFetcher httpFetcher;
	
	
	public TweetDAO() {
		this.httpFetcher = new HttpFetcher();
	}


	public List<Tweet> getTweetsForContributor(Contributor contributor) {
		final String timelineApiUrl = getTimelineApiUrl(contributor);
		Log.i(TAG, timelineApiUrl);
		String json = httpFetcher.getContent(timelineApiUrl);
		Log.i(TAG, json);
		if (json == null) {
			return null;		
		}	
		return parseJsonForTweet(json);		
	}

	
	private List<Tweet> parseJsonForTweet(String jsonString) {
		
		List<Tweet> tweets = new ArrayList<Tweet>();			
		try {
			JSONArray json = new JSONArray(jsonString);
								
				for (int i=0; i < json.length(); i++) {
					JSONObject tweet = json.getJSONObject(i);
					Log.i(TAG, tweet.toString());
					if (tweet.has("text")) {
						tweets.add(new Tweet(tweet.getString("text")));
					}
				}
				return tweets;
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	private String getTimelineApiUrl(Contributor contributor) {
		return "http://api.twitter.com/1/statuses/user_timeline/" +
			contributor.getTwitterUsername() + ".json";
	}
	
}
