package com.stimulsoft.demo;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.web.classes.StiRequestParams;
import com.stimulsoft.web.enums.StiWebViewMode;
import com.stimulsoft.webdesigner.StiWebDesigerHandlerJk;
import com.stimulsoft.webdesigner.StiWebDesignerOptions;
import com.stimulsoft.webviewer.StiWebViewerOptions;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class StimulsoftController {
    @Autowired
    ServletContext context;

    @GetMapping("/viewer")
    public String viewerAction(Model model) throws Exception {

        String reportPath = context.getRealPath("/reports/Dashboards.mrt");
        StiReport report = StiSerializeManager.deserializeReport(new File(reportPath));
        report.render();
        StiWebViewerOptions options = new StiWebViewerOptions();
        options.getToolbar().setZoom(-1);
        options.getToolbar().setViewMode(StiWebViewMode.Continuous);

        model.addAttribute("report", report);
        model.addAttribute("options", options);
        return "viewer";
    }

    @GetMapping("/designer")
    public String designerAction(Model model) throws Exception {

        String reportPath = context.getRealPath("/reports/Dashboards.mrt");
        StiReport report = StiSerializeManager.deserializeReport(new File(reportPath));

        StiWebDesignerOptions options = new StiWebDesignerOptions();
        StiWebDesigerHandlerJk handler = new StiWebDesigerHandlerJk() {

            @Override
            public void onSaveReportTemplate(StiReport report, StiRequestParams requestParams, HttpServletRequest request) {
                // StiSerializeManager.serializeReport(report, OutputStream);
            }

            @Override
            public void onOpenReportTemplate(StiReport report, HttpServletRequest request) {

            }

            @Override
            public void onNewReportTemplate(StiReport report, HttpServletRequest request) {

            }

            @Override
            public StiReport getEditedReport(HttpServletRequest request) {
                return report;
            }
        };

        model.addAttribute("handler", handler);
        model.addAttribute("options", options);
        return "designer";
    }

}
