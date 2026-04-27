package com.martin.techdemo.repository;

import java.util.Iterator;
import com.martin.techdemo.domain.Stock;
import com.martin.techdemo.domain.Transaction;
import com.martin.techdemo.types.OrderType;
import com.martin.techdemo.types.TransactionType;

public interface Repository {

	void save(Stock stock, Transaction transaction) throws Exception;

	Transaction find(Stock stock, Transaction transaction);

	Iterator<Stock> findAllStock();

	Iterator<Transaction> findAllTransactionsByStock(Stock stock, TransactionType transactionType, OrderType orderType);

	void deleteAll();

	long getCountOfAllTransactions();

}