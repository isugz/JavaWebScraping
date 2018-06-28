package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.isugz.ScraperTools.Webpage;

public class WebPageTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void whenWebpageCreatedUrlObjectCorrectTest() {
		String baseUrl = "https://seattle.craigslist.org";
		Webpage page = new Webpage(baseUrl);
		assertEquals(page.urlObject.toString(), baseUrl);
	}
	
	@Test
	public void whenHeadersGivengetConnectionReturnsCorrectUrlTest() {
		// https://seattle.craigslist.org/search/hhh?postal=98144&max_price=3000&min_bedrooms=3&min_bathrooms=1&availabilityMode=0&sale_date=all+dates
		String baseUrl = "https://seattle.craigslist.org";
		// TODO make hashmap
		String[] searchParams = {"postal", "max_price", "min_bedrooms", "min_bathrooms"};
		String correctUrl = "https://seattle.craigslist.org/search/hhh?postal=98144&max_price=3000&min_bedrooms=3&min_bathrooms=1&availabilityMode=0&sale_date=all+dates";
		Webpage page = new Webpage(baseUrl);
//		String url = page.getConnectionUrl(searchParams);
//		assertEquals(url, correctUrl);
		
	}

}
