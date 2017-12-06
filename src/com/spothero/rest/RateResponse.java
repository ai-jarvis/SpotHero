package com.spothero.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RateResponse {

	private String rate;

	public RateResponse() {}
	
	public RateResponse(String rate) {
		this.rate = rate;
	}
	
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public String getRate() {
		return rate;
	}
	
	@Override
	public String toString() {
		return rate;
	}
}
