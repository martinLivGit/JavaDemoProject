package com.martin.techdemo.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.martin.techdemo.domain.factory.StockFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StockFactoryTest {
	
	static JSONObject m;
	
	@BeforeClass
	public static void init() {
		String message = "{ \"company\": \"co\", \"id\": 1, \"product\": \"p2\", \"sale\": { \"amount\": 10.1}, \"adjustment\": { \"operation\": \"ADD\", \"amount\": 1.5}}";
		JSONParser parser = new JSONParser();
		try {
			m = (JSONObject)parser.parse(message);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Before
	public void beforeEachTest() {
		//System.out.println("This is executed before each Test");
	}

	@After
	public void afterEachTest() {
		//System.out.println("This is exceuted after each Test");
	}

	@Test
	public void returns_the_correct_product_name() throws Exception {
		Stock s = StockFactory.createStock(m);
		assertEquals("p2", s.getName());
	}
	
	
}