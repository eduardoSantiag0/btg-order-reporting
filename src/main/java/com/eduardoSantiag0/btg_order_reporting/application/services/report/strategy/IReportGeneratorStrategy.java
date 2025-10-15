package com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy;

import com.eduardoSantiag0.btg_order_reporting.infra.dtos.ReportInformation;

public interface IReportGeneratorStrategy {
    String generateReport(ReportInformation info);
}
