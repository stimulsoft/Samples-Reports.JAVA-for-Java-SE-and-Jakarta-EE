package com.stimulsoft.demo;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stimulsoft.base.json.JSONObject;
import com.stimulsoft.base.mail.StiMailProperties;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.web.proxyee.StiHttpServletRequest;
import com.stimulsoft.web.proxyee.StiHttpServletResponse;
import com.stimulsoft.webviewer.StiAngularViewerHandler;
import com.stimulsoft.webviewer.StiWebViewerOptions;
import com.stimulsoft.webviewer.servlet.StiWebViewerActionServletHelper;

/**
 * Copyright Stimulsoft
 */
@RestController
public class StiViewerController implements StiAngularViewerHandler {

    @RequestMapping("/webviewer/**")
    public void StiWebViewer(HttpServletRequest request, HttpServletResponse response) {
        StiWebViewerActionServletHelper.processing(new StiHttpServletRequest(request, this), new StiHttpServletResponse(response));
    }

    public StiWebViewerOptions getOptions(StiHttpServletRequest request, StiHttpServletResponse response) {
        StiWebViewerOptions options = new StiWebViewerOptions();
        options.getAppearance().setScrollbarsMode(true);
        return options;
    }

    public StiReport getReport(StiHttpServletRequest request, StiHttpServletResponse response, JSONObject viewerProperties) {
        try {
            File reportFile = new ClassPathResource(viewerProperties.getString("reportName")).getFile();

            StiReport report = StiSerializeManager.deserializeReport(reportFile);
            report.render();

            return report;
        } catch (Exception e) {
            return null;
        }
    }

    public StiMailProperties getMailProperties(StiHttpServletRequest request, StiHttpServletResponse response) {
        StiMailProperties mailProperties = new StiMailProperties();
        // fill necessary fields
        return mailProperties;
    }

}
