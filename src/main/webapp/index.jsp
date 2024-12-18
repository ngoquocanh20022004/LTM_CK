<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Video to Audio Converter</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="CSS/index.css" rel="stylesheet">
</head>
<body>
	<%
		String username = (String) request.getSession().getAttribute("username");
        if (username != null) {
	%>
	<div class="history-container">
		<input type="button" class="convert-btn" value="History"
			onclick="window.location='Main_Controller?action=history'">
	</div>
	<div class="login-container">
		<span class="username"><%=username%></span>
		<input type="button" class="convert-btn" value="Logout" 
				onclick="window.location='Main_Controller?action=logout'">
	</div>
	<% } else { %>
	<div class="login-container">
		<input type="button" class="convert-btn" value="Login" 
				onclick="window.location='Main_Controller?action=login'">
		<input type="button" class="convert-btn" value="Signup" style="margin-left: 10px;"
				onclick="window.location='Main_Controller?action=signup'">
	</div>
	<% } %>
    <div class="container">
        <form action="Convert_Controller" method="post" enctype="multipart/form-data" id="convertForm">
            <h2>Video to Audio Converter</h2>
            <div class="drop-zone" id="dropZone">
                <p>Drag and drop your video file here</p>
                <p>or</p>
                <input type="file" id="fileInput" name="video" accept=".mp4" style="display: none;" required>
                <button type="button" onclick="document.getElementById('fileInput').click()" class="convert-btn">Choose File</button>
                <div class="loading" id="loading"></div>
                <div id="fileInfo" class="file-info"></div>
            </div>
            <input type="submit" class="convert-btn" id="submitBtn" value="Convert to Audio">
        </form>
    </div>
    <div class="overlay" id="loadingOverlay">
	    <div class="loading-popup">
	        <div class="loading-spinner"></div>
	        <h2>Converting your file...</h2>
	        <p>Please wait, this may take a few moments</p>
	    </div>
	</div>
    <script>
        const dropZone = document.getElementById('dropZone');
        const fileInput = document.getElementById('fileInput');
        const fileInfo = document.getElementById('fileInfo');
        const loading = document.getElementById('loading');
        const submitBtn = document.getElementById('submitBtn');

        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            dropZone.addEventListener(eventName, preventDefaults);
        });

        function preventDefaults(e) {
            e.preventDefault();
            e.stopPropagation();
        }

        dropZone.addEventListener('dragenter', () => {
            dropZone.classList.add('drag-over');
        });

        dropZone.addEventListener('dragleave', () => {
            dropZone.classList.remove('drag-over');
        });

        dropZone.addEventListener('drop', handleDrop);
        fileInput.addEventListener('change', handleFileSelect);

        function handleDrop(e) {
            dropZone.classList.remove('drag-over');
            const file = e.dataTransfer.files[0];
            handleFile(file);
        }

        function handleFileSelect(e) {
            const file = e.target.files[0];
            handleFile(file);
        }

        function handleFile(file) {
            if (file && file.type.includes('video')) {
                loading.style.display = 'block';
                setTimeout(() => {
                    loading.style.display = 'none';
                    fileInfo.textContent = 'Selected: ' + file.name;
                    fileInfo.classList.add('active');
                    submitBtn.disabled = false;
                }, 2000);
            }
        }
	    document.getElementById('convertForm').onsubmit = function(e) {
	        e.preventDefault();
	        
	        <% if (session.getAttribute("username") == null) { %>
	            window.location.href = 'login.jsp';
	            return false;
	        <% } else { %>
	            document.getElementById('loadingOverlay').style.display = 'flex';
	            this.submit();
	        <% } %>
	    };
    </script>
</body>
</html>