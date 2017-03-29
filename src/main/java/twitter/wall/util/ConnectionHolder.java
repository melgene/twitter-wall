package twitter.wall.util;


import io.vertx.rxjava.core.Vertx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionHolder {

	private final Vertx vertx;
	private final Map<String, Long> connections = new ConcurrentHashMap<>();

	public ConnectionHolder(Vertx vertx) {
		this.vertx = vertx;
	}

	public void registerPeriodic(String address, long delay, Runnable runnable) {
		long periodicHandle = vertx.setPeriodic(delay, t -> {
			runnable.run();
		});
		connections.put(address, periodicHandle);
	}

	public void unregisterPeriodic(String address) {
		Long periodicHandle = connections.remove(address);
		vertx.cancelTimer(periodicHandle);
	}


}
