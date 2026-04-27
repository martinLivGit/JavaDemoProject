package com.martin.techdemo.repository;

import java.util.*;
import java.util.Observable; //deprecated since Java 9

import com.martin.techdemo.domain.AdjustmentTransaction;
import com.martin.techdemo.domain.Stock;
import com.martin.techdemo.domain.SaleTransaction;
import com.martin.techdemo.domain.Transaction;
import com.martin.techdemo.types.OrderType;
import com.martin.techdemo.types.TransactionType;
import com.martin.techdemo.util.Logger;

public class TransactionRepository implements Repository {

	private static final String DUPLICATE_TRANSACTION = "Duplicate transaction";
	private static final String SAVE_FAILED = "Save:Failed";
	
	private Map<Stock, List<Transaction>> store = new LinkedHashMap<>();
	private static TransactionRepository instance;
	 
    public static synchronized TransactionRepository getInstance(){
        if(instance == null){
            instance = new TransactionRepository();
        }
        return instance;
    }
    
	@Override
	public void save(Stock newStock, Transaction transaction) throws Exception{
		if ( ! store.containsKey(newStock)) {
			List<Transaction> list = new LinkedList<>();
			list.add(transaction);
			store.put(newStock, list);
		}
		else if ( store.get(newStock).contains(transaction)) {
			Logger.log("%n%n:%s:%s:%n%n", SAVE_FAILED, newStock, transaction );
			throw new Exception(DUPLICATE_TRANSACTION);
		}
		else {
			List<Transaction> list = store.get(newStock);
			list.add(transaction);
		}
		
		//Update the price of the Stock object
		if (transaction instanceof AdjustmentTransaction){
			find(newStock).setPrice(((AdjustmentTransaction) transaction).getAmount(), ((AdjustmentTransaction) transaction).getOperation());
		}
	}
	
	@Override
	public Transaction find(Stock stock, Transaction transaction){
		int idx = store.get(stock).lastIndexOf(transaction);
		return store.get(stock).get(idx);	
	}	
	
	public Stock findStock(Stock stock) {
        return find(stock);
	}

	public Stock find(Stock stock) {
		//System.out.printf("\nfindStock %s %s\n", stock.getName(), stock.getPrice());
		if ( store.containsKey(stock)) {
	         for(Stock iStock : store.keySet())
	         {
	             if (iStock.equals(stock)) {
	            	 return iStock;
	             }           	 
	         }
		}
		return null;
	}
	
	@Override
	public Iterator<Stock> findAllStock() {
		return store.keySet().iterator();	
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public Iterator<Transaction> findAllTransactionsByStock(Stock stock, TransactionType transactionType, OrderType orderType){
		List<Transaction> result =  new LinkedList<>();
		List<Transaction> trans = store.get(stock);		
		
		for ( Transaction transaction : trans) {
			if ( transactionType == TransactionType.SALE && transaction instanceof SaleTransaction) {
				result.add((SaleTransaction) transaction);
			}
			else if (transactionType == TransactionType.ADJUSTMENT && transaction instanceof AdjustmentTransaction){
				result.add((AdjustmentTransaction) transaction);
			}	
		}
		
		if (orderType==OrderType.ASC) {
			return result.iterator();
		}
		else {
			return ((Deque<Transaction>)result).descendingIterator();
		}
	}

	@Override
	public void deleteAll() {
		((LinkedHashMap<Stock, List<Transaction>>) store).clear();	
	}

	@Override
	public long getCountOfAllTransactions() {
		long count = 0L;
		for ( Stock p: store.keySet()) {
			count+=store.get(p).size();
		}
		return count;
	}

}
