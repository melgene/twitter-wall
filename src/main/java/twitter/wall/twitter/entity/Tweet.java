package twitter.wall.twitter.entity;

import java.util.Date;

public class Tweet {

	private long id;

	private String userId;
	private String userName;
	private String userImage;

	private String text;
	private Date createdAt;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
}
