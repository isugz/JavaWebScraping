/**
 * 
 */
package com.isugz.ScraperTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
	private Boolean redirect;
	
	// not parameters
	private Document document = null;
	private Map<String, String> data = new HashMap<>(); 
	private Map<String, String> temporaryData = new HashMap<>();
	
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
	public Map<String, String> getData(){
		return this.data;
	}
	
	/**
	 * Constructor for constructing a Webpage object with or without search parameters.
	 * @param builder: Builder object with all required and optional parameters set.
	 */
	private Webpage(WebpageBuilder builder) {
		this.url = builder.url;
		this.maxPrice = builder.maxPrice;
		this.minPrice = builder.minPrice;
		this.maxBed = builder.maxBed;
		this.minBed = builder.minBed;
		this.maxBath = builder.maxBath;
		this.minBath = builder.minBath;
		this.petDog = builder.petDog;
		this.petCat = builder.petCat;
		this.redirect = builder.redirect;
		this.setTemporaryData();
	}
	
	/**
	 * Private method to set a hashmap to possible search parameter key, value pairs based on parameters given during construction.
	 */
	private void setTemporaryData() {
		// construct temp hash map of search params; will include nulls
		this.temporaryData.put("max price", this.maxPrice);
		this.temporaryData.put("max rent", this.maxPrice);
		this.temporaryData.put("min price", this.minPrice);
		this.temporaryData.put("min rent", this.minPrice);
		this.temporaryData.put("max bed", this.maxBed);
		this.temporaryData.put("min bed", this.minBed);
		this.temporaryData.put("max bath", this.maxBath);
		this.temporaryData.put("min bath", this.minBath);
		this.temporaryData.put("pet dog", this.petDog);
		this.temporaryData.put("pet cat", this.petCat);
		//TODO handle the case of pets differently
//		String isPetAllowed;
//		if((this.petCat == null && this.petDog == null) || 
//		   (this.petCat.isEmpty() && this.petDog.isEmpty()))
//			isPetAllowed = "0";
//		else
//			isPetAllowed = "1";
//		this.temporaryData.put("pet", isPetAllowed);
//		System.out.println(this.temporaryData.toString());
		// need to delete nulls
		this.temporaryData.values().removeIf(Objects::isNull);
//		System.out.println(this.temporaryData.toString());
	}
	
	
	/**
	 * Public method gets an HTTP connection from Jsoup using a specified URL.
	 * @return: Connection created from URL with any search parameters applied.
	 * @throws IOException 
	 */
	public Connection getJsoupConnection() throws IOException {
		Connection connection;
		Connection baseConnection;
		if(this.redirect == true) {
			baseConnection = Jsoup.connect(this.url).ignoreContentType(true).ignoreHttpErrors(true);
			this.getDocument(baseConnection);
			connection = this.getRedirect();
		}
		else 
			connection = Jsoup.connect(this.url).ignoreContentType(true).ignoreHttpErrors(true);
		return connection;
				
	}
	
	/**
	 * Public method to get Jsoup connection with search parameters.
	 * @return: Jsoup connection with valid search parameters.
	 * @throws IOException 
	 */
	public Connection getRedirect() throws IOException {
		Connection connection;
		ArrayList<String> availableKeywords = (ArrayList)this.findFormDataKeywords();
//		System.out.println("available keywords: " + availableKeywords);
		this.setData(availableKeywords);
		connection = Jsoup.connect(this.url).ignoreContentType(true).ignoreHttpErrors(true).data(this.data);
//		System.out.println(connection.get().baseUri());
		return connection;
	}
	
	/**
	 * Public method to find keywords for search query based on common terms used in real estate forms.
	 * @return: Arraylist with the available names of keys for search queries.
	 */
	public List<String> findFormDataKeywords() {
		String[] keys = {"price", "rent", "bedroom", "bathroom", "type", "pet", "unit", "dog", "cat"};
		ArrayList<String> nameList = new ArrayList<>();
		for(String key: keys) {
//			System.out.println("key " + key.getClass());
			Elements namesFound = (this.document.getElementsByAttributeValueContaining("name", key));
//			System.out.println("names found" + namesFound);
			for(Element name: namesFound) {
				nameList.add(name.attr("name"));
			}
		}
		List<String> noDuplicatesNameList = nameList.stream().distinct().collect(Collectors.toList());
//		System.out.println("available names" + noDuplicatesNameList);
		return noDuplicatesNameList;
	}	
	
	/**
	 * Private method used to set the hashMap data, containing the final key, value pairs for valid search parameters.
	 * @param availableKeywords: ArrayList containing all valid keywords for search parameters.
	 */
	private void setData(ArrayList<String> availableKeywords) {
	// TODO: make this method more efficient and handle edge cases (pets, unit type, etc.)
		for(Map.Entry<String, String> entry: this.temporaryData.entrySet()) {
			String[] keyDescription = entry.getKey().toString().split(" ");
			for(String item: availableKeywords) {
				if(item.contains(keyDescription[0]) && item.contains(keyDescription[1]))
					this.data.put(item.toString(), entry.getValue().toString());
			}
		}
//		System.out.println(this.data);

	}
	
	/**
	 * Public method to retrieve document from given URL.
	 * @param connection: Jsoup connection to retrieve an HTML document from.
	 * @return: Document (HTML) retrieved from URL.
	 */
	public Document getDocument(Connection connection) {
		try {
			this.document = connection.get();
			this.url = this.document.baseUri();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
		
	/**
	 * Builder Class for Webpage class.
	 * @author Ivy Sugars
	 *
	 */
	public static class WebpageBuilder{
		// required parameters
		private String url; 
		
		// optional parameters
		private String maxPrice, minPrice;
		private String maxBed, minBed;
		private String maxBath, minBath;
		private String petDog, petCat;
		// this should be set to true if any search parameters are set.
		private Boolean redirect = false;
		
		/**
		 * Constructor sets required parameters.
		 * @param url: String representing a base URL for a Webpage.
		 */
		public WebpageBuilder(String url) {
			this.url = url;
		}
		
		/**
		 * Public method to set the maximum price.
		 * @param price: String value of the maximum price.
		 * @return: WebpageBuilder with set parameters.
		 */
		public WebpageBuilder setMaxPrice(String price) {
			this.maxPrice = price;
			return this;
		}
		
		/**
		 * Public method to set the minimum price.
		 * @param price: String value of the minimum price.
		 * @return: WebpageBuilder with set parameters.
		 */
		public WebpageBuilder setMinPrice(String price) {
			this.minPrice = price;
			return this;
		}
		
		/**
		 * Public method to set the maximum number of bedrooms.
		 * @param beds: String value of the maximum number of bedrooms.
		 * @return: WebpageBuilder with set parameters.
		 */
		public WebpageBuilder setMaxBed(String beds) {
			this.maxBed = beds;
			return this;
		}
		
		/**
		 * Public method to set the minimum number of bedrooms.
		 * @param beds: String value of the minimum number of bedrooms.
		 * @return: WebpageBuilder with set parameters.
		 */
		public WebpageBuilder setMinBed(String beds) {
			this.minBed = beds;
			return this;
		}
		
		/**
		 * Public method to set the maximum number of bathrooms.
		 * @param baths: String value of the maximum number of bathrooms.
		 * @return: WebpageBuilder with set parameters.
		 */
		public WebpageBuilder setMaxBath(String baths) {
			this.maxBath = baths;
			return this;
		}
		
		/**
		 * Public method to set the minimum number of bathrooms.
		 * @param baths: String value of the minimum number of bathrooms.
		 * @return: WebpageBuilder with set parameters.
		 */
		public WebpageBuilder setMinBath(String baths) {
			this.minBath = baths;
			return this;
		}
		
		/**
		 * Public method to set if dogs are allowed in the property.
		 * @param dogs: String value; "1" is allowed and "0" is not allowed.
		 * @return: WebpageBuilder with set parameters.
		 */
		public WebpageBuilder setPetDogs(String dogs) {
			this.petDog = dogs;
			return this;
		}
		
		/**
		 * Public method to set if cats are allowed in the property.
		 * @param cats: String value; "1" is allowed and "0" is not allowed.
		 * @return: WebpageBuilder with set parameters.
		 */
		public WebpageBuilder setPetCats(String cats) {
			this.petCat = cats;
			return this;
		}
		
		/**
		 * Public method to set whether a redirect is required.
		 * @param bool: Boolean value is false unless search parameters are set.
		 * @return: WebpageBuilder with set parameters.
		 */
		public WebpageBuilder setRedirect(Boolean bool) {
			this.redirect = bool;
			return this;
		}
		
		/**
		 * Public method builds a Webpage with all required and optional parameters set.
		 * @return: Webpage with all parameter set.
		 */
		public Webpage build() {
			return new Webpage(this);
		}
	}
	
		
	
}
 