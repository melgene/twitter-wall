package twitter.wall.converter;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.handler.sockjs.BridgeEvent;

public class AddressConverter implements Converter<BridgeEvent, String> {

	private static final String ADDRESS = "address";

	@Override
	public String convert(BridgeEvent bridgeEvent) {
		JsonObject rawMessage = bridgeEvent.getRawMessage();
		return rawMessage.getString(ADDRESS);
	}

}
