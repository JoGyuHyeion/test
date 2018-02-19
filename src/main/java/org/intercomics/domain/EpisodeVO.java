package org.intercomics.domain;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component("EpisodeVO")
public class EpisodeVO {

	private int episodeNo;
	private String webtoonId;
	private String episodeId;
	private String episodeName;
	private String episodeThumbnail_s;
	private String episodeLink;
	private Date episodeDate;
	private Timestamp episodeTimestamp;
	private String episodecol;

	public int getEpisodeNo() {
		return episodeNo;
	}

	public void setEpisodeNo(int episodeNo) {
		this.episodeNo = episodeNo;
	}

	public String getWebtoonId() {
		return webtoonId;
	}

	public void setWebtoonId(String webtoonId) {
		this.webtoonId = webtoonId;
	}

	public String getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}

	public String getEpisodeName() {
		return episodeName;
	}

	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}

	public String getEpisodeThumbnail_s() {
		return episodeThumbnail_s;
	}

	public void setEpisodeThumbnail_s(String episodeThumbnail_s) {
		this.episodeThumbnail_s = episodeThumbnail_s;
	}

	public String getEpisodeLink() {
		return episodeLink;
	}

	public void setEpisodeLink(String episodeLink) {
		this.episodeLink = episodeLink;
	}

	public Date getEpisodeDate() {
		return episodeDate;
	}

	public void setEpisodeDate(Date episodeDate) {
		this.episodeDate = episodeDate;
	}

	public Timestamp getEpisodeTimestamp() {
		return episodeTimestamp;
	}

	public void setEpisodeTimestamp(Timestamp episodeTimestamp) {
		this.episodeTimestamp = episodeTimestamp;
	}

	public String getEpisodecol() {
		return episodecol;
	}

	public void setEpisodecol(String episodecol) {
		this.episodecol = episodecol;
	}

	@Override
	public String toString() {
		return "EpisodeVO [episodeNo=" + episodeNo + ", webtoonId=" + webtoonId + ", episodeId=" + episodeId
				+ ", episodeName=" + episodeName + ", episodeThumbnail_s=" + episodeThumbnail_s + ", episodeLink="
				+ episodeLink + ", episodeDate=" + episodeDate + ", episodeTimestamp=" + episodeTimestamp
				+ ", episodecol=" + episodecol + "]";
	}

}
