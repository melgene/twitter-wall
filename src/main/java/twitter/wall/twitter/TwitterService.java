package twitter.wall.twitter;

import twitter.wall.converter.Converter;
import twitter.wall.converter.TweetConverter;
import twitter.wall.twitter.entity.Tweet;
import twitter.wall.twitter.entity.TwitterQuery;
import twitter4j.*;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.toList;

public class TwitterService {

	private final Twitter twitter = new TwitterFactory().getInstance();
	private final Converter<Status, Tweet> tweetConverter = new TweetConverter();

	public List<Tweet> search(TwitterQuery twitterQuery) {
		List<Tweet> statusList;
		try {
			Query query = new Query(twitterQuery.getValue());
			QueryResult searchResult = twitter.search(query);
			statusList = searchResult.getTweets().stream().map(tweetConverter::convert)
					.sorted(comparing(tw -> tw.getCreatedAt()))
					.collect(toList());
		} catch (TwitterException e) {
			statusList = emptyList();
		}
		return statusList;
	}

}
