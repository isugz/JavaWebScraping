/**
 * 
 */
package com.isugz.ScraperTools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Ivy Sugars
 * 
 * TODO: implement redirects to find correct search urls
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
	 * Get connection to base url
	 * Get html document
	 * Find key attribute "name=" in <form> that include key words
	 * Keywords: "max" and ("price" or "rent")
	 * 			 "min" and "bedroom"
	 * 			 "min" and "bathroom"
	 * 			 "unit" and "type"
	 * 			 "pet" or "dog"
	 */
	
	
	/**
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	public Connection getJsoupConnection(String url, Map<String, String> data) {
		Connection connection;
		if(!(data == null || data.isEmpty()))
			connection = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).data(data);
		else 
			connection = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true);
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
	
	/**
	 * Public method to find keywords for search query based on common terms used in real estate forms.
	 * @param document: Document containing html from a base url.
	 * @return: Arraylist with the available names of keys for search queries.
	 */
	public ArrayList<String> findFormDataKeywords(Document document) {
		String[] keys = {"price", "bedroom", "bathroom", "type", "pet", "unit", "dog"};
		ArrayList<Elements> nameList = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		for(String key: keys) {
			nameList.add(document.getElementsByAttributeValueContaining("name", key));
			for(Elements item: nameList) {
				names.add(item.attr("name"));
			}
		}
		return names;
	}
	
}
 