package org.intercomics.domain;

public class StatisticsVO {

	private String type;
	private int value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "StatisticsVO [type=" + type + ", value=" + value + "]";
	}

}
