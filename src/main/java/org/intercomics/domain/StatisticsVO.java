package org.intercomics.domain;

public class StatisticsVO {
	private String name;
	private int count;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "StatisticsVO [name=" + name + ", count=" + count + "]";
	}

}
