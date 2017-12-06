import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spothero.rest.*;

public class RestServiceUnitTest {

	String 	startTime, 
			endTime, 
			rateScheduleJSON;
	
	RateService rateService;
	RateRequest rateRequest;
	RateSchedule rateSchedule;

	@Before
	public void setUp() throws Exception {	
		rateService = new RateService();
		
		rateRequest = new RateRequest();
		rateRequest.setDay("TH");
		rateRequest.setStartTime(100000);
		rateRequest.setEndTime(160000);
		
		Rate[] rates = new Rate[3];
		rates[0] = new Rate();
		rates[1] = new Rate();
		rates[2] = new Rate();
		
		rates[0].setDays("mon,tues,wed");
		rates[0].setTimes("0900-2100");
		rates[0].setPrice(1500);
		
		rates[1].setDays("thur,fri");
		rates[1].setTimes("0900-2100");
		rates[1].setPrice(2000);
		
		rates[2].setDays("sat,sun");
		rates[2].setTimes("0600-1600");
		rates[2].setPrice(2500);
		
		rateSchedule = new RateSchedule();
		
		rateSchedule.setRates(rates);
		
		startTime = "2017-11-30T10:00:00Z";
		endTime = "2017-11-30T16:00:00Z";
		
		rateScheduleJSON = "{\"rates\":[{\"days\":\"mon,tues,wed\",\"times\":\"0900-2100\",\"price\":1500},{\"days\":\"thurs,fri\",\"times\":\"0900-2100\",\"price\":2000},{\"days\":\"sat,sun\",\"times\":\"0600-1600\",\"price\":2500}]}";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindRate() {
		assertEquals(rateService.findRate(rateSchedule, rateRequest).getRate(),"2000");
		
		RateRequest badRequest = new RateRequest();
		badRequest.setDay("TH");
		badRequest.setStartTime(100000);
		badRequest.setEndTime(230000);
		
		assertEquals(rateService.findRate(rateSchedule, badRequest).getRate(),"Unavailable");
	}

	@Test
	public void testBuildRateRequest() {		
		RateRequest rq2 = rateService.buildRateRequest(startTime, endTime);
		
		assertEquals(rq2.getDay(), rateRequest.getDay());
		assertEquals(rq2.getStartTime(), rateRequest.getStartTime());
		assertEquals(rq2.getEndTime(), rateRequest.getEndTime());
		assertNull(rateService.buildRateRequest("2017-11-29T10:00:00Z", "2017-11-30T10:00:00Z"));
		assertNull(rateService.buildRateRequest(endTime,startTime));
	}

	@Test
	public void testConvertTime() {
		assertEquals(rateService.convertTime("12:00:00Z"), 120000);
	}

	@Test
	public void testConvertToDayOfWeek() {
		assertEquals(rateService.convertToDayOfWeek("2017-11-30"), "TH");
	}
}
