package twitter.wall.verticle;

import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import io.vertx.rxjava.ext.web.handler.sockjs.SockJSHandler;
import twitter.wall.twitter.TwitterEventHandler;

public class TwitterVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);

		router.route("/feed/*").handler(createSockJSHandler());
		router.route("/assets/*").handler(StaticHandler.create("assets"));
		router.route().handler(StaticHandler.create());

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	}

	private SockJSHandler createSockJSHandler() {
		BridgeOptions bridgeOptions = new BridgeOptions()
				.addInboundPermitted(new PermittedOptions())
				.addOutboundPermitted(new PermittedOptions().setAddressRegex("feed\\..+"));
		return SockJSHandler.create(vertx).bridge(bridgeOptions, new TwitterEventHandler(vertx));
	}

}
