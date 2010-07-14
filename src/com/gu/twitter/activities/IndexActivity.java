package com.gu.twitter.activities;

import java.util.List;

import com.gu.twitter.R;
import com.gu.twitter.daos.ContributorsDAO;
import com.gu.twitter.model.Contributor;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IndexActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.index);	
		populateView();
	}
	
	
	public void populateView() {		
		ContributorsDAO dao = new ContributorsDAO();
		List<Contributor> contributors = dao.getContributors();
		
		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout destintation = (LinearLayout) findViewById(R.id.AuthorsList);

		for (Contributor contributor : contributors) {
			View authorTrial = inflater.inflate(R.layout.authortrial, null);
			TextView authorsName = (TextView) authorTrial.findViewById(R.id.AuthorName);
			authorsName.setText(contributor.getName());			 
			authorsName.setOnClickListener(new AuthorClicker(contributor));			 
			destintation.addView(authorTrial);
		}		 
	}
	
	
	class AuthorClicker implements OnClickListener {
		Contributor contributor;
				
		public AuthorClicker(Contributor contributor) {
			super();
			this.contributor = contributor;
		}

		private static final String TAG = "AuthorClicker";

		@Override
		public void onClick(View v) {
			Log.i(TAG, "Click received: " + contributor.getName());
			
			// An intent is how we switch to another activity and pass arguments
			Intent intent = new Intent(v.getContext(), AuthorTweets.class);
			Bundle extras = new Bundle();
			extras.putSerializable("contributor", contributor);
			intent.putExtras(extras);
			v.getContext().startActivity(intent);			 
		}
	}
	
}
