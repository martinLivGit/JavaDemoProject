package com.martin.techdemo.report;

import java.util.Iterator;

import com.martin.techdemo.domain.AdjustmentTransaction;
import com.martin.techdemo.domain.Stock;
import com.martin.techdemo.domain.Transaction;
import com.martin.techdemo.repository.Repository;
import com.martin.techdemo.types.OrderType;
import com.martin.techdemo.types.TransactionType;
import com.martin.techdemo.util.Logger;

public class SalesAdjustmentStockReport extends StockReport {
	
	//After Y messages your application should log 
	//that it is pausing, stop accepting new messages 
	//and log a report of the adjustments that have 
	//been made to each sale type while the application was running. 
			
	//private static final String REPORT_HEADER = "%nSales Adjustment Report%n=======================%n";
	private static final String NEWLINE_ADJUSTMENT_SEPARATOR = "%n      %s:%s, ";
	private static final String ADJUSTMENT_SEPARATOR = "%s:%s, ";
	private static final String PRODUCT_SEPARATOR = "%n%s:%n      ";
	private static final int NUMBER_OF_ADJUSTMENTS_PER_LINE = 3;
	
	public SalesAdjustmentStockReport(Repository saleRepository) {
		super(saleRepository, "%nSales Adjustment Report%n=======================%n", 2);
	}

    public SalesAdjustmentStockReport(Repository repository, String header, int invocation_interval) {
        super(repository, header, invocation_interval);
    }

    public void display(Stock stock) {
        Logger.log(PRODUCT_SEPARATOR, stock.getName());
        Iterator<Transaction> adjs = repository.findAllTransactionsByStock(stock, TransactionType.ADJUSTMENT, OrderType.ASC);
        int i = 0;
        for (; adjs.hasNext(); i++) {
            AdjustmentTransaction adj = (AdjustmentTransaction) adjs.next();
            if (i == 0 || i % NUMBER_OF_ADJUSTMENTS_PER_LINE != 0) {
                Logger.log(ADJUSTMENT_SEPARATOR, adj.getOperation(), adj.getAmount());
            } else {
                Logger.log(NEWLINE_ADJUSTMENT_SEPARATOR, adj.getOperation(), adj.getAmount());
                i = 0;
            }
        }
    }

}
