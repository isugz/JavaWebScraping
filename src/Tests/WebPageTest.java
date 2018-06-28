package Tests;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matcher.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
	public void documentRetrievedBaseUrlCorrectTest() throws IOException {
		String baseUrl = "https://seattle.craigslist.org/d/housing/search/hhh";
		HashMap<String, String> searchParams = new HashMap<>();
		Webpage page = new Webpage(baseUrl);
		Connection connection = page.getJsoupConnection(baseUrl, searchParams);
		Document doc = page.getDocument(connection);
		String url = doc.baseUri();
		assertEquals(url, baseUrl);
	}
	
//	@Test
//	public void documentRetrievedContainsCorrectPostalCodesTest() {
//		String baseUrl = "https://seattle.craigslist.org/d/housing/search/hhh";
//		HashMap<String, String> searchParams = new HashMap<>();
//		searchParams.put("postal", "98144");
//		searchParams.put("search_distance", "1");
//		searchParams.put("max_price", "3000");
//		searchParams.put("min_bedrooms", "2");
//		searchParams.put("min_bathrooms", "1");
//		Webpage page = new Webpage(baseUrl);
//		Document doc = page.getDocument(baseUrl, searchParams);
//		int maxPrice = 3000;
//		Elements elements = doc.select("p.result-info > span.result-price");
//		 for (Element e : elements) {
//			 System.out.println("price: " + e.text());
//			 int htmlPrice = Integer.parseInt(e.text());
//			 assertTrue(htmlPrice <= maxPrice);
//		 }
//	}
	
	@Test
	public void documentRetrievedContainsCorrectMaximumPriceTest() throws IOException {
		String baseUrl = "https://seattle.craigslist.org/d/housing/search/hhh";
		// https://seattle.craigslist.org/d/housing/search/hhh?max_price=3000&min_bedrooms=2&postal=98144&search_distance=1&min_bathrooms=1
		Map<String, String> searchParams = new HashMap<>();
		searchParams.put("max_price", "3000");
		Webpage page = new Webpage(baseUrl);
		Connection connection = page.getJsoupConnection(baseUrl, searchParams);
		Document doc = page.getDocument(connection);
		int maxPrice = 3000;
		Elements elements = doc.select("span.result-price");
		System.out.println(doc.baseUri());
		System.out.println("elements returned:" + elements.text().length());
		 for (Element e : elements) {
			 System.out.println("price is: " + e.text().substring(1, e.text().length()));
			 int htmlPrice = Integer.parseInt(e.text().substring(1, e.text().length()));
			 assertTrue(htmlPrice <= maxPrice);
		 }
	}
	
	@Test
	public void documentRetrievedContainsCorrectMinimumBedroomsTest() {
		fail("not implemented");
	}
	
	@Test 
	public void documentRetrievedContainsCorrectMinimumBathroomsTest() {
		fail("not implemented");
	}
	

}
