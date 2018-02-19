package org.intercomics.domain;

import org.springframework.stereotype.Component;

@Component("RelationshipVO")
public class RelationshipVO {

	private String user_id;
	private String webtoon_id;
	private int rating = 0;
	private String episodeId;
	private String viewTime;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getWebtoon_id() {
		return webtoon_id;
	}
	public void setWebtoon_id(String webtoon_id) {
		this.webtoon_id = webtoon_id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getEpisode() {
		return episodeId;
	}
	public void setEpisode(String episodeId) {
		this.episodeId = episodeId;
	}
	public String getViewTime() {
		return viewTime;
	}
	public void setViewTime(String viewTime) {
		this.viewTime = viewTime;
	}
	
}
