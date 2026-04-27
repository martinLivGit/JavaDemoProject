package com.martin.techdemo;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.martin.techdemo.report.SalesAdjustmentStockReport;
import com.martin.techdemo.report.StockReport;
import com.martin.techdemo.repository.TransactionRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.martin.techdemo.domain.factory.StockFactory;
import com.martin.techdemo.domain.factory.TransactionFactory;
import com.martin.techdemo.report.SalesByProductStockReport;
import com.martin.techdemo.service.TransactionService;
import com.martin.techdemo.util.Logger;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;

public class MessageProcessor {

    //A requirement of the exercise is to minimise the use of third party libraries

    private static class ReportObservers implements ReportObservable {
        private List<StockReport> reports = new ArrayList<>();

        //notify the observers ie the reports by calling invoke
        public void notifyObservers() {
            for (StockReport report : this.reports) {
                report.onMessage();
            }
        }

        @Override
        public void addReportObserver(StockReport report) {
            this.reports.add(report);
        }

        @Override
        public void removeReportObserver(StockReport report) {
            this.reports.remove(report);
        }
    }

	private static TransactionService transactionService ;

	public static void main(String[] args) throws Exception  {

        //create observable Singleton repo
        TransactionRepository transRepo = TransactionRepository.getInstance();

        //create the transaction service
        transactionService = new TransactionService(transRepo);

        //process the messages contained in a file
        //reports will be triggered as they observe changes
        //loosely implements observer/observable
		JSONArray messages = readMessagesFromFile(args);
        MessageProcessor messageProcessor = new MessageProcessor();

        //create a couple of observer reports which are notified of new messages
        ReportObservers observers = new ReportObservers();
        observers.addReportObserver(new SalesByProductStockReport(transRepo,"%nSales Report%n=======================%n", 2));
        observers.addReportObserver(new SalesAdjustmentStockReport(transRepo,"%nSales Adjustment Report%n=======================%n", 4));

        messageProcessor.processMessages(messages, observers);
    }

	public void processMessages(JSONArray messages, ReportObservers observers) {

        for (Object message : messages)
        {
            try {
				process((JSONObject)message);
                observers.notifyObservers();
			}
			catch (Exception e) {
				//skip message and process next
				Logger.log("Skipping message:%s:%s%n", e.getMessage(),((JSONObject)message).toJSONString());
				continue;
			}
        }
	}

	public static JSONArray readMessagesFromFile(String[] args) throws Exception {
		JSONArray messages = null;
		try {
			messages = parseFile(args[0]);
		} 
        catch (Exception e) {
        	Logger.log("Problem processing file or contents:%s", e.getMessage()); 
            throw e;
        }
		return messages;
	}
	
	public static JSONArray parseFile(String fileName) throws IOException, ParseException{
		JSONParser parser = new JSONParser();
    	return (JSONArray)parser.parse(new FileReader(fileName));         
	}

	public static void process( JSONObject sale) throws Exception {
		transactionService.processTransaction(	StockFactory.createStock(sale)
												,TransactionFactory.createSaleTransaction(sale)
												,TransactionFactory.createAdjustmentTransaction(sale));
	}

}
	
