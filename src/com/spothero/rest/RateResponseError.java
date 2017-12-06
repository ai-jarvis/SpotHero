package com.spothero.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RateResponseError {

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
