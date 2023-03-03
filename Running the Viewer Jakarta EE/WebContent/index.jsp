<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.stimulsoft.webviewer.enums.StiWebViewerTheme"%>
<%@page import="com.stimulsoft.webviewer.StiWebViewerOptions"%>
<%@page import="com.stimulsoft.webviewer.StiWebViewer"%>
<%@page import="java.io.File"%>
<%@page import="com.stimulsoft.report.StiSerializeManager"%>
<%@page import="com.stimulsoft.report.StiReport"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://stimulsoft.com/webviewer" prefix="stiwebviewer"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Stimulsoft Reports for Java</title>
</head>
<body>
	<%
	    String reportPath = request.getSession().getServletContext().getRealPath("/reports/Dashboards.mrt");
	    StiReport report = StiSerializeManager.deserializeReport(new File(reportPath));
	    report.render();
	    StiWebViewerOptions options = new StiWebViewerOptions();
	    pageContext.setAttribute("report", report);
	    pageContext.setAttribute("options", options);
	%>
	<stiwebviewer:stiwebviewer report="${report}" options="${options}" />
</body>
</html>