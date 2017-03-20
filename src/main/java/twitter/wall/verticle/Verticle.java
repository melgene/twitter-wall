package twitter.wall.verticle;

import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import io.vertx.rxjava.ext.web.handler.sockjs.SockJSHandler;
import rx.Observable;
import rx.Subscription;

public class Verticle extends AbstractVerticle {


	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);

		router.route("/news-feed/*").handler(SockJSHandler.create(vertx).socketHandler(sockJSSocket -> {
			Observable<String> msg = vertx.eventBus().<String>consumer("news-feed")
					.bodyStream()
					.toObservable();

			Subscription subscription = msg.subscribe(sockJSSocket::write);

			sockJSSocket.endHandler(v -> subscription.unsubscribe());
		}));

		router.route().handler(StaticHandler.create());

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);

		vertx.setPeriodic(1000, t -> vertx.eventBus().publish("news-feed", "news from the server!"));


	}

}
