/**
 * 
 */
package com.isugz.JavaWebScraper;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.isugz.ScraperTools.Webpage;
import com.isugz.ScraperTools.BuildRequestedDataFromHtml;

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
	public BuildRequestedDataFromHtml data;
	
	/**
	 * Constructor takes a Document to scrape.
	 * @param htmlToScrape
	 */
	public Scraper(Document htmlToScrape) {
		this.document = htmlToScrape;
	}

	/**
	 * Method scrapes a Document for a set of data.
	 * @return: A string with scraped data.
	 * TODO look at making a builder method to handle the requested data and handing scrape() that object
	 * 		scrape(scrapeDataBuilder builder)
	 */
	public String scrape(String[] requestedData, String contentId, String contentClass) {
		StringBuilder resultString = new StringBuilder();
		Element resultContent = this.document.getElementById(contentId);
		Elements resultItems = resultContent.getElementsByClass(contentClass);
		for(Element item : resultItems) {
			resultString.append("{\n");
			resultString.append(item.getElementsByClass(requestedData[3]).attr("href"));
			resultString.append(":\n");
			String houseInformation = item.getElementsByClass(requestedData[0]).text();
			String price = item.getElementsByClass(requestedData[1]).text();
			String neighborhood = item.getElementsByClass(requestedData[2]).text();
			System.out.println(houseInformation + "\t" + price + "\t" + neighborhood);
			resultString.append(houseInformation);
			resultString.append(price);
			resultString.append(neighborhood);
			resultString.append("\n}\n");
			
		}
		return resultString.toString();
	}
}
