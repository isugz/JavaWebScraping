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
	public String getConnection(String[] searchParams) {
		URL urlWithHeaders = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
			connection.connect();
			if(searchParams.length > 0) {			
				String parameters = getHeaders(searchParams, connection);
				connection.disconnect();
				try {
					urlWithHeaders = new URL(url + parameters);
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
	 * Method reads the response from an HTTP connection.
	 * @param connection: HttpURLConnection that the response comes from.
	 * @return: StringBuffer that holds the response content.
	 */
	public StringBuffer readResponse(HttpURLConnection connection) {
		StringBuffer content = null;
		try {
			int status = connection.getResponseCode();
			BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			content = new StringBuffer();
			while ((inputLine = input.readLine()) != null) {
			    content.append(inputLine);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * Private helper method to get headers for URL.
	 * @param searchParams: String array that include all parameters for final URL.
	 * @param connection: Established HTTP connection.
	 * @return: the constructed headers.
	 * TODO this should just return a header string to add as the final part of the URL
	 */
	private String getHeaders(String[] searchParams, HttpURLConnection connection) {
		HashMap<String, String> headers = new HashMap<>();
		StringBuilder resultString = new StringBuilder();
		for(String item : searchParams) {
			headers.put(item, connection.getHeaderField(item));
		}
		try {
			for(Map.Entry<String, String> entry : headers.entrySet()) {
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
}
 