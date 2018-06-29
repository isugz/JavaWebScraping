/**
 * 
 */
package com.isugz.JavaWebScraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	public Document document;
	
	/**
	 * Constructor takes a Document to scrape.
	 * @param htmlToScrape
	 */
	public Scraper(Document htmlToScrape) {
		this.document = htmlToScrape;
	}

	/**
	 * Method scrapes a Document for a set of data.
	 * @return: JSON formatted text of the scraped data.
	 * TODO look at making a request object that holds the list of data to scrape from a document
	 * 		and the html id's, tags, & classes to use
	 */
	public Object scrape(String[] requestedData, String contentId, String contentTag) {
//		JsonObject jsonString = new JsonObject();
		StringBuilder jsonString = new StringBuilder();
		Element resultContent = this.document.getElementById(contentId);
		Elements resultListItems = resultContent.getElementsByTag(contentTag);
		for(Element item : resultListItems) {
			// iterate through the requestData, adding to a JSON formatted string
			for(String request : requestedData) {
			}
		}
		/**
		 * traverse the html
		 * pull data that matches requestedData elements
		 * 
		 * 
		 * we are looking for the <ul>
		 * we want to loop through the <li> below
		 * 
		 * 
		 * for each result in html
		 * 		get requested info
		 * return info-->text?
		 */
		return null;
	}
	
	/**
	 * Find real estate web pages
	 * Scrape for data
	 * 
	 */
	
}
