<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.stimulsoft.webviewer.enums.StiWebViewerTheme"%>
<%@page import="com.stimulsoft.webviewer.StiWebViewerOptions"%>
<%@page import="com.stimulsoft.webviewer.StiWebViewer"%>
<%@page import="java.io.File"%>
<%@page import="com.stimulsoft.report.StiSerializeManager"%>
<%@page import="com.stimulsoft.report.StiReport"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://stimulsoft.com/webviewer" prefix="stiwebviewer"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Stimulsoft Reports for Java</title>
</head>
<body onload="loaded()">
	<%
	    String reportPath = request.getSession().getServletContext().getRealPath("/reports/TwoSimpleLists.mrt");
	    StiReport report = StiSerializeManager.deserializeReport(new File(reportPath));
	    report.render();
	    StiWebViewerOptions options = new StiWebViewerOptions();
	    pageContext.setAttribute("report", report);
	    pageContext.setAttribute("options", options);
	%>
	<div style="width: 100%; height: 100%;" id="viewerDiv">
		<stiwebviewer:webviewer report="${report}" options="${options}" />
	</div>
	<script type="text/javascript">
		function loaded() {
			var jsStiNetCoreViewer1 = window["js" + document.getElementById("viewerDiv").childNodes[1]["id"]];
			jsStiNetCoreViewer1.onready = function() {
				var customButton = jsStiNetCoreViewer1.SmallButton("customButton", "Custom Button", "emptyImage");
				customButton.image.src = "icon.png";
				customButton.action = function() {
					alert("Custom Button Event");
				}

				var toolbarTable = jsStiNetCoreViewer1.controls.toolbar.firstChild.firstChild;
				var buttonsTable = toolbarTable.rows[0].firstChild.firstChild;
				var customButtonCell = buttonsTable.rows[0].insertCell(0);
				customButtonCell.appendChild(customButton);
			}
		}
	</script>
</body>
</html>