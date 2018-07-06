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

//	static String baseUrl = "https://seattle.craigslist.org/d/housing/search/hhh";
//	static Map<String, String> searchParams = new HashMap<>();
//	static Webpage page = new Webpage(baseUrl, searchParams);
//	static Document doc;
//	Scraper scraper = new Scraper(doc);
//	
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		searchParams.put("postal", "98144");
//		searchParams.put("max_price", "3000");
//		searchParams.put("min_bedrooms", "3");
//		searchParams.put("min_bathrooms", "1");
//		Connection connection = page.getJsoupConnection(searchParams);
//		doc = page.getDocument(connection);
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void test() {
//		String[] requestedData = {"housing", "result-price", "result-hood", "result-title hdrlnk"};
//		String contentId = "sortable-results";
//		String contentClass = "result-info";
//		String results = scraper.scrape(requestedData, contentId, contentClass);
//		assertFalse(results.isEmpty());
////		fail("Not yet implemented");
//	}

}
