package com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.impl;

import com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.IReportGeneratorStrategy;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.ReportInformation;
import org.springframework.stereotype.Component;

@Component
public class ReportGeneratorHtmlImpl implements IReportGeneratorStrategy {
    @Override
    public String generateReport(ReportInformation info) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<h1>Customer Report</h1>");
        sb.append("<p>Total Orders: ").append(info.numberOfOrders()).append("</p>");
        sb.append("<p>Total Value: ").append(info.totalValueSpent()).append("</p>");
        sb.append("<ul>");
        info.orders().forEach(o -> sb.append("<li>Order: ").append(o.orderValue()).append("</li>"));
        sb.append("</ul>");
        sb.append("</body></html>");
        return sb.toString();
    }
}
