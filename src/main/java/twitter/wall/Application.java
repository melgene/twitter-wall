package twitter.wall;

import io.vertx.core.Vertx;
import twitter.wall.verticle.TwitterVerticle;

// To run from idea
public class Application {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(new TwitterVerticle());
	}

}
