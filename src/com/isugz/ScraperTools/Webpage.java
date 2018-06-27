/**
 * 
 */
package com.isugz.ScraperTools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * @author Ivy Sugars
 * creates Webpage object with search criteria
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
 * -Trulia
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
	 * Get the redirect that redirects the page to filtered results
	 * 	params: Array[] of headers
	 * 
	 */
	/**
	 * Constructor for Webpage Object; creates URL object from givenURL.
	 * @param givenURL: String representing a base URL for Webpage.
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
	
	public HttpURLConnection getConnection(String[] searchParams) {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) urlObject.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			DataOutputStream output = new DataOutputStream(connection.getOutputStream());
			HashMap<String, String> parameters = getHeaders(searchParams, connection);
			output.writeBytes(ParameterStringBuilder.getParameterString(parameters));
			output.flush();
			output.close();
			return connection;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public StringBuffer getResponse(HttpURLConnection connection) {
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

	private HashMap<String, String> getHeaders(String[] searchParams, HttpURLConnection connection) {
		HashMap<String, String> parameters = new HashMap<>();
		for(String item : searchParams) {
			parameters.put(item, connection.getHeaderField(item));
		}
		return parameters;
	}
}
 