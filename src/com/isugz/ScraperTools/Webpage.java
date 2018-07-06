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
	// required parameters
	private String url; 
	
	// optional parameters
	private String maxPrice, minPrice;
	private String maxBed, minBed;
	private String maxBath, minBath;
	private String petDog, petCat;
	
	// not parameters
	private Document document = null;
	private Map<String, String> data = new HashMap<>(); 
	
	
	/**
	 * Public method gets the value of the Webpage's URL.
	 * @return: String representing the URL of the Webpage object.
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * Public method gets the value of the maxPrice search parameter.
	 * @return: String representing the maximum price contained in the Webpage search parameters.
	 */
	public String getMaxPrice() {
		return this.maxPrice;
	}
	
	/**
	 * Public method gets the value of the minPrice search parameter.
	 * @return: String representing the minimum price contained in the Webpage search parameters.
	 */
	public String getMinPrice() {
		return this.minPrice;
	}
	
	/**
	 * Public method gets the value of the maxBed search parameter.
	 * @return: String representing the maximum bedrooms contained in the Webpage search parameters.
	 */
	public String getMaxBed() {
		return this.maxBed;
	}
	
	/**
	 * Public method gets the value of the minBed search parameter.
	 * @return: String representing the minimum bedrooms contained in the Webpage search parameters.
	 */
	public String getMinBed() {
		return this.minBed;
	}
	
	/**
	 * Public method gets the value of the maxBath search parameter.
	 * @return: String representing the maximum bathrooms contained in the Webpage search parameters.
	 */
	public String getMaxBath() {
		return this.maxBath;
	}
	
	/**
	 * Public method gets the value of the minBath search parameter.
	 * @return: String representing the minimum bathrooms contained in the Webpage search parameters.
	 */
	public String getMinBath() {
		return this.minBath;
	}
	
	/**
	 * Public bathroom gets the value of the petDog search parameter.
	 * @return: String representing the value of dogs in the Webpage search parameters.
	 * Example: petDog = "1" i.e. dogs are allowed at this rental.
	 * 			petDog = "0" i.e. dogs are not allowed at this rental.
	 */
	public String getPetDog() {
		return this.petDog;
	}
	
	/**
	 * Public bathroom gets the value of the petCat search parameter.
	 * @return: String representing the value of cats in the Webpage search parameters.
	 * Example: petCat = "1" i.e. cats are allowed at this rental.
	 * 			petCat = "0" i.e. cats are not allowed at this rental.
	 */
	public String getPetCat() {
		return this.petCat;
	}
	
	/**
	 * Public method gets the values of the search parameters contained in data.
	 * @return: String representing the key, value pairs contained in data. Null if there are no search parameters.
	 */
	public String getData(){
		return this.data.toString();
	}
	
	/**
	 * Constructor for Webpage object takes a given URL as a String and HashMap containing header information.
	 * @param givenURL: String representing a base URL.
	 * @param data: HashMap containing header information.
	 * TODO: use a builder design pattern for this
	 */
	public Webpage(String givenURL, Map<String, String> data) {
		this.data = data;
		this.url = givenURL;
		try {
			new URL(url);
		}
		catch(MalformedURLException e) {
			System.out.print("The URL was malformed: ");
			e.printStackTrace();
		}
	}
	
	private Webpage(WebpageBuilder builder) {
		/**
		 * private String maxPrice, minPrice;
	private String maxBed, minBed;
	private String maxBath, minBath;
	private String petDog, petCat;
		 */
		this.url = builder.url;
		this.maxPrice = builder.maxPrice;
		this.minPrice = builder.minPrice;
		this.maxBed = builder.maxBed;
		this.minBed = builder.minBed;
		this.maxBath = builder.maxBath;
		this.minBath = builder.minBath;
		this.petDog = builder.petDog;
		this.petCat = builder.petCat;
	}
	
	// Builder Class
	public static class WebpageBuilder{
		// required parameters
		private String url; 
		
		// optional parameters
		private String maxPrice, minPrice;
		private String maxBed, minBed;
		private String maxBath, minBath;
		private String petDog, petCat;
		
		/**
		 * 
		 * @param url
		 */
		public WebpageBuilder(String url) {
			this.url = url;
		}
		
		
		public WebpageBuilder setMaxPrice(String price) {
			this.maxPrice = price;
			return this;
		}
		
		
		public WebpageBuilder setMinPrice(String price) {
			this.minPrice = price;
			return this;
		}
		
		
		public WebpageBuilder setMaxBed(String beds) {
			this.maxBed = beds;
			return this;
		}
		
		
		public WebpageBuilder setMinBed(String beds) {
			this.minBed = beds;
			return this;
		}
		
		
		public WebpageBuilder setMaxBath(String baths) {
			this.maxBath = baths;
			return this;
		}
		
		
		public WebpageBuilder setMinBath(String baths) {
			this.minBath = baths;
			return this;
		}
		
		
		public WebpageBuilder setPetDogs(String dogs) {
			this.petDog = dogs;
			return this;
		}
		
		
		public WebpageBuilder setPetCats(String cats) {
			this.petCat = cats;
			return this;
		}
		
		
		public Webpage build() {
			return new Webpage(this);
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
			connection = this.getRedirect();
		else 
			connection = Jsoup.connect(this.url).ignoreContentType(true).ignoreHttpErrors(true);
		 
		return connection;
				
	}
	
	/**
	 * Public method to get Jsoup connection with search parameters.
	 * @return: Jsoup connection with valid search parameters.
	 */
	public Connection getRedirect() {
		ArrayList<String> availableKeywords = (ArrayList)this.findFormDataKeywords();
//		System.out.println("available keywords: " + availableKeywords);
		Map<String, String> newData = new HashMap<>();
		// TODO: make this method more efficient
		for(Map.Entry<String, String> entry: data.entrySet()) {
			String[] keyDescription = entry.getKey().toString().split(" ");
			for(String item: availableKeywords) {
				if(item.contains(keyDescription[0]) && item.contains(keyDescription[1]))
					newData.put(item.toString(), entry.getValue().toString());
			}
		}
//		System.out.println(newData);
		
		return Jsoup.connect(this.url).ignoreContentType(true).ignoreHttpErrors(true).data(newData);
	}
	
	/**
	 * Public method to find keywords for search query based on common terms used in real estate forms.
	 * @return: Arraylist with the available names of keys for search queries.
	 */
	public List<String> findFormDataKeywords() {
		String[] keys = {"price", "bedroom", "bathroom", "type", "pet", "unit", "dog", "cat"};
		ArrayList<String> nameList = new ArrayList<>();
		for(String key: keys) {
			Elements namesFound = (this.document.getElementsByAttributeValueContaining("name", key));
			for(Element name: namesFound) {
				nameList.add(name.attr("name"));
			}
		}
		List<String> noDuplicatesNameList = nameList.stream().distinct().collect(Collectors.toList());
//		System.out.println(noDuplicatesNameList);
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
 