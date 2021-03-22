package com.mrsisa.mrsisaprojekat.model;

import java.util.HashMap;

public class Pricelist {
	
	private HashMap<Long,PricelistItem> prices;
	
	public Pricelist() {}

	public HashMap<Long, PricelistItem> getPrices() {
		return prices;
	}

	public void setPrices(HashMap<Long, PricelistItem> prices) {
		this.prices = prices;
	}
	
	
	

}
