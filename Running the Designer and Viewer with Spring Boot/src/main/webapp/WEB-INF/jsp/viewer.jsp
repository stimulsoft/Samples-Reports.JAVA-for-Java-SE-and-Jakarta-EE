<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://stimulsoft.com/webviewer" prefix="stiwebviewer"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Stimulsoft Report Viewer</title>
</head>
<body>    
	 <stiwebviewer:stiwebviewer report="${report}" options="${options}" />
     
</body>
</html>
