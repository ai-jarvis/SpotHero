import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spothero.rest.Metrics;

public class MetricsUnitTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		Metrics.clearMetrics();
	}
	
	@Test
	public void validateSingleton() {
		Metrics.clearMetrics();
		Metrics.getMetrics().setNumRequests(100);
		assertEquals(Metrics.getMetrics().getNumRequests(), 100);
	}

	@Test
	public void testAddResponseTime() {
		Metrics.clearMetrics();
		Metrics.getMetrics().addResponseTime(10);
		assertEquals(Metrics.getMetrics().getAvgResponseTime(), 10.0, 10);
	}
}
