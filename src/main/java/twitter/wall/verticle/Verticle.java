package twitter.wall.verticle;

import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Route;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import io.vertx.rxjava.ext.web.handler.sockjs.SockJSHandler;

import static java.util.stream.Collectors.toList;

public class Verticle extends AbstractVerticle {


	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);

		BridgeOptions bridgeOptions = new BridgeOptions()
				.addOutboundPermitted(new PermittedOptions().setAddress("some-address"))
				.addInboundPermitted(new PermittedOptions().setAddress("some-address"));
		SockJSHandler sockJsHandler = SockJSHandler.create(vertx).bridge(bridgeOptions, be -> {
			System.out.println("EVENT: " + be.type());
			be.complete(true);
		});
		router.route("/feed/*").handler(sockJsHandler);
		router.route("/assets/*").handler(StaticHandler.create("assets"));
		router.route().handler(StaticHandler.create());
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);

		vertx.eventBus().consumer("some-address", m -> {
			System.out.println("GOT MESSAGE:" + m.address() + ":" + m.replyAddress() + "  " + m.body());
		});
		vertx.setPeriodic(3000, t -> vertx.eventBus().publish("news-feed", "news from the server!"));

	}

}
