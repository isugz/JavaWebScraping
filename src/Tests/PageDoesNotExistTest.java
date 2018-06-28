package Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.isugz.JavaWebScraper.Scraper;
import com.isugz.ScraperTools.PageDoesNotExist;

public class PageDoesNotExistTest {

	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Testing that if the page doesn't exist, an error is raised.
	 */
	@Test(expected = PageDoesNotExist.class)
	public void testExceptionIsRaisedOnInvalidWebpage() {
		Scraper mothScraper = new Scraper();
		// Make Webpage
		// give it non-existent headers
		String[] data = {"city", "state", "type"};
		Object testObject = mothScraper.scrape(data);
	}
	
	/**
	 * Testing that if the page exists, an error is not raised.
	 * @throws Exception
	 */
	@Test
	public void testExceptionNotRaisedOnValidWebpage() throws PageDoesNotExist{
		Scraper mothScraper = new Scraper();
		// Make webpage
		// Give it existing headers
		String[] data = {"city", "state", "type"};
		Object testObject = mothScraper.scrape(data);
	}

}
