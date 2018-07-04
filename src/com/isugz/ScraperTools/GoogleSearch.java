/**
 * 
 */
package com.isugz.ScraperTools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
	
	public static final String SEARCH_URL = "www.google.com/search";
	public static final String NUMBER_OF_RESULTS = "5";
	String[] listOfUrls = new String[Integer.parseInt(GoogleSearch.NUMBER_OF_RESULTS)];
	public URL url;
	public String[] searchTerms;
	
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
		query.append(GoogleSearch.NUMBER_OF_RESULTS);
		this.url = new URL(query.toString());
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
		int counter = 0;
		for(Element item: resultUrls) {
			this.listOfUrls[counter] = item.getElementsByTag("a").attr("href");
			System.out.println(this.listOfUrls[counter]);
			counter++;
		}
	}

}
