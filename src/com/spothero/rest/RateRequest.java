package com.spothero.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RateRequest {

	private String day;
	private int startTime, endTime;
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
}
