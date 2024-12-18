package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import Model.bean.ConvertedFile;
import Model.dao.FileDAO;

/**
 * Servlet implementation class Convert_Controller
 */
@WebServlet("/Convert_Controller")
@MultipartConfig
public class Convert_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String SERVER_HOST = "localhost";
	private static final int SERVER_PORT = 7749;
	
	private static final String QUEUES_DIR = "queues";
	private static final String RESULTS_DIR = "results";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Convert_Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Part filePart = request.getPart("video");

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("login.jsp");
			filePart.delete();
			return;
		}
		
		String fileName = filePart.getSubmittedFileName();
		String baseDir = getProjectDirPath();
		baseDir = baseDir + "\\" + QUEUES_DIR;
        File queueFolder = new File(baseDir);
        if (!queueFolder.exists()) {
            queueFolder.mkdirs();
        }
		File queuedFile = new File(baseDir, fileName);
		filePart.write(baseDir + "\\" + fileName);
		
		int accountId = (int) session.getAttribute("accountID");
		
		try {
			Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
			System.out.println(socket.getSoTimeout());
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.println(queuedFile.getName() + "," + accountId);
			out.flush();
			String serverResponse = in.readLine();
			if (!serverResponse.startsWith("ERROR:")) {
				String downloadLink = request.getContextPath() + "/" + RESULTS_DIR + "/" + accountId + "/" + serverResponse;
				request.setAttribute("downloadLink", downloadLink);
				
				ConvertedFile convertedFile = new ConvertedFile();
				convertedFile.setNameFile(serverResponse);
				convertedFile.setUserID(accountId);
				FileDAO fileDAO = new FileDAO();
				fileDAO.insertConvertedFile(convertedFile);
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/download.jsp");
				rd.forward(request, response);
			} else {
				response.getWriter().write("Conversion failed. Please try again.");
			}
			
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			response.getWriter().write("Error: " + e.getMessage());
			e.printStackTrace();
		}
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
