package com.stimulsoft;

import java.io.File;
import java.io.Serializable;

import com.stimulsoft.base.mail.StiMailProperties;
import com.stimulsoft.base.serializing.StiDeserializationException;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.report.dictionary.databases.StiXmlDatabase;
import com.stimulsoft.webviewer.StiWebViewerOptions;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

/**
 * Copyright Stimulsoft
 */
@Named("WebViewerBean")
@SessionScoped
public class StiWebViewerBean implements Serializable {

    private static final long serialVersionUID = -7493299933065314904L;

    StiReport report;
    StiWebViewerOptions options;
    String viewerID = "StimulsoftWebViewer";
    StiMailProperties mailProperties;

    /**
     * @return the report
     * @throws StiDeserializationException
     */
    public StiReport getReport() {
        if (report == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            String reportPath = session.getServletContext().getRealPath("/reports/Master-Detail.mrt");
            try {
                report = StiSerializeManager.deserializeReport(new File(reportPath));
                String xmlPath = session.getServletContext().getRealPath("/data/Demo.xml");
                String xsdPath = session.getServletContext().getRealPath("/data/Demo.xsd");
                report.getDictionary().getDatabases().add(new StiXmlDatabase("Demo", xsdPath, xmlPath));
                report.render();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return report;
    }

    /**
     * @param report
     *            the report to set
     */
    public void setReport(StiReport report) {
        this.report = report;
    }

    /**
     * @return the options
     */
    public StiWebViewerOptions getOptions() {
        options = new StiWebViewerOptions();
        return options;
    }

    /**
     * @param options
     *            the options to set
     */
    public void setOptions(StiWebViewerOptions options) {
        this.options = options;
    }

    /**
     * @return the viewerID
     */
    public String getViewerID() {
        return viewerID;
    }

    /**
     * @param viewerID
     *            the viewerID to set
     */
    public void setViewerID(String viewerID) {
        this.viewerID = viewerID;
    }

    /**
     * @return the mailProperties
     */
    public StiMailProperties getMailProperties() {
        mailProperties = new StiMailProperties();
        return mailProperties;
    }

    /**
     * @param mailProperties
     *            the mailProperties to set
     */
    public void setMailProperties(StiMailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

}
