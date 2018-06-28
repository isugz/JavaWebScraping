/**
 * 
 */
package com.isugz.ScraperTools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Ivy Sugars
 * 
 * Search Criteria:
 * 	homeType = apt, condo, townhouse, singleFamilyHome
 * 	location = neighborhoods(UniversityDistrict to BeaconHill/Mt Baker)
 * 	numberOfBedrooms = 3+
 * 	numberOfBathrooms = 1+
 * 	maximumPrice = $3000
 * 	posted = range(16 days)
 * 
 * --Note-- start with craigslist
 * 
 * Real estate search sites without api's:
 * -Craigslist
 * -Hotpads.com
 * -Rent.com
 * -Homes.com
 * -
 */
public class Webpage {
	public String url;
	public URL urlObject = null;
	
	/**
	 * Constructor for Webpage object takes a given URL as a String and parses it to a URL object.
	 * @param givenURL: String representing a base URL.
	 */
	public Webpage(String givenURL) {
		url = givenURL;
		try {
			urlObject = new URL(url);
		}
		catch(MalformedURLException e) {
			System.out.print("The URL was malformed: ");
			e.printStackTrace();
		}
	}
	
	public Connection getJsoupConnection(String url, Map<String, String> headers) {
		Connection connection = null;
		connection = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true);
		if(!(headers == null || headers.isEmpty())) {
			for(Map.Entry<String, String> header : headers.entrySet()) {
				connection.header(header.getKey(), header.getValue());
			}
			System.out.println(connection.toString());
		}
		return connection;
				
	}
	
	/**
	 * Public method to retrieve document from given URL.
	 * @param url: String representing final version of request URL.
	 * @return: Document (HTML) retrieved from URL.
	 * @throws UnsupportedEncodingException 
	 */
	public Document getDocument(Connection connection) {
		Document document = null;
		try {
			document = connection.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
}
 