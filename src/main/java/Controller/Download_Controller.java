package Controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Download_Controller")
public class Download_Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String processedDir;

    @Override
    public void init() throws ServletException {
        super.init();
        processedDir = getProjectDirPath() + "\\" + "results";
    }
    private String getUserProcessedDir(int accountId) {
        return processedDir + "\\" + accountId;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int accountId = (int) session.getAttribute("accountID");
        
        String userProcessedDir = getUserProcessedDir(accountId);

        String fileName = request.getParameter("file");
        File file = new File(userProcessedDir, fileName);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("audio/mpeg");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentLength((int) file.length());

        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    private String getProjectDirPath() {
        String projectDirPath = getServletContext().getRealPath("/");

        String temp1 = projectDirPath.substring(0, projectDirPath.lastIndexOf(".metadata"));
        String temp2 = "";

        if (!projectDirPath.endsWith("\\")) {
            temp2 = projectDirPath.substring(projectDirPath.lastIndexOf("\\") + 1);
        } else {
            String trimmedPath = projectDirPath.substring(0, projectDirPath.length() - 1);
            temp2 = trimmedPath.substring(trimmedPath.lastIndexOf("\\") + 1);
        }

        String baseDir = temp1 + temp2;
        return baseDir;
    }
}