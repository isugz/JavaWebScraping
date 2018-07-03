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
 * 
 */
public class GoogleSearch {
	
	public static final String SEARCH_URL = "www.google.com/search";
	public static final String NUMBER_OF_RESULTS = "5";
	String[] listOfUrls = new String[Integer.parseInt(GoogleSearch.NUMBER_OF_RESULTS)];
	public URL url;
	public String[] searchTerms;
	
	/**
	 * 
	 * @param keywords
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
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public URL constructSearchURL() throws MalformedURLException {
		StringBuilder query = new StringBuilder();
		query.append("?q=" + this.searchTerms[0]);
		for(int i = 1; i < this.searchTerms.length; i++) {
			query.append("+");
			query.append(this.searchTerms[i]);
		}
		query.append(GoogleSearch.NUMBER_OF_RESULTS);
		this.url = new URL(query.toString());
		return this.url;
	}
	
	/**
	 * 
	 * @return
	 */
	private Connection getJsoupConnection() {
		Connection connection;
		connection = Jsoup.connect(this.url.toString()).ignoreContentType(true).ignoreHttpErrors(true);
		return connection;
	}
	
	/**
	 * 
	 * @param connection
	 * @return
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
	 * 
	 * @return
	 */
	public String[] getUrls() {
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
		return this.listOfUrls;
	}

}
