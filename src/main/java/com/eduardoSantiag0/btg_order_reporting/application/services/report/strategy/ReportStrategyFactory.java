package com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy;

import com.eduardoSantiag0.btg_order_reporting.application.services.errors.InvalidFormatException;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.EReportFormat;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.impl.ReportGeneratorHtmlImpl;
import com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.impl.ReportGeneratorJsonImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportStrategyFactory {

    @Autowired
    private ReportGeneratorJsonImpl jsonStrategy;

    @Autowired
    private ReportGeneratorHtmlImpl htmlStrategy;

    public IReportGeneratorStrategy getStrategy(EReportFormat format) {
        if (format == null) {
            throw new InvalidFormatException("Invalid report format: " + format);
        }

        return switch (format) {
            case JSON -> jsonStrategy;
            case HTML -> htmlStrategy;
        };
    }

}
