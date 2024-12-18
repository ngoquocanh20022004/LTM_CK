package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.bean.ConvertedFile;
import Model.bo.FileBO;

/**
 * Servlet implementation class Main_Controller
 */
@WebServlet("/Main_Controller")
public class Main_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main_Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if (action == null) {
            action = "index";
        }
		String destination = null;
		HttpSession session = request.getSession();
		RequestDispatcher rd;

		switch (action) {
		case "login": //
			destination = "/login.jsp";
        	rd = getServletContext().getRequestDispatcher(destination);
			rd.forward(request, response);
			break;
		case "signup": 
			destination = "/signup.jsp";
        	rd = getServletContext().getRequestDispatcher(destination);
			rd.forward(request, response);
			break;
		case "logout":
			session.invalidate();
			destination = "/index.jsp";
			rd = getServletContext().getRequestDispatcher(destination);
			rd.forward(request, response);
			break;
		case "history":
			//TODO get all converted files
			ArrayList<ConvertedFile> files = new ArrayList<ConvertedFile>();
			FileBO fileBO = new FileBO();
			int accountID = (int) session.getAttribute("accountID");
			try {
				files = fileBO.getAllConvertedFiles(accountID);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			request.setAttribute("files", files);
			destination = "/history.jsp";
			rd = getServletContext().getRequestDispatcher(destination);
			rd.forward(request, response);
			break;
		default:
			destination = "/index.jsp";
        	rd = getServletContext().getRequestDispatcher(destination);
			rd.forward(request, response);
			break;
		}
		
	}

}
