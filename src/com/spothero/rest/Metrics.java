package com.spothero.rest;

public class Metrics {

	private int numRequests;
	private int numFailedRequests;
	private long responseTimeArr[];
	private int counter;
	private int rollOver;

	private static Metrics metrics;
	
	private Metrics() {
		numRequests = 0;
		numFailedRequests = 0;

		rollOver = 1000;
		responseTimeArr = new long[rollOver];
		java.util.Arrays.fill(responseTimeArr, 0);
		counter = 0;
	}
	
	public synchronized int getNumRequests() {
		return numRequests;
	}
	
	public synchronized void setNumRequests(int numRequests) {
		this.numRequests = numRequests;
	}
	
	public synchronized int getNumFailedRequests() {
		return numFailedRequests;
	}

	public synchronized void setNumFailedRequests(int numFailedRequests) {
		this.numFailedRequests = numFailedRequests;
	}
	
	public synchronized double getAvgResponseTime() {
		
		double sumTime = 0.0;
		
		for( long time : this.responseTimeArr )
			sumTime += time;
		
		if( numRequests < rollOver )
			return ((double)sumTime) / ((double)numRequests);
		else
			return ((double)sumTime) / ((double)rollOver);
	}

	public synchronized void setAvgResponseTime(long responseTime) {
		counter = 0;	
		numRequests = 0;
		
		this.responseTimeArr[counter] = responseTime;
		counter++;
		numRequests++;
	}
	
	public synchronized void addRequest() {
		this.numRequests++;
	}
	
	public synchronized void addFailedRequest() {
		this.numFailedRequests++;
	}
	
	public synchronized void addResponseTime(long responseTime) {
		this.responseTimeArr[counter] = responseTime;
		counter++;
		
		if(counter >= rollOver)
			counter = 0;
	}
	
	public synchronized static void clearMetrics() {
		metrics = new Metrics();
	}
	
	public synchronized static Metrics getMetrics() {
		if ( metrics == null )
			metrics = new Metrics(); 
		
		return metrics;
	}
}