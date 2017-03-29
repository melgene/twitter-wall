package twitter.wall.twitter;


import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.handler.sockjs.BridgeEvent;
import rx.Observable;
import twitter.wall.converter.AddressConverter;
import twitter.wall.converter.Converter;
import twitter.wall.converter.QueryConverter;
import twitter.wall.twitter.entity.Tweet;
import twitter.wall.twitter.entity.TwitterQuery;
import twitter.wall.util.ConnectionHolder;

import java.util.List;

public class TwitterEventHandler implements Handler<BridgeEvent> {

	private final Vertx vertx;
	private final ConnectionHolder connectionHolder;
	private final TwitterService twitterService = new TwitterService();

	private final Converter<BridgeEvent, String> addressConverter = new AddressConverter();
	private final Converter<BridgeEvent, TwitterQuery> queryConverter = new QueryConverter();

	public TwitterEventHandler(Vertx vertx) {
		this.vertx = vertx;
		this.connectionHolder = new ConnectionHolder(vertx);
	}

	@Override
	public void handle(BridgeEvent bridgeEvent) {
		System.out.println(bridgeEvent.type() + "   " + bridgeEvent.getRawMessage());
		switch (bridgeEvent.type()) {
			case REGISTER:
				onRegister(bridgeEvent);
				break;
			case UNREGISTER:
				onUnregister(bridgeEvent);
				break;
			default:
				bridgeEvent.complete(true);
		}
	}

	private void onRegister(BridgeEvent bridgeEvent) {
		String address = addressConverter.convert(bridgeEvent);
		TwitterQuery query = queryConverter.convert(bridgeEvent);
		if (query.notEmpty()) {
			bridgeEvent.complete(true);
			connectionHolder.registerPeriodic(address, 3000, () -> sendTweetsByQuery(address, query));
		} else {
			bridgeEvent.complete(false);
		}
	}

	private void sendTweetsByQuery(String address, TwitterQuery query) {
		vertx.<List<Tweet>>rxExecuteBlocking(future -> future.complete(twitterService.search(query)))
				.toObservable().flatMap(Observable::from).map(JsonObject::mapFrom)
				.forEach(tweet -> vertx.eventBus().send(address, tweet));
	}

	private void onUnregister(BridgeEvent bridgeEvent) {
		String address = addressConverter.convert(bridgeEvent);
		connectionHolder.unregisterPeriodic(address);
	}

}
