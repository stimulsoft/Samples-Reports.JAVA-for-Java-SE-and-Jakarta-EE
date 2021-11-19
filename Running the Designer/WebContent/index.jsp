<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.stimulsoft.report.dictionary.databases.StiJsonDatabase"%>
<%@page import="com.stimulsoft.report.dictionary.databases.StiXmlDatabase"%>
<%@page import="com.stimulsoft.report.dictionary.databases.StiSqlDatabase"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.net.URL"%>
<%@page import="com.stimulsoft.webdesigner.enums.StiWebDesignerTheme"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="com.stimulsoft.web.classes.StiRequestParams"%>
<%@page import="com.stimulsoft.webdesigner.StiWebDesigerHandler"%>
<%@page import="com.stimulsoft.webdesigner.StiWebDesignerOptions"%>
<%@page import="java.io.File"%>
<%@page import="com.stimulsoft.report.StiSerializeManager"%>
<%@page import="com.stimulsoft.report.StiReport"%>
<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://stimulsoft.com/webdesigner"
	prefix="stiwebdesigner"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Stimulsoft Webdesigner for Java</title>
<style type="text/css">
</style>
</head>
<body>
	<%
	    final String reportPath = request.getSession().getServletContext().getRealPath("/reports/Master-Detail.mrt");
	    final String savePath = request.getSession().getServletContext().getRealPath("/save/");
	    
	    com.stimulsoft.base.licenses.StiLicense.setKey("ff");

	    StiWebDesignerOptions options = new StiWebDesignerOptions();
	    //options.setLocalization(request.getSession().getServletContext().getRealPath("/localization/de.xml"));
	    options.setTheme(StiWebDesignerTheme.Office2013DarkGrayBlue);		    
	    StiWebDesigerHandler handler = new StiWebDesigerHandler() {
	        //Occurred on loading webdesinger. Must return edited StiReport
	        public StiReport getEditedReport(HttpServletRequest request) {
	            try {
	            	StiReport report = StiSerializeManager.deserializeReport(new FileInputStream(reportPath));
	                return report;
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return null;
	        }

	        //Occurred on opening StiReport. Method intended for populate report data.
	        public void onOpenReportTemplate(StiReport report, HttpServletRequest request) {	            
	        }

	        //Occurred on new StiReport. Method intended for populate report data.
	        public void onNewReportTemplate(StiReport report, HttpServletRequest request) {	            
	        }

	        //Occurred on save StiReport. Method must implement saving StiReport
	        public void onSaveReportTemplate(StiReport report, StiRequestParams requestParams, HttpServletRequest request) {
	            try {
	                FileOutputStream fos = new FileOutputStream(savePath + requestParams.designer.fileName);
	                if (requestParams.designer.password != null) {
	                    StiSerializeManager.serializeReport(report, fos, requestParams.designer.password);
	                } else {
	                    StiSerializeManager.serializeReport(report, fos, true);
	                }
	                fos.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    };
	    pageContext.setAttribute("handler", handler);
	    pageContext.setAttribute("options", options);
	%>

	<stiwebdesigner:webdesigner handler="${handler}" options="${options}" />
</body>
</html>
