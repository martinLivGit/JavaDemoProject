package com.martin.techdemo;

import com.martin.techdemo.report.StockReport;

public interface ReportObservable {

    void addReportObserver(StockReport report);

    void removeReportObserver(StockReport report);

}
