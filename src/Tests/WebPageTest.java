package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.isugz.ScraperTools.Webpage;

public class WebPageTest {
	String baseUrl = "https://seattle.craigslist.org/search/apa";
	Map<String, String> searchParams = new HashMap<>();
	Webpage page = new Webpage(baseUrl, searchParams);
	
	@Before
	public void setUp() throws Exception {
		searchParams.put("postal", "98144");
		searchParams.put("max_price", "3000");
		searchParams.put("min_bedrooms", "3");
		searchParams.put("min_bathrooms", "1");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void whenWebpageCreatedUrlObjectCorrectTest() {
		assertEquals(page.urlObject.toString(), baseUrl);
	}
	
	
	@Test
	public void getConnectionNoSearchParamsBaseUrlCorrectTest() throws IOException {
		HashMap<String, String> noSearchParams = new HashMap<>();
		Connection connection = page.getJsoupConnection(noSearchParams);
		Document doc = page.getDocument(connection);
		String url = doc.baseUri();
		String expectedUrl = "https://seattle.craigslist.org/search/apa";
//		System.out.println("base url with no params: " + url);
		assertEquals(expectedUrl, url);
	}
	
	
	@Test
	public void getConnectionSearchParamsBaseUrlCorrectTest() throws IOException {
		Connection connection = page.getJsoupConnection(searchParams);
		Document doc = page.getDocument(connection);
		String url = doc.baseUri();
		String expectedUrl = "https://seattle.craigslist.org/search/apa?max_price=3000&min_bedrooms=3&postal=98144&min_bathrooms=1";
//		System.out.println("baseurl with params: " + url);
		assertEquals(expectedUrl, url);
	}
	
	
	@Test
	public void getDocumentContainsCorrectMaximumPriceTest() throws IOException {
		int maxPrice = 3000;
		Connection connection = page.getJsoupConnection(searchParams);
		Document doc = page.getDocument(connection);
		Elements elements = doc.select("span.result-price");
		 for (Element e : elements) {
			 int htmlPrice = Integer.parseInt(e.text().substring(1, e.text().length()));
			 assertTrue(htmlPrice <= maxPrice);
		 }
	}
	
	@Test
	public void getDocumentContainsCorrectMinimumBedroomsTest() {
		int minimumBedrooms = 3;
		Connection connection = page.getJsoupConnection(searchParams);
		Document doc = page.getDocument(connection);
		Elements elements = doc.select("span.housing");
		 for (Element e : elements) {
			 int htmlMinimumBedrooms = Integer.parseInt(e.text().substring(0, 1));
//			 System.out.println("bedrooms: " + htmlMinimumBedrooms);
			 assertTrue(htmlMinimumBedrooms >= minimumBedrooms);
		 }
	}
	
	
	@Test
	public void getFormDataKeyNamesTest() {
		List<String> formData = new ArrayList<>();
		String[] keys = {"min_price", "max_price", "min_bedrooms", "max_bedrooms", "min_bathrooms",
							"max_bedrooms", "pets_cat", "pets_dog"};
		Connection connection = page.getJsoupConnection(null);
		Document doc = page.getDocument(connection);
//		System.out.println(doc.baseUri());
//		System.out.println(doc.getElementsByAttribute("name"));
		formData = page.findFormDataKeywords();
		for(String item: keys) {
//			System.out.println(item);
//			System.out.println(formData.contains(item));
			assertTrue(formData.contains(item));
		}
	}
}
