import java.util.ArrayList;
import java.util.HashMap;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterClient {
	
	public String OAuthConsumerKey;
	public String OAuthConsumerSecret;
	public String OAuthAccessToken;
	public String OAuthAccessTokenSecret;
	
	public TwitterClient(String OAuthConsumerKey,String OAuthConsumerSecret,String OAuthAccessToken,String OAuthAccessTokenSecret) {
		this.OAuthConsumerKey = OAuthConsumerKey;
		this.OAuthConsumerSecret = OAuthConsumerSecret;
		this.OAuthAccessToken = OAuthAccessToken;
		this.OAuthAccessTokenSecret = OAuthAccessTokenSecret;
	}
	
	public ArrayList<String> getTweets(String keyword) {
		ConfigurationBuilder cb = this.connect();
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
	    Query query = new Query(keyword);
	    int numberOfTweets = 512;
	    long lastID = Long.MAX_VALUE;
	    ArrayList<String> tweet_contents = new ArrayList<>();
	    
	    ArrayList<Status> tweets = new ArrayList<Status>();
	    int i = 0;
	    do {
	    	     i++;
		    	 if (numberOfTweets - tweets.size() > 100)
		    	 {
		    		 query.setCount(101);
		    	 }    
	 	     else 
	 	      {
	 	    	 	query.setCount(numberOfTweets - tweets.size());
	 	      }
		    	 
		    	   try {
		    	        QueryResult result = twitter.search(query);
		    	        tweets.addAll(result.getTweets());
		    	        for (Status t: tweets) 
		    	          if(t.getId() < lastID) lastID = t.getId();
	    	      }
	    	      catch (TwitterException te) {
	    	    	  		System.out.println("Couldn't connect: " + te);
	    	      }; 
	    	      
	    	      query.setMaxId(lastID-1);
		} while (i <= 50);
	     
	    for (i = 0; i < tweets.size(); i++) {
		      Status t = (Status) tweets.get(i);
		      t.getText();
		      tweet_contents.add(t.getText());
	    }
	    
	    return tweet_contents;
	}
	
	protected ConfigurationBuilder connect()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	          .setOAuthConsumerKey(this.OAuthConsumerKey)
	          .setOAuthConsumerSecret(this.OAuthConsumerSecret)
	          .setOAuthAccessToken(this.OAuthAccessToken)
	          .setOAuthAccessTokenSecret(this.OAuthAccessTokenSecret);
	    
	    return cb;
	}
	
}
