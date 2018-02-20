package org.intercomics.domain;

import java.util.List;

public class StatisticsDTO {

	private String name;
	private List<StatisticsVO> ranks;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StatisticsVO> getRanks() {
		return ranks;
	}

	public void setRanks(List<StatisticsVO> ranks) {
		this.ranks = ranks;
	}

	@Override
	public String toString() {
		return "RankDTO [name=" + name + ", ranks=" + ranks + "]";
	}

}
