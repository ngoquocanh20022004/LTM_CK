<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Download Audio</title>
    <link href="CSS/download.css" rel="stylesheet">
</head>
<body>
    <div class="download-container">
        <h2>Your Audio File is Ready!</h2>
        <p>Click below to download your converted audio file</p>
        <%
            String fileName = request.getAttribute("downloadLink").toString();
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        %>
        <a href="Download_Controller?file=<%=fileName%>" class="download-btn">Download Audio</a>
        <p><a href="index.jsp" style="color: #667eea; text-decoration: none;">Back to Converter</a></p>
    </div>
</body>
</html>