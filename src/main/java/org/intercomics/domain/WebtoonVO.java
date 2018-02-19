package org.intercomics.domain;

import org.springframework.stereotype.Component;

@Component("WebtoonVO")
public class WebtoonVO {

	private String webtoonId;
	private String webtoonName;
	private String webtoonThumbnail_s;
	private String webtoonIntroduce;
	private int isNew;
	private String lastDate;
	private String day;
	private String genre;
	private String platform;
	private String writer;
	private Boolean isUpdate;
	private int viewCount;

	public String getWebtoonId() {
		return webtoonId;
	}

	public void setWebtoonId(String webtoonId) {
		this.webtoonId = webtoonId;
	}

	public String getWebtoonName() {
		return webtoonName;
	}

	public void setWebtoonName(String webtoonName) {
		this.webtoonName = webtoonName;
	}

	public String getWebtoonThumbnail_s() {
		return webtoonThumbnail_s;
	}

	public void setWebtoonThumbnail_s(String webtoonThumbnail_s) {
		this.webtoonThumbnail_s = webtoonThumbnail_s;
	}

	public String getWebtoonIntroduce() {
		return webtoonIntroduce;
	}

	public void setWebtoonIntroduce(String webtoonIntroduce) {
		this.webtoonIntroduce = webtoonIntroduce;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Boolean getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	@Override
	public String toString() {
		return "WebtoonVO [webtoonId=" + webtoonId + ", webtoonName=" + webtoonName + ", webtoonThumbnail_s="
				+ webtoonThumbnail_s + ", webtoonIntroduce=" + webtoonIntroduce + ", isNew=" + isNew + ", lastDate="
				+ lastDate + ", day=" + day + ", genre=" + genre + ", platform=" + platform + ", writer=" + writer
				+ ", isUpdate=" + isUpdate + ", viewCount=" + viewCount + "]";
	}

}
