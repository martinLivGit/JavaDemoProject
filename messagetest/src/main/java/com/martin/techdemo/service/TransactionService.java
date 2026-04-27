package com.martin.techdemo.service;

import com.martin.techdemo.domain.AdjustmentTransaction;
import com.martin.techdemo.domain.Stock;
import com.martin.techdemo.domain.SaleTransaction;
import com.martin.techdemo.repository.Repository;
import com.martin.techdemo.util.Logger;

public class TransactionService  {

	private static final String	FAILED_TO_STORE_TRANSACTION = "Failed to store transaction%n";

	private Repository repo;

	public TransactionService(Repository repo)
    {
        this.repo = repo;
	}
	
	public void processTransaction(Stock stock, SaleTransaction saleTransaction,
							AdjustmentTransaction adjustmentTransaction) throws Exception
    {
        try {
			//saves the sale so that the adjustment
			//then saves and process adjustment 
			repo.save(stock, saleTransaction);
			if (adjustmentTransaction != null) {
				repo.save(stock, adjustmentTransaction);
			}			
		} catch (Exception e) {
			Logger.log(FAILED_TO_STORE_TRANSACTION);
			throw e;
		}
	}
}
