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
	public void givenBaseUrlWhenWebpageCreatedUrlObjectCorrectTest() {
		String baseUrl = "https://seattle.craigslist.org";
		Webpage page = new Webpage(baseUrl);
		assertEquals(page.urlObject.toString(), "https://seattle.craigslist.org");
	}

}
