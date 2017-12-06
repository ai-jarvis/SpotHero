package com.spothero.rest;

import java.time.LocalDate;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/rate")
public class RateService {
	
	/*
	 * 
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
	public Response getRate(  @QueryParam("start")String startDateTime, 
							 @QueryParam("end") String endDateTime,
							 @QueryParam("rateschedule") String rateSchedule) {
	
		long requestStart = System.currentTimeMillis();
		
		try {
			if(startDateTime.isEmpty() || endDateTime.isEmpty() || rateSchedule.isEmpty())
				throw new Exception("Missing arguement in request");
			
			RateResponse rateResponse = findRate(new ObjectMapper().readValue(rateSchedule, RateSchedule.class), buildRateRequest(startDateTime, endDateTime));
			Metrics.getMetrics().addRequest();
			Metrics.getMetrics().addResponseTime(System.currentTimeMillis() - requestStart);
            return Response.status(200).entity(rateResponse).build();
            
        } catch (Exception e) {
        		Metrics.getMetrics().addFailedRequest();
        		RateResponseError rse = new RateResponseError();
        		rse.setErrorMessage("There was an error in the format of your request.");
        		e.getMessage();
    			return Response.status(400).entity(rse).build();
        }
	}
	
	/*
	 * 
	 */
	public RateResponse findRate(RateSchedule rates, RateRequest request) {
		
		for( Rate rate : rates.getRates() ) {
			if( rate.getDays().toLowerCase().contains(request.getDay().toLowerCase())){
				String[] times = rate.getTimes().split("-");
				
				int start = Integer.parseInt(times[0]+= "00");
				int end = Integer.parseInt(times[1]+= "00");
				
				if(start < request.getStartTime() && end > request.getEndTime())
					return new RateResponse(new Integer(rate.getPrice()).toString());
			}
		}
		
		return new RateResponse("Unavailable");
	}
	
	/*
	 * 
	 */
	public RateRequest buildRateRequest(String startTime, String endTime) {
		
		if( !(startTime.split("T"))[0].equalsIgnoreCase((endTime.split("T"))[0]) )
			return null;
		
		RateRequest rateRequest = new RateRequest();
		rateRequest.setDay(convertToDayOfWeek((startTime.split("T"))[0]));		
		rateRequest.setStartTime(convertTime((startTime.split("T"))[1]));
		rateRequest.setEndTime(convertTime((endTime.split("T"))[1]));
		
		if(rateRequest.getStartTime() > rateRequest.getEndTime())
			return null;
		
		return rateRequest;
	}
	
	/*
	 * 
	 */
	public int convertTime(String time) {
		time = time.replaceAll(":", "");
		time = time.replace("Z", "");
		return Integer.parseInt(time);
	}
	
	/*
	 * 
	 */
	public String convertToDayOfWeek(String date) {
		String[] dateArr = date.split("-");
		LocalDate calcDay =  LocalDate.of(
				Integer.parseInt(dateArr[0]),
				Integer.parseInt(dateArr[1]), 
				Integer.parseInt(dateArr[2]));		
		return calcDay.getDayOfWeek().toString().substring(0, 2);
	}
}