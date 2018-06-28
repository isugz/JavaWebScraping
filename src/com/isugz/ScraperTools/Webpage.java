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
	
	/**
	 * Method constructs HTTP connection using urlObject.
	 * @param searchParams: String array listing headers to be included with final URL, if any.
	 * @return: String representing the final URL for connection.
	 */
	public String getConnectionUrl(HashMap<String, String> searchParams) {
		URL urlWithHeaders = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
			connection.connect();
			if(!searchParams.isEmpty()) {			
				String parameters = getHeaderString(searchParams, connection);
				connection.disconnect();
				try {
					urlWithHeaders = new URL(url + "/search/hhs?" + parameters);
					HttpURLConnection newConnection = (HttpURLConnection) urlWithHeaders.openConnection();
					newConnection.connect();
					return urlWithHeaders.toString();
				}catch(MalformedURLException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return urlObject.toString();
	}
	
	/**
	 * Private helper method to get headers for URL.
	 * @param searchParams: String array that include all parameters for final URL.
	 * @param connection: Established HTTP connection.
	 * @return: the constructed headers.
	 * TODO this should just return a header string to add as the final part of the URL
	 */
	private String getHeaderString(HashMap<String, String> searchParams, HttpURLConnection connection) {
		StringBuilder resultString = new StringBuilder();
		try {
			for(Map.Entry<String, String> entry : searchParams.entrySet()) {
	          resultString.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
	          resultString.append("=");
	          resultString.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
	          resultString.append("&");
			}
		}catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return resultString.toString();
	}
	
	/**
	 * Public method to retrieve document from given URL.
	 * @param url: String representing final version of request URL.
	 * @return: Document (HTML) retrieved from URL.
	 */
	public Document getDocument(String url) {
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
			// TODO Jsoup.connect is designed for method chaining; address cookies here
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
}
 