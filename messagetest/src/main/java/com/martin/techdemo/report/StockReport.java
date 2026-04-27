package com.martin.techdemo.report;

import java.util.Iterator;

import com.martin.techdemo.domain.Stock;
import com.martin.techdemo.repository.Repository;
import com.martin.techdemo.util.Logger;

public abstract class StockReport implements ReportStockObserver {
	
	private static final String APP_PAUSING_MESSAGE = "%nApplication is pausing........%n";
	protected String header;
    protected int pause      = 1000;
	
	protected Repository repository ;
    protected int invocation_interval;
    protected long invocations = 0;

	public StockReport(Repository repository, String header, int invocation_interval) {
		this.repository = repository;
		this.header = header;
		this.invocation_interval = invocation_interval;
	}
	
	public StockReport(Repository repository, int pause) {
        this.pause = pause;
	}

    public void onMessage() {
        display();
    }

    public void display() {
        if (invocations++ % invocation_interval == 0) {
            pause();
            Logger.log(header);
            Iterator<Stock> stocks = repository.findAllStock();
            while (stocks.hasNext()) {
                Stock stock = stocks.next();
                display(stock);
            }
        }
    }

    abstract public void display(Stock stock) ;

    public void pause() {
        if (pause > 0 ) {
            try {
                Logger.log(APP_PAUSING_MESSAGE);
                Thread.sleep(pause);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}