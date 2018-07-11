/**
 * 
 */
package com.isugz.JavaWebScraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.isugz.ScraperTools.GoogleSearch;
import com.isugz.ScraperTools.Webpage;

/**
 * @author Ivy Sugars
 * I want to find rental properties posted recently on the web that fit my search criteria
 * 
 * Search Criteria:
 * 	homeType = apt, condo, townhouse, singleFamilyHome
 * 	location = neighborhoods(UniversityDistrict to BeaconHill/Mt Baker)
 * 	numberOfBedrooms = 3+
 * 	numberOfBathrooms = 1+
 * 	maximumPrice = $3000
 * 	posted = range(16 days)
 */

public class Scraper {
	private Webpage page;
	private Map<String, String> dataIdentifiers = new HashMap<>();
	public Document document;
	public static final String[] KEYWORDS = {"Seattle", "rental"};
	
	public Scraper(Webpage page) {
		this.page = page;
		this.document = page.getDocument();
	}

	/**
	 * Method scrapes a Document for a set of data.
	 * @return: A string with scraped data.
	 */
	public String scrape() {
		this.setTemporaryIdentifiers();
		/**
		 * all classes whose text contains '$'
		 * need patterns for:
		 * -beds
		 * -baths
		 * -pets
		 * -result link or property link
		 */

		return null;
	}
	
	/**
	 * Private method used to set temporary identifiers based on selected options for a Webpage
	 */
	private void setTemporaryIdentifiers() {
		for(Map.Entry<String, String> entry: this.page.getData().entrySet()) {
			this.dataIdentifiers.put(entry.getKey(), "");
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		// Search keywords
		GoogleSearch search = new GoogleSearch(KEYWORDS);
		ArrayList<String> urlsToScrape = search.getListOfUrls();
		System.out.println(urlsToScrape.get(3));
		Webpage page = new Webpage.WebpageBuilder(urlsToScrape.get(3)).setMaxPrice("3000")
																	  .setMinBed("4")
																	  .setMinBath("2")
																	  .setRedirect(true)
																	  .build();
		Connection connection = page.getJsoupConnection();
		page.getDocument(connection);
		System.out.println(page.getUrl());
		System.out.println(page.getData());
		// Scrape data
		Scraper rentalScraper = new Scraper(page);
		rentalScraper.scrape();
	}
}
