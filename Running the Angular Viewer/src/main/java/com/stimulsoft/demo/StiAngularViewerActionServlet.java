package com.stimulsoft.demo;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class StiAngularViewerActionServlet extends HttpServlet implements StiAngularViewerHandler {

    private static final long serialVersionUID = 2759116620736251447L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processing(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processing(request, response);
    }

    protected void processing(HttpServletRequest request, HttpServletResponse response) {
        StiWebViewerActionServletHelper.processing(new StiHttpServletRequest(request, this), new StiHttpServletResponse(response));
    }

    public StiWebViewerOptions getOptions(StiHttpServletRequest request, StiHttpServletResponse response) {
        StiWebViewerOptions options = new StiWebViewerOptions();
        options.getAppearance().setScrollbarsMode(true);
        return options;
    }

    public StiReport getReport(StiHttpServletRequest request, StiHttpServletResponse response, JSONObject viewerProperties) {
        try {
            File reportFile = new File(request.getSession().getServletContext().getRealPath("reports\\" + viewerProperties.getString("reportName")));

            StiReport report = StiSerializeManager.deserializeReport(reportFile);
            report.render();

            return report;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public StiMailProperties getMailProperties(StiHttpServletRequest request, StiHttpServletResponse response) {
        StiMailProperties mailProperties = new StiMailProperties();
        // fill necessary fields
        return mailProperties;
    }
}
