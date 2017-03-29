package twitter.wall.converter;

import twitter.wall.twitter.entity.Tweet;
import twitter4j.Status;

public class TweetConverter implements Converter<Status, Tweet> {

	@Override
	public Tweet convert(Status status) {
		Tweet tweet = new Tweet();
		tweet.setText(status.getText());
		tweet.setUserId(status.getUser().getScreenName());
		tweet.setUserName(status.getUser().getName());
		tweet.setCreatedAt(status.getCreatedAt());
		tweet.setUserImage(status.getUser().getMiniProfileImageURLHttps());
		tweet.setId(status.getId());
		return tweet;
	}

}
