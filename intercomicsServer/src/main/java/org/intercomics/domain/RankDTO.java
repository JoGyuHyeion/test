package org.intercomics.domain;

import java.util.List;

public class RankDTO {

	private String name;
	private List<WebtoonVO> ranks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WebtoonVO> getRanks() {
		return ranks;
	}

	public void setRanks(List<WebtoonVO> ranks) {
		this.ranks = ranks;
	}

	@Override
	public String toString() {
		return "RankDTO [name=" + name + ", ranks=" + ranks + "]";
	}

}
