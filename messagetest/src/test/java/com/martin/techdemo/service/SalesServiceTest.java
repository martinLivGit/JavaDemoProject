package com.martin.techdemo.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.martin.techdemo.MessageProcessor;
import com.martin.techdemo.domain.Stock;
import com.martin.techdemo.domain.SaleTransaction;
import com.martin.techdemo.domain.Transaction;
import com.martin.techdemo.repository.TransactionRepository;
import com.martin.techdemo.types.OrderType;
import com.martin.techdemo.types.TransactionType;

public class SalesServiceTest  {
	
	private static TransactionRepository repo = (TransactionRepository) TransactionRepository.getInstance();
	
	@BeforeClass
	public static void init() throws Exception
	{		
	}

	@Before
	public void beforeEachTest() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("testFile3.jsn").getFile());		
		JSONParser parser = new JSONParser();
		JSONArray messages = (JSONArray)parser.parse(new FileReader(file.getAbsolutePath()));         
    	MessageProcessor pm = new MessageProcessor();
    	for (Object message : messages)
        {
        	//System.out.println("\nSale:"+((JSONObject)message).toJSONString());
        	pm.process((JSONObject)message);
        }
	}

	@After
	public void afterEachTest() {
		repo.deleteAll();
	}

	@Test
	public void transaction_count_correct_after_processing() throws ParseException {
		assertEquals(17L, repo.getCountOfAllTransactions());
	}
	
	@Test
	//@Ignore
	public void product_total_correct_after_addition_adjustment() throws ParseException {
		assertEquals(115.0D, getProductTotal(new Stock("Pear"))/100,0.00D);
	}
	
	@Test
	//@Ignore
	public void product_total_correct_after_subtract_adjustment() throws ParseException {
		assertEquals(90.00D,getProductTotal(new Stock("Orange"))/100,0.00D);
	}
	
	@Test
    //@Ignore
	public void product_total_correct_after_multiply_adjustment() throws ParseException {
		assertEquals(120.00D,getProductTotal(new Stock("Apple"))/100,0.00D);
	}
		
	@Test
	//@Ignore
	public void product_total_correct_after_mutiple_adjustments() throws Exception {
		assertEquals(66.00D,getProductTotal(new Stock("Banana"))/100,0.00D);
	}	
	
	static double getProductTotal(Stock stock){
		Iterator<Transaction> it = repo.findAllTransactionsByStock(stock, TransactionType.SALE, OrderType.ASC);
		double total = 0D;
		while ( it.hasNext()) {
			SaleTransaction sale = (SaleTransaction) it.next();
			System.out.printf("getProductTotal %d:%f\n", sale.getQuantity(),repo.find(stock).getPrice() );
			total+=(sale.getQuantity()*repo.find(stock).getPrice());
		}
		System.out.println("Total:"+total);
		return total;
	}
}