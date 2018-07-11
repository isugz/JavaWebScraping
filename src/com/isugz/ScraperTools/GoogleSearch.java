/**
 * 
 */
package com.isugz.ScraperTools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Ivy Sugars
 * Class that handles creating a google search from a set of keywords, that yields a list of resulting URLs.
 */
public class GoogleSearch {
	
	private static final String SEARCH_URL = "https://www.google.com/search";
	private static final String NUMBER_OF_RESULTS = "5";
	private ArrayList<String> listOfUrls = new ArrayList<>();
	private URL url;
	private String[] searchTerms;
	
	/**
	 * Constructor creates a google search from keywords, initializing the resulting list of URLs.
	 * @param keywords: String array whose elements represent the search terms to use for a google search.
	 */
	public GoogleSearch(String[] keywords) {
		this.searchTerms = keywords;
		try {
			this.url = new URL(SEARCH_URL);
			this.constructSearchURL();
			this.getUrls();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Private method to construct a search URL.
	 * @throws MalformedURLException
	 */
	private void constructSearchURL() throws MalformedURLException {
		StringBuilder query = new StringBuilder();
		query.append("?q=" + this.searchTerms[0]);
		for(int i = 1; i < this.searchTerms.length; i++) {
			query.append("+");
			query.append(this.searchTerms[i]);
		}
		query.append("&num=" + GoogleSearch.NUMBER_OF_RESULTS);
		this.url = new URL(GoogleSearch.SEARCH_URL + query.toString());
	}
	
	/**
	 * Private method to get a Jsoup connection from GoogleSearch url.
	 * @return: The Jsoup connection.
	 */
	private Connection getJsoupConnection() {
		Connection connection;
		connection = Jsoup.connect(this.url.toString()).ignoreContentType(true).ignoreHttpErrors(true);
		return connection;
	}
	
	/**
	 * Private method to get an HTML document from a Jsoup connection.
	 * @param connection: The Jsoup connection created from a given URL.
	 * @return: Document with the parsed HTML.
	 */
	private Document getDocument(Connection connection) {
		Document document = null;
		try {
			document = connection.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * Private method to retrieve the URLs from a google search.
	 */
	private void getUrls() {
		Connection connection;
		Document document;
		connection = this.getJsoupConnection();
		document = this.getDocument(connection);
		Elements resultUrls = document.select("h3.r > a");
		
		for(Element item: resultUrls) {
			this.listOfUrls.add(item.getElementsByTag("a").attr("href"));
		}
	}
	
	/**
	 * Public method gets the complete google search URL.
	 * @return: String representing the google search URL.
	 */
	public String getSearchUrl() {
		return this.url.toString();
	}
	
	/**
	 * Public method gets the number of results included in a google search.
	 * @return: String representing the number of results returned for a google search.
	 */
	public String getNumberOfResults() {
		return GoogleSearch.NUMBER_OF_RESULTS;
	}
	
	/**
	 * Public method gets the list of resulting URLs for a google search.
	 * @return: ArrayList whose elements are String representations of URLs results from a google search.
	 */
	public ArrayList<String> getListOfUrls(){
		return this.listOfUrls;
	}

}
