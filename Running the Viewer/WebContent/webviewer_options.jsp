<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.stimulsoft.web.enums.StiContentAlignment"%>
<%@page import="com.stimulsoft.web.enums.StiWebViewMode"%>
<%@page import="com.stimulsoft.lib.utils.StiValidationUtil"%>
<%@page import="com.stimulsoft.base.drawing.StiColor"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="com.stimulsoft.base.utils.StiXmlMarshalUtil"%>
<%@page import="com.stimulsoft.base.localization.StiLocalizationInfo"%>
<%@page import="com.stimulsoft.lib.io.StiFileUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.InputStream"%>
<%@page import="com.stimulsoft.webviewer.enums.StiShowMenuMode"%>
<%@page import="com.stimulsoft.webviewer.enums.StiPrintDestination"%>
<%@page import="com.stimulsoft.base.drawing.StiColorEnum"%>
<%@page import="com.stimulsoft.webviewer.enums.StiWebViewerTheme"%>
<%@page import="com.stimulsoft.webviewer.StiWebViewerOptions"%>
<%@page
	import="com.stimulsoft.report.dictionary.databases.StiJDBCDatabase"%>
<%@page
	import="com.stimulsoft.report.dictionary.databases.StiXmlDatabase"%>
<%@page import="java.io.File"%>
<%@page import="com.stimulsoft.report.StiSerializeManager"%>
<%@page import="com.stimulsoft.report.StiReport"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://stimulsoft.com/webviewer" prefix="stiwebviewer"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- meta http-equiv="Content-Type" content="text/html; charset=UTF-8"-->
<title>Stimulsoft Webviewer for Java</title>
<stiwebviewer:resources />
<style type="text/css">
.t1 td {
	padding-right: 30px
}
</style>
</head>
<body>
	<%
	    String reportName = StiValidationUtil.isNotNullOrEmpty(request.getParameter("report"))
	            && new File(request.getSession().getServletContext()
	                               .getRealPath("/reports/" + request.getParameter("report"))).exists() ? request.getParameter("report")
	            : "TwoSimpleLists.mdc";
	    StiReport report;
	    if (reportName.endsWith(".mdc")) {//load compiled report
	        report = StiSerializeManager.deserializeDocument(
	                new File(request.getSession().getServletContext()
	                                .getRealPath("/reports/" + reportName))).getReport();	        
	    } else {//render report from mrt file
	        report = StiSerializeManager.deserializeReport(new File(
	                request.getSession().getServletContext()
	                       .getRealPath("/reports/" + reportName)));
	        StiXmlDatabase xmlDatabase = new StiXmlDatabase("Demo", request.getSession().getServletContext().getRealPath("/data/Demo.xsd"),
	                request.getSession().getServletContext().getRealPath("data/Demo.xml"));
	        report.getDictionary().getDatabases().add(xmlDatabase);
	        report.Render(false);
	    }

	    StiWebViewerOptions options = new StiWebViewerOptions();
	    //Populate viewer options
	    if (request.getParameter("localization") != null) {
	        options.setLocalization(request.getSession()
	                                       .getServletContext()
	                                       .getRealPath(
	                                               "/localization/"
	                                                       + request.getParameter("localization")));
	    }
	    if (request.getParameter("theme") != null) {
	        try {
	            options.setTheme(StiWebViewerTheme.valueOf(request.getParameter("theme")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("width") != null) {
	        options.setWidth(request.getParameter("width"));
	    }
	    if (request.getParameter("height") != null) {
	        options.setHeight(request.getParameter("height"));
	    }

	    if (request.getParameter("backColor") != null) {
	        try {
	            String stC = request.getParameter("backColor");
	            options.setBackColor(new StiColor(Integer.parseInt(stC.substring(0, 2), 16),
	                    Integer.parseInt(stC.substring(2, 4), 16), Integer.parseInt(
	                            stC.substring(4, 6), 16)));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    if (request.getParameter("rightToLeft") != null) {
	        try {
	            options.setRightToLeft("on".equals(request.getParameter("rightToLeft")));
	        } catch (Exception e) {
	        }
	    }

	    if (request.getParameter("scrollbarsMode") != null) {
	        try {
	            options.setScrollbarsMode("on".equals(request.getParameter("scrollbarsMode")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("menuAnimation") != null) {
	        try {
	            options.setMenuAnimation("on".equals(request.getParameter("menuAnimation")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("menuShowMode") != null) {
	        try {
	            options.setMenuShowMode(StiShowMenuMode.valueOf(request.getParameter("menuShowMode")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("pagesViewMode") != null) {
	        try {
	            options.setPagesViewMode(StiWebViewMode.valueOf(request.getParameter("pagesViewMode")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("pageAlignment") != null) {
	        try {
	            options.setPageAlignment(StiContentAlignment.valueOf(request.getParameter("pageAlignment")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("printDestination") != null) {
	        try {
	            options.setMenuPrintDestination(StiPrintDestination.valueOf(request.getParameter("printDestination")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("toolbarVisible") != null) {
	        try {
	            options.setToolbarVisible("on".equals(request.getParameter("toolbarVisible")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("showPrint") != null) {
	        try {
	            options.setShowButtonPrint("on".equals(request.getParameter("showPrint")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("showSave") != null) {
	        try {
	            options.setShowButtonSave("on".equals(request.getParameter("showSave")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("showZoom") != null) {
	        try {
	            options.setShowButtonZoom("on".equals(request.getParameter("showZoom")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("showDocumentExport") != null) {
	        try {
	            options.setShowExportToDocument("on".equals(request.getParameter("showDocumentExport")));
	        } catch (Exception e) {
	        }
	    }
	    if (request.getParameter("showPDFExport") != null) {
	        try {
	            options.setShowExportToPdf("on".equals(request.getParameter("showPDFExport")));
	        } catch (Exception e) {
	        }
	    }

	    options.setRefreshTimeout(3);	
	    pageContext.setAttribute("report", report);
	    pageContext.setAttribute("options", options);
	%>

	<h1 align="center">Welcome to JAVA WebViewer!</h1>
	<table class="t2">
		<tr>
			<td valign="top" style="border-right: solid 1px #e8eef4">
				<form>
					<table style="white-space: nowrap;" class="t1">
						<tr>
							<td>Report</td>
						</tr>
						<tr>
							<td colspan="2"><select name="report" style="width: 100%">
									<option value="TwoSimpleLists.mdc" <%="TwoSimpleLists.mdc".equals(reportName) ? "selected" : ""%>  >Compiled report</option>
									<option value="SimpleList.mrt" <%="SimpleList.mrt".equals(reportName) ? "selected" : ""%>>Report from template</option>
									<option value="ParametersSelectingCountry.mrt" <%="ParametersSelectingCountry.mrt".equals(reportName) ? "selected" : ""%>>Report with
										parameters</option>
									<option value="Master-Detail.mrt" <%="Master-Detail.mrt".equals(reportName) ? "selected" : ""%>>Report with
										bookmarks</option>
							</select></td>
						</tr>
						<tr>
							<td>Language</td>
							<td><select name="localization" style="width: 100%">
									<%
									    File localizationDir = new File(request.getSession().getServletContext()
									                                           .getRealPath("/localization"));
									    if (localizationDir.exists()) {
									        Iterator<File> iterateLocalization = StiFileUtil.iterateFiles(localizationDir,
									                new String[] { "xml" }, false);
									        for (; iterateLocalization.hasNext();) {
									            File fileLoc = iterateLocalization.next();
									            InputStream is = new BufferedInputStream(new FileInputStream(fileLoc));
									            StiLocalizationInfo localization = StiXmlMarshalUtil.unmarshal(is,
									                    StiLocalizationInfo.class);
									%>
									<option value="<%=fileLoc.getName()%>"
										<%=fileLoc.getName().equals(request.getParameter("localization"))
                            || (request.getParameter("localization") == null && fileLoc.getName()
                                                                                       .equals("en.xml")) ? "selected"
                            : ""%>><%=localization.getDescription()%></option>
									<%
									    }
									    }
									%>
							</select></td>
						</tr>
						<tr>
							<td>Theme</td>
							<td><select name="theme" style="width: 100%">
									<%
									    for (StiWebViewerTheme theme : StiWebViewerTheme.values()) {
									%>
									<option value="<%=theme.name()%>"
										<%=theme == options.getTheme() ? "selected" : ""%>><%=theme.name()%></option>
									<%
									    }
									%>
							</select></td>
						</tr>
						<tr>
							<td>Width (px or %)</td>
							<td><input type="text" name="width"
								value="<%=options.getWidth()%>" /></td>
						</tr>
						<tr>
							<td>Height (px or %)</td>
							<td><input type="text" name="height"
								value="<%=options.getHeight()%>" /></td>
						</tr>
						<tr>
							<td>Background color (RGB)</td>
							<td><input type="text" name="backColor"
								value="<%=options.getBackColor().toHTML().substring(1)%>" /></td>
						</tr>
						<tr>
							<td>Right to left</td>
							<td><input name="rightToLeft" type="checkbox"
								<%=options.isRightToLeft() ? "checked='checked'" : ""%> /></td>
						</tr>
						<tr>
							<td>Scrollbars mode</td>
							<td><input name="scrollbarsMode" type="checkbox"
								<%=options.isScrollbarsMode() ? "checked='checked'" : ""%> /></td>
						</tr>
						<tr>
							<td>Menu animation</td>
							<td><input name="menuAnimation" type="checkbox"
								<%=options.isMenuAnimation() ? "checked='checked'" : ""%> /><input
								type="hidden" name="menuAnimation" value="off" /></td>
						</tr>
						<tr>
							<td>Menu show mode</td>
							<td><select name="menuShowMode" style="width: 100%"><option
										value="<%=StiShowMenuMode.Click.name()%>"
										<%=StiShowMenuMode.Click == options.getMenuShowMode() ? "selected" : ""%>><%=StiShowMenuMode.Click.name()%></option>
									<option value="<%=StiShowMenuMode.Hover.name()%>"
										<%=StiShowMenuMode.Hover == options.getMenuShowMode() ? "selected" : ""%>><%=StiShowMenuMode.Hover.name()%></option>
							</select></td>
						</tr>
						<tr>
							<td>Pages view mode</td>
							<td><select name="pagesViewMode" style="width: 100%"><option
										value="<%=StiWebViewMode.OnePage.name()%>"
										<%=StiWebViewMode.OnePage == options.getPagesViewMode() ? "selected" : ""%>><%=StiWebViewMode.OnePage.name()%></option>
									<option value="<%=StiWebViewMode.WholeReport.name()%>"
										<%=StiWebViewMode.WholeReport == options.getPagesViewMode() ? "selected" : ""%>><%=StiWebViewMode.WholeReport.name()%></option>
							</select></td>
						</tr>
						<tr>
							<td>Pages alignmeng</td>
							<td><select name="pageAlignment" style="width: 100%">
									<%
									    for (StiContentAlignment align : StiContentAlignment.values()) {
									%>
									<option value="<%=align.name()%>"
										<%=align == options.getPageAlignment() ? "selected" : ""%>><%=align.name()%></option>
									<%
									    }
									%>
							</select></td>
						</tr>
						<tr>
							<td>Toolbar visible</td>
							<td><input name="toolbarVisible" type="checkbox"
								<%=options.isToolbarVisible() ? "checked='checked'" : ""%> /><input
								type="hidden" name="toolbarVisible" value="off" /></td>
						</tr>
						<tr>
							<td>Show pring button</td>
							<td><input name="showPrint" type="checkbox"
								<%=options.isShowButtonPrint() ? "checked='checked'" : ""%> /><input
								type="hidden" name="showPrint" value="off" /></td>
						</tr>
						<tr>
							<td>Show save button</td>
							<td><input name="showSave" type="checkbox"
								<%=options.isShowButtonSave() ? "checked='checked'" : ""%> /><input
								type="hidden" name="showSave" value="off" /></td>
						</tr>
						<tr>
							<td>Show zoom button</td>
							<td><input name="showZoom" type="checkbox"
								<%=options.isShowButtonZoom() ? "checked='checked'" : ""%> /><input
								type="hidden" name="showZoom" value="off" /></td>
						</tr>
						<tr>
							<td>Show Document export</td>
							<td><input name="showDocumentExport" type="checkbox"
								<%=options.isShowExportToDocument() ? "checked='checked'" : ""%> /><input
								type="hidden" name="showDocumentExport" value="off" /></td>
						</tr>
						<tr>
							<td>Show PDF export</td>
							<td><input name="showPDFExport" type="checkbox"
								<%=options.isShowExportToPdf() ? "checked='checked'" : ""%> /><input
								type="hidden" name="showPDFExport" value="off" /></td>
						</tr>
						<tr>
							<td>Pring destination</td>
							<td><select name="printDestination" style="width: 100%">
									<%
									    for (StiPrintDestination print : StiPrintDestination.values()) {
									%>
									<option value="<%=print.name()%>"
										<%=print == options.getMenuPrintDestination() ? "selected" : ""%>><%=print.name()%></option>
									<%
									    }
									%>
							</select></td>
						</tr>


						<tr>
							<td colspan="1"><input type="submit" /></td>
						</tr>
					</table>




				</form>



			</td>
			<td valign="top" style="width: 100%;"><stiwebviewer:webviewer
					report="${report}" options="${options}" /></td>
		</tr>
	</table>







</body>
</html>
