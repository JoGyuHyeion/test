package org.intercomics.domain;

import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component("SearchCriteria")
public class SearchCriteria extends Criteria {

	private String day;
	private String platforms[];
	private String allPlatform;
	private String webtoonId;
	private int episodeNo;
	private String userId;
	private String genre;
	private String writer;
	private String orderby;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = String.valueOf(Day.valueOf(day).ordinal());
	}

	public String[] getPlatforms() {
		return platforms;
	}

	public void setPlatforms(String[] platforms) {
		this.platforms = platforms;
	}

	public String getWebtoonId() {
		return webtoonId;
	}

	public String getAllPlatform() {
		return allPlatform;
	}

	public void setAllPlatform(String allPlatform) {
		this.allPlatform = allPlatform;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	@Override
	public String toString() {
		return "SearchCriteria [day=" + day + ",allPlatform=" + allPlatform + ", platforms="
				+ Arrays.toString(platforms) + ", webtoonId=" + webtoonId + ", userId=" + userId + ", genre=" + genre
				+ ", writer=" + writer + ", episodeNo=" + episodeNo + ", scroll=" + getScroll() + "perScrollNum="
				+ getPerScrollNum() + "]";
	}

}
