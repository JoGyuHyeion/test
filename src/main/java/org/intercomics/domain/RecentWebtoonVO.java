package org.intercomics.domain;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component("RecentWebtoonVO")
public class RecentWebtoonVO {

	private String userId;
	private String webtoonId;
	private int episodeNo;
	private Date viewTime;

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

	public int getEpisodeNo() {
		return episodeNo;
	}

	public void setEpisodeNo(int episodeNo) {
		this.episodeNo = episodeNo;
	}

	public Date getViewTime() {
		return viewTime;
	}

	public void setViewTime(Date viewTime) {
		this.viewTime = viewTime;
	}

	@Override
	public String toString() {
		return "RecentWebtoonVO [userId=" + userId + ", webtoonId=" + webtoonId + ", episodeNo=" + episodeNo
				+ ", viewTime=" + viewTime + "]";
	}

}
