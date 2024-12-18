<%@page import="Model.bean.ConvertedFile"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Conversion History</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="CSS/history.css" rel="stylesheet">
</head>
<body>
    <a href="index.jsp" class="back-link">← Back</a>
    <div class="history-table-container">
        <h2>Conversion History</h2>
        <% ArrayList<ConvertedFile> files = (ArrayList<ConvertedFile>) request.getAttribute("files"); 
        if (files != null && !files.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>Filename</th>
                        <th>Download</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (ConvertedFile file : files) { %>
                        <tr>
                            <td><%=file.getNameFile()%></td>
                            <td>
                                <a href="Download_Controller?file=<%=file.getNameFile()%>" 
                                   class="download-btn">Download</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p>No conversion history found</p>
        <% } %>
    </div>
</body>
</html>