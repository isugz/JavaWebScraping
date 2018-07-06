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
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Ivy Sugars
 * 
 */
public class Webpage {
	public String url;
	public URL urlObject = null;
	public Document document = null;
	public Map<String, String> data = new HashMap<>();
	
	/**
	 * Constructor for Webpage object takes a given URL as a String and HashMap containing header information.
	 * @param givenURL: String representing a base URL.
	 * @param data: HashMap containing header information.
	 */
	public Webpage(String givenURL, Map<String, String> data) {
		this.data = data;
		this.url = givenURL;
		try {
			urlObject = new URL(url);
		}
		catch(MalformedURLException e) {
			System.out.print("The URL was malformed: ");
			e.printStackTrace();
		}
	}
	
		
	/**
	 * Public method gets an HTTP connection from Jsoup using a specified URL.
	 * @param data: hashmap where keys=search parameters and values=value of search parameter.
	 * @return: Connection created from URL with any search parameters applied.
	 */
	public Connection getJsoupConnection(Map<String, String> data) {
		Connection connection;
		if(!(data == null || data.isEmpty()))
			connection = Jsoup.connect(this.url).ignoreContentType(true).ignoreHttpErrors(true).data(data);
		else 
			connection = Jsoup.connect(this.url).ignoreContentType(true).ignoreHttpErrors(true);
		 
		return connection;
				
	}
	
	/**
	 * TODO: fix this to actually map key correctly in order to construct newData with valid entries
	 */
	public void getRedirect() {
		ArrayList<String> availableKeywords = (ArrayList)this.findFormDataKeywords();
		Map<String, String> newData = new HashMap<>();
		for(Entry entry: data.entrySet()) {
			String[] keyDescription = entry.getKey().toString().split(" ");
			// if keyDescription[0] && keyDescription[1] in availableKeywords.keys
			if(availableKeywords.contains(keyDescription[0]) && availableKeywords.contains(keyDescription[1]));
				newData.put(entry.getKey().toString(), entry.getValue().toString());
		}
		System.out.println(newData);
		// keys should be 2 words: "attribute noun" -->> "max price" or "min bed"
		// should search keys of result=FindFormDataKeywords() for contains(this.data.key.substring)
	}
	
	/**
	 * Public method to find keywords for search query based on common terms used in real estate forms.
	 * @return: Arraylist with the available names of keys for search queries.
	 */
	public List<String> findFormDataKeywords() {
		String[] keys = {"price", "bedroom", "bathroom", "type", "pet", "unit", "dog"};
		ArrayList<String> nameList = new ArrayList<>();
		for(String key: keys) {
			Elements namesFound = (this.document.getElementsByAttributeValueContaining("name", key));
			for(Element name: namesFound) {
				nameList.add(name.attr("name"));
			}
		}
		List<String> noDuplicatesNameList = nameList.stream().distinct().collect(Collectors.toList());
		System.out.println(noDuplicatesNameList);
		return noDuplicatesNameList;
	}	
	
	/**
	 * Public method to retrieve document from given URL.
	 * @param url: String representing final version of request URL.
	 * @return: Document (HTML) retrieved from URL.
	 * @throws UnsupportedEncodingException 
	 */
	public Document getDocument(Connection connection) {
		try {
			this.document = connection.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
}
 