package com.gu.twitter.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gu.twitter.R;
import com.gu.twitter.daos.TweetDAO;
import com.gu.twitter.model.Contributor;
import com.gu.twitter.model.Tweet;

public class AuthorTweets extends Activity {

	private static final String TAG = "AuthorTweets";

	private static final int TWEETS_AVAILABLE = 1;

	
	UpdateTweetsHandler updateTweetsHandler;	
	List<Tweet> loadedTweets;
	
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.authortweets);
		
		// Retrieve the contributor argument which was past to us.
		Contributor contributor = (Contributor) this.getIntent().getExtras().getSerializable("contributor");
		Log.i(TAG, "Got pass contributor: " + contributor.getName());
		updateTweetsHandler = new UpdateTweetsHandler();
		populateView(contributor);
	}
	
	
	private void populateView(Contributor contributor) {
		// This part is fast - do it in the main thread
		TextView authorNameTextView = (TextView) findViewById(R.id.authorName);
		authorNameTextView.setText(contributor.getName());
		TextView authorDescriptionTextView = (TextView) findViewById(R.id.authorDescription);
		authorDescriptionTextView.setText(contributor.getDescription());
		
		// The api call to Twitter is slow - we need to spin off a seperate thread so that the user 
		// gets control of the phone back. However, background threads can't write to the gui, 
		// so we will need to use a Message and Handler to signal the main thread when the content is available for drawing.
		Runnable tweetLoaderRunnable = new TweetLoader(contributor);
		Thread tweetLoader = new Thread(tweetLoaderRunnable);
		tweetLoader.start();
	}
	

	private void populateTweets(List<Tweet> tweetsForContributor) {		
		for(Tweet tweet: tweetsForContributor) {
			Log.i(TAG, "Populating tweet: " + tweet.getText());				
			LinearLayout destintation = (LinearLayout) findViewById(R.id.tweetList);										
			inflateTweet(tweet, destintation);		
		}				
	}
	
	private void inflateTweet(Tweet tweet, LinearLayout destintation) {
		 LayoutInflater inflater = LayoutInflater.from(this);
		 View tweetTrial = inflater.inflate(R.layout.tweettrial, null);

		 TextView authorsName = (TextView) tweetTrial.findViewById(R.id.Tweet);
		 authorsName.setText(tweet.getText());
		 destintation.addView(tweetTrial);
	}

		
	class TweetLoader implements Runnable {
		
		private Contributor contributor;
		
		public TweetLoader(Contributor contributor) {
			super();
			this.contributor = contributor;
		}

		@Override
		public void run() {
			fetchTweetsFromSlowRunningApiCall(contributor);		
		}
		
		
		private void fetchTweetsFromSlowRunningApiCall(Contributor contributor) {
			TweetDAO tweetDAO = new TweetDAO();
						
			List<Tweet> tweetsForContributor = tweetDAO.getTweetsForContributor(contributor);
			loadedTweets = tweetsForContributor;
			
			// Send a message to the main thread saying there are tweets available to display
			Message m = new Message();
			m.what = TWEETS_AVAILABLE;
			updateTweetsHandler.sendMessage(m);			
		}

	}

	
	class UpdateTweetsHandler extends Handler {
	
		@Override
		public void handleMessage(Message msg) {
			Log.i(TAG, "Got tweets loaded message");
			super.handleMessage(msg);
			
			if (msg.what == TWEETS_AVAILABLE) {
				if(loadedTweets != null) {
					populateTweets(loadedTweets);
				}
			}
		}
		
	}
		
}
