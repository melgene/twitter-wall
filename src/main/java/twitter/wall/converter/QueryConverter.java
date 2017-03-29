package twitter.wall.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.handler.sockjs.BridgeEvent;
import twitter.wall.twitter.entity.TwitterQuery;

public class QueryConverter implements Converter<BridgeEvent, TwitterQuery> {

	private static final String HEADERS = "headers";
	private static final String QUERY = "query";

	@Override
	public TwitterQuery convert(BridgeEvent bridgeEvent) {
		JsonObject rawMessage = bridgeEvent.getRawMessage();
		JsonObject headers = rawMessage.getJsonObject(HEADERS);
		String query = headers.getString(QUERY);
		return new TwitterQuery(query);
	}

}
