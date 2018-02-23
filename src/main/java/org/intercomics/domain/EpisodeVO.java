package org.intercomics.domain;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component("EpisodeVO")
public class EpisodeVO {

	private int episodeNo;
	private String webtoonId;
	private String episodeId;
	private String episodeName;
	private String episodeThumbnail_s;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String episodeThumbnail_m;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String episodeThumbnail_b;
	private String episodeLink;
	private String episodeDate;
	private String episodeTimestamp;
	private String charge;

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

	public String getEpisodeThumbnail_m() {
		return episodeThumbnail_m;
	}

	public void setEpisodeThumbnail_m(String episodeThumbnail_m) {
		this.episodeThumbnail_m = episodeThumbnail_m;
	}

	public String getEpisodeThumbnail_b() {
		return episodeThumbnail_b;
	}

	public void setEpisodeThumbnail_b(String episodeThumbnail_b) {
		this.episodeThumbnail_b = episodeThumbnail_b;
	}

	public String getEpisodeLink() {
		return episodeLink;
	}

	public void setEpisodeLink(String episodeLink) {
		this.episodeLink = episodeLink;
	}

	public String getEpisodeDate() {
		return episodeDate;
	}

	public void setEpisodeDate(String episodeDate) {
		this.episodeDate = episodeDate;
	}

	public String getEpisodeTimestamp() {
		return episodeTimestamp;
	}

	public void setEpisodeTimestamp(String episodeTimestamp) {
		this.episodeTimestamp = episodeTimestamp;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	@Override
	public String toString() {
		return "EpisodeVO [episodeNo=" + episodeNo + ", webtoonId=" + webtoonId + ", episodeId=" + episodeId
				+ ", episodeName=" + episodeName + ", episodeThumbnail_s=" + episodeThumbnail_s
				+ ", episodeThumbnail_m=" + episodeThumbnail_m + ", episodeThumbnail_b=" + episodeThumbnail_b
				+ ", episodeLink=" + episodeLink + ", episodeDate=" + episodeDate + ", episodeTimestamp="
				+ episodeTimestamp + ", charge=" + charge + "]";
	}

}
