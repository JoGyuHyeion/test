package org.intercomics.domain;

import org.springframework.stereotype.Component;

@Component("MywebtoonVO")
public class MywebtoonVO {

	private String userId;
	private String webtoonId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWebtoonId() {
		return webtoonId;
	}

	public void setWebtoonId(String webtoonId) {
		this.webtoonId = webtoonId;
	}

	@Override
	public String toString() {
		return "MywebtoonVO [userId=" + userId + ", webtoonId=" + webtoonId + "]";
	}

}
