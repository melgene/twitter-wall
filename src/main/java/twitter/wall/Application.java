package twitter.wall;

import io.vertx.core.Vertx;
import twitter.wall.verticle.Verticle;

public class Application {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(new Verticle());
	}

}
