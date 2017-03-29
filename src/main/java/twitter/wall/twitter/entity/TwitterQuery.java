package twitter.wall.twitter.entity;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class TwitterQuery {

	private String value;

	public TwitterQuery(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public boolean notEmpty() {
		return isNotEmpty(value);
	}
}
