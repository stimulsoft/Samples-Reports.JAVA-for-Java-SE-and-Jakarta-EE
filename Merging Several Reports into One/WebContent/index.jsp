<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.stimulsoft.report.components.simplecomponents.StiText"%>
<%@page import="com.stimulsoft.report.components.StiComponent"%>
<%@page import="com.stimulsoft.report.components.StiPage"%>
<%@page import="java.io.File"%>
<%@page import="com.stimulsoft.report.StiSerializeManager"%>
<%@page import="com.stimulsoft.report.StiReport"%>
<%@page import="com.stimulsoft.webviewer.StiWebViewerOptions"%>
<%@ taglib uri="http://stimulsoft.com/webviewer" prefix="stiwebviewer"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Merge Reports</title>
</head>
<body>

	<%
	String path1 = application.getRealPath("/reports/SimpleList.mrt");
	StiReport report1 = StiSerializeManager.deserializeReport(new File(path1));
	report1.render();

	String path2 = application.getRealPath("/reports/SimpleGroup.mrt");
	StiReport report2 = StiSerializeManager.deserializeReport(new File(path2));
	report2.render();

	StiReport reportMerge = StiReport.newInstance();

	reportMerge.getRenderedPages().clear();

	for (StiPage renderedPage : report1.getRenderedPages()) {
    	reportMerge.getRenderedPages().add(renderedPage);
	}

	for (StiPage renderedPage : report2.getRenderedPages()) {
    	reportMerge.getRenderedPages().add(renderedPage);
	}

	reportMerge.setIsRendered(true);

	// Ensure continuous numbering of pages
	for (int index = 0; index < reportMerge.getRenderedPages().size(); index++) {
	    StiPage renderedPage = reportMerge.getRenderedPages().get(index);

	    // Name of page number component in the first report
	    StiComponent component = renderedPage.GetComponentByName("Text6");
	    
	    // Name of page number component in the second report
	    if (component == null || !((StiText) component).getText().getValue().contains("Page"))
	        component = renderedPage.GetComponentByName("Text5");
	    // Set displayed page number
	    if (component != null)
	        ((StiText) component).setText("Page " + (index + 1) + " of " + reportMerge.getRenderedPages().size());
	}

	StiWebViewerOptions options = new StiWebViewerOptions();

	pageContext.setAttribute("report", reportMerge);
	pageContext.setAttribute("options", options);
	%>

	<stiwebviewer:webviewer report="${report}" options="${options}" />

</body>
</html>