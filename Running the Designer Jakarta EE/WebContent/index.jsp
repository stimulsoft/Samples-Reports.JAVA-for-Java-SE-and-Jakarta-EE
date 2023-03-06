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
<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page import="com.stimulsoft.webdesigner.StiWebDesigerHandlerJk"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page import="com.stimulsoft.report.utils.data.StiDataColumnsUtil"%>
<%@page import="com.stimulsoft.report.dictionary.StiDataColumnsCollection"%>
<%@page import="com.stimulsoft.report.dictionary.StiDataColumn"%>
<%@page import="com.stimulsoft.report.utils.data.StiXmlTableFieldsRequest"%>
<%@page import="com.stimulsoft.report.dictionary.dataSources.StiDataTableSource"%>
<%@page import="com.stimulsoft.report.utils.data.StiXmlTable"%>
<%@page import="com.stimulsoft.report.utils.data.StiSqlField"%>
<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://stimulsoft.com/webdesigner"
	prefix="stiwebdesigner"%>
<html>
<head>
<title>Stimulsoft Webdesigner for Java 11+</title>
</head>
<body>
	<%
		final String reportPath = request.getSession().getServletContext().getRealPath("/reports/Master-Detail.mrt");
        final String xmlPath = request.getSession().getServletContext().getRealPath("/data/Demo.xml");
		final String xsdPath = request.getSession().getServletContext().getRealPath("/data/Demo.xsd");		
	    final String savePath = request.getSession().getServletContext().getRealPath("/save/");
	    
	    com.stimulsoft.base.licenses.StiLicense.setKey("ff");

	    StiWebDesignerOptions options = new StiWebDesignerOptions();
	    //options.setLocalization(request.getSession().getServletContext().getRealPath("/localization/de.xml"));
	    options.setTheme(StiWebDesignerTheme.Office2013DarkGrayBlue);		    
	    StiWebDesigerHandlerJk handler = new StiWebDesigerHandlerJk() {
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
	            report.getDictionary().getDatabases().add(new StiXmlDatabase("Demo", xsdPath, xmlPath));
	        }

	        //Occurred on new StiReport. Method intended for populate report data.
	        public void onNewReportTemplate(StiReport report, HttpServletRequest request) {	     
	            report.getDictionary().getDatabases().add(new StiXmlDatabase("Demo", xsdPath, xmlPath));
				try {
					StiXmlTableFieldsRequest tables = StiDataColumnsUtil.parceXSDSchema(new FileInputStream(xsdPath));
					for (StiXmlTable table : tables.getTables()) {
						StiDataTableSource tableSource = new StiDataTableSource(
							"Demo." + table.getName(), table.getName(), table.getName());
						tableSource.setColumns(new StiDataColumnsCollection());
						for (StiSqlField field : table.getColumns()) {
							StiDataColumn column = new StiDataColumn(
								field.getName(), field.getName(), field.getSystemType());
							tableSource.getColumns().add(column);
						}
						tableSource.setDictionary(report.getDictionary());
						report.getDictionary().getDataSources().add(tableSource);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
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

	<stiwebdesigner:stiwebdesigner handler="${handler}" options="${options}" />
</body>
</html>
