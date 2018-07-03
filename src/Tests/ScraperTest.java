/**
 * 
 */
package Tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.isugz.JavaWebScraper.Scraper;
import com.isugz.ScraperTools.Webpage;

/**
 * @author Ivy Sugars
 *
 */
public class ScraperTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String baseUrl = "https://seattle.craigslist.org/d/housing/search/hhh";
		Webpage page = new Webpage(baseUrl);
		Map<String, String> searchParams = new HashMap<>();
		searchParams.put("postal", "98144");
		searchParams.put("max_price", "3000");
		searchParams.put("min_bedrooms", "3");
		searchParams.put("min_bathrooms", "1");
		Connection connection = page.getJsoupConnection(baseUrl, searchParams);
		Document doc = page.getDocument(connection);
		Scraper scraper = new Scraper(doc);
		String[] requestedData = {"housing", "result-price", "result-hood"};
		String contentId = "sortable-results";
		String contentClass = "result-meta";
		scraper.scrape(requestedData, contentId, contentClass);
//		fail("Not yet implemented");
	}

}
