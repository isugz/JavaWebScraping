package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.isugz.ScraperTools.GoogleSearch;

public class GoogleSearchTest {
	// TODO: implement more tests
	public String[] keywords = {"Seattle", "rental"};
	public String expectedSearchUrl = "https://www.google.com/search?q=Seattle+rental&num=5";
	GoogleSearch search;

	@Before
	public void setUp() throws Exception {
		search = new GoogleSearch(keywords);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void googleSearchCreatedSearchUrlCorrectTest() {
		String actualSearchUrl = search.getSearchUrl(); 
		assertEquals(expectedSearchUrl, actualSearchUrl);
	}
	
	@Test
	public void googleSearchCreatedSetNumberOfUrlsRetrievedTest() {
		search = new GoogleSearch(keywords);
		int expectedNumberResults = Integer.parseInt(search.getNumberOfResults());
		int actualNumberResults = search.getListOfUrls().size();
		assertEquals(expectedNumberResults, actualNumberResults);
	}

}
