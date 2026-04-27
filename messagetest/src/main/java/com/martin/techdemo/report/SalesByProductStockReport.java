package com.martin.techdemo.report;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Iterator;

import com.martin.techdemo.domain.Stock;
import com.martin.techdemo.domain.SaleTransaction;
import com.martin.techdemo.domain.Transaction;
import com.martin.techdemo.repository.Repository;
import com.martin.techdemo.types.OrderType;
import com.martin.techdemo.types.TransactionType;
import com.martin.techdemo.util.Logger;

public class SalesByProductStockReport extends StockReport {

	//After every 10th message received your application should 
	//log a report detailing the number of sales of each 
	//product and their total value

	public SalesByProductStockReport(Repository saleRepository) {
        super(saleRepository, "%n%nSales Report%n============%n",2);
	}

    public SalesByProductStockReport(Repository repository, String header, int invocation_interval) {
        super(repository, header, invocation_interval);
    }

	public void display(Stock stock) {
		BigDecimal total;
		Iterator<Transaction> sales = repository.findAllTransactionsByStock(stock, TransactionType.SALE, OrderType.ASC);
		total = new BigDecimal(0L);
		while ( sales.hasNext()) {
			SaleTransaction sale = (SaleTransaction) sales.next();
			total = total.add( new BigDecimal(sale.getQuantity() * stock.getPrice()));		
		}
		Logger.log("%-25s: Total Sale:%s%n", stock.getName(), NumberFormat.getCurrencyInstance().format(total.divide(new BigDecimal(100L),2, BigDecimal.ROUND_HALF_UP)));
	}
	
}
