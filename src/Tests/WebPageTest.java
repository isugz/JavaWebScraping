package Tests;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matcher.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

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
	
//	@Test
//	public void whenHeadersGivengetDocumentUrlCorrectTest() {
//		String baseUrl = "https://seattle.craigslist.org/d/housing/search/hhh";
//		HashMap<String, String> searchParams = new HashMap<>();
//		searchParams.put("postal", "98144");
//		searchParams.put("max_price", "3000");
//		searchParams.put("min_bedrooms", "2");
//		searchParams.put("min_bathrooms", "1");
//		String correctUrl = "https://seattle.craigslist.org/search/hhh?max_price=3000&min_bathrooms=1&min_bedrooms=2&postal=98144";
//		Webpage page = new Webpage(baseUrl);
//		Document doc = page.getDocument(baseUrl, searchParams);
//		String url = doc.absUrl(baseUrl);
//		System.out.println("constructed url: " + doc.baseUri() + "\nactual url: " + correctUrl);
////		assertEquals(url, correctUrl);
//		fail("how do I get the full url with jsoup?");
//	}
	
	@Test
	public void whenNoHeadersgetConnectionReturnsCorrectUrlTest() {
		String baseUrl = "https://seattle.craigslist.org/d/housing/search/hhh";
		HashMap<String, String> searchParams = new HashMap<>();
		Webpage page = new Webpage(baseUrl);
		Document doc = page.getDocument(baseUrl, searchParams);
		String url = doc.baseUri();
		assertEquals(url, baseUrl);
	}
	
	// Test that the document returned contains only items that fit within search parameters
	@Test
	public void documentRetrievedContainsCorrectPostalCodesTest() {
		String baseUrl = "https://seattle.craigslist.org/d/housing/search/hhh";
		HashMap<String, String> searchParams = new HashMap<>();
		searchParams.put("postal", "98144");
		searchParams.put("max_price", "3000");
		searchParams.put("min_bedrooms", "2");
		searchParams.put("min_bathrooms", "1");
		Webpage page = new Webpage(baseUrl);
		Document doc = page.getDocument(baseUrl, searchParams);
		int maxPrice = 3000;
		// check all postal codes
		Elements elements = doc.select("p.result-info > span.result-price");
		 for (Element e : elements) {
//		      assertThat(Integer.parseInt(e.text()),lessThan(3000));
			 System.out.println("price: " + e.text());
			 int htmlPrice = Integer.parseInt(e.text());
			 assertTrue(htmlPrice <= maxPrice);
		 }
	}
//	
//	@Test
//	public void documentRetrievedContainsCorrectMaximumPriceTest() {
//		fail("not implemented");
//	}
//	
//	@Test
//	public void documentRetrievedContainsCorrectMinimumBedroomsTest() {
//		fail("not implemented");
//	}
//	
//	@Test void documentRetrievedContainsCorrectMinimumBathroomsTest() {
//		fail("not implemented");
//	}
//	

}
