package com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.impl;

import com.eduardoSantiag0.btg_order_reporting.application.services.report.strategy.IReportGeneratorStrategy;
import com.eduardoSantiag0.btg_order_reporting.infra.dtos.ReportInformation;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class ReportGeneratorJsonImpl implements IReportGeneratorStrategy {
    @Override
    public String generateReport(ReportInformation info) {
        Gson gson = new Gson();
        return gson.toJson(info);
//        System.out.println(json + "\n\n");
//        return json;
    }
}
