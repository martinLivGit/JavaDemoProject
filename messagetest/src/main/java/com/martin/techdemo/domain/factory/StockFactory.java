package com.martin.techdemo.domain.factory;

import com.martin.techdemo.domain.definitions.Fields;
import com.martin.techdemo.domain.Stock;
import org.json.simple.JSONObject;
import com.martin.techdemo.util.Logger;

public class StockFactory implements Fields {
	
	public static Stock createStock(JSONObject jmessage) throws Exception {
		try {
			JSONObject jsale = (JSONObject) jmessage.get(SALE);
			return new Stock((String) jmessage.get(PRODUCT), (Double) jsale.get(AMOUNT));
		}
		catch (Exception e) {
	    	Logger.log("createProduct:Problem parsing message:%s%n", e.getMessage()); 
	    	throw e;
	    }
	}

}
