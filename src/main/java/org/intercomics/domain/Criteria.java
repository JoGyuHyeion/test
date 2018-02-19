package org.intercomics.domain;

import org.springframework.stereotype.Component;

@Component("Criteria")
public class Criteria {

	private int scroll;
	private int perScrollNum;
//	private int totalCount;
//	private boolean isLast = false;

	public Criteria() {
		this.scroll = 1;
		this.perScrollNum = 9;
	}

	public void setScroll(int scroll) {

		if (scroll <= 0) {
			this.scroll = 1;
			return;
		}

		this.scroll = scroll;
	}

	public void setPerScrollNum(int perScrollNum) {

		if (perScrollNum <= 0 || perScrollNum > 100) {
			this.perScrollNum = 9;
			return;
		}

		this.perScrollNum = perScrollNum;
	}

	public int getScroll() {
		return scroll;
	}

	// method for MyBatis SQL Mapper -
	public int getScrollStart() {

		return (this.scroll - 1) * perScrollNum;
	}

	// method for MyBatis SQL Mapper
	public int getPerScrollNum() {

		return this.perScrollNum;
	}

	@Override
	public String toString() {
		return "Criteria [scroll=" + scroll + ", perScrollNum=" + perScrollNum + "]";
	}

//	public void setTotalCount(int totalCount) {
//		this.totalCount = totalCount;
//		calcData();
//	}
//
//	public boolean isLast() {
//		return isLast;
//	}
//
//	private void calcData() {
//		int endScroll = (int) (Math.ceil(getScroll()) / (double) getPerScrollNum() * getPerScrollNum());
//		int tempEndScroll = (int) (Math.ceil(totalCount) / (double) getPerScrollNum());
//		if (endScroll > tempEndScroll) {
//			endScroll = tempEndScroll;
//		}
//		isLast = endScroll * perScrollNum >= totalCount ? true : false;
//	}



}
