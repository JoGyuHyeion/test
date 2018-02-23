package org.intercomics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RankVO extends WebtoonVO {
	private String webtoonId;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String preRank;
	private String currentRank;
	private int difference;
	private String type;

	public String getWebtoonId() {
		return webtoonId;
	}

	public void setWebtoonId(String webtoonId) {
		this.webtoonId = webtoonId;
	}

	public String getPreRank() {
		return preRank;
	}

	public void setPreRank(String preRank) {
		this.preRank = preRank;
	}

	public String getCurrentRank() {
		return currentRank;
	}

	public void setCurrentRank(String currentRank) {
		this.currentRank = currentRank;
	}

	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RankVO [webtoonId=" + webtoonId + ", preRank=" + preRank + ", currentRank=" + currentRank
				+ ", difference=" + difference + ", type=" + type + "]";
	}

}
