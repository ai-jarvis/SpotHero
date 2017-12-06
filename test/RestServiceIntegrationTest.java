import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.Response;
import com.spothero.rest.RateResponse;
import com.spothero.rest.RateService;
import com.spothero.rest.RateResponseError;

public class RestServiceIntegrationTest {

	RateService rateService;
	RateResponse rateResponse;
	Response checkResponse;
	RateResponseError rateResponseError;
	
	String startDateTime;
	String endDateTime;
	String rateSchedule;
	
	@Before
	public void setUp() throws Exception {
		rateService = new RateService();
		
		startDateTime = "2015-07-04T10:00:00Z";
		endDateTime = "2015-07-04T12:00:00Z" ;
		rateSchedule = "{\"rates\":[{\"days\":\"mon,tues,wed\",\"times\":\"0900-2100\",\"price\":1500},{\"days\":\"thurs,fri,wed\",\"times\":\"0900-2100\",\"price\":2000},{\"days\":\"sat,sun\",\"times\":\"0600-1600\",\"price\":2500}]}";
	}

	@Test
	public void testGetRateSuccess() {
		
		rateResponse = new RateResponse();
		rateResponse.setRate("2500");
		
		checkResponse = Response.status(200).entity(rateResponse).build();
		
		Response testResponse = rateService.getRate(startDateTime, endDateTime, rateSchedule);
				
		assertEquals(checkResponse.getStatus(), testResponse.getStatus());
		assertEquals(((RateResponse)checkResponse.getEntity()).getRate(), ((RateResponse)testResponse.getEntity()).getRate());
	}
	
	@Test
	public void testGetRateMissingParameterCheck() {
		
		rateResponse = new RateResponse();
		rateResponse.setRate("2500");
		Response testResponse;
		
		//Check for missing startDateTime
		checkResponse = Response.status(200).entity(rateResponse).build();
		testResponse = rateService.getRate("", endDateTime, rateSchedule);
				
		assertNotEquals(checkResponse.getStatus(), testResponse.getStatus());
		assertNotEquals(((RateResponse)checkResponse.getEntity()).getRate(), ((RateResponseError)testResponse.getEntity()).getErrorMessage());
		
		rateResponseError = new RateResponseError();
		rateResponseError.setErrorMessage("Missing arguement in request");
		
		checkResponse = Response.status(400).entity(rateResponseError).build();
		
		assertEquals(checkResponse.getStatus(), testResponse.getStatus());
		assertNotEquals(((RateResponseError)checkResponse.getEntity()).getErrorMessage(), ((RateResponseError)testResponse.getEntity()).getErrorMessage());
		
		//Check for missing endDateTime
		checkResponse = Response.status(200).entity(rateResponse).build();
		testResponse = rateService.getRate(startDateTime, "", rateSchedule);
		
		assertNotEquals(checkResponse.getStatus(), testResponse.getStatus());
		assertNotEquals(((RateResponse)checkResponse.getEntity()).getRate(), ((RateResponseError)testResponse.getEntity()).getErrorMessage());
		
		rateResponseError = new RateResponseError();
		rateResponseError.setErrorMessage("Missing arguement in request");
		
		checkResponse = Response.status(400).entity(rateResponseError).build();
		
		assertEquals(checkResponse.getStatus(), testResponse.getStatus());
		assertNotEquals(((RateResponseError)checkResponse.getEntity()).getErrorMessage(), ((RateResponseError)testResponse.getEntity()).getErrorMessage());
		
		//Check for missing rateSchedule
		checkResponse = Response.status(200).entity(rateResponse).build();
		testResponse = rateService.getRate(startDateTime, endDateTime, "");
		
		assertNotEquals(checkResponse.getStatus(), testResponse.getStatus());
		assertNotEquals(((RateResponse)checkResponse.getEntity()).getRate(), ((RateResponseError)testResponse.getEntity()).getErrorMessage());
		
		rateResponseError = new RateResponseError();
		rateResponseError.setErrorMessage("Missing arguement in request");
		
		checkResponse = Response.status(400).entity(rateResponseError).build();
		
		assertEquals(checkResponse.getStatus(), testResponse.getStatus());
		assertNotEquals(((RateResponseError)checkResponse.getEntity()).getErrorMessage(), ((RateResponseError)testResponse.getEntity()).getErrorMessage());
	}
	
	@Test
	public void testGetRateMissingBadParameterCheck() {
		
		rateResponse = new RateResponse();
		rateResponse.setRate("2500");
		
		checkResponse = Response.status(200).entity(rateResponse).build();
		
		Response testResponse = rateService.getRate("", endDateTime, rateSchedule);
				
		assertNotEquals(checkResponse.getStatus(), testResponse.getStatus());
		assertNotEquals(((RateResponse)checkResponse.getEntity()).getRate(), ((RateResponseError)testResponse.getEntity()).getErrorMessage());
		
		rateResponseError = new RateResponseError();
		rateResponseError.setErrorMessage("Missing arguement in request");
		
		checkResponse = Response.status(400).entity(rateResponseError).build();
		
		assertEquals(checkResponse.getStatus(), testResponse.getStatus());
		assertNotEquals(((RateResponseError)checkResponse.getEntity()).getErrorMessage(), ((RateResponseError)testResponse.getEntity()).getErrorMessage());
	}
}
