package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.bean.Account;
import Model.bo.AccountBO;

/**
 * Servlet implementation class Login_Controller
 */
@WebServlet("/Login_Controller")
public class Login_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login_Controller() {
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
		HttpSession session = request.getSession();
		String destination = null;
		RequestDispatcher rd;
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        AccountBO accountBO = new AccountBO();
        Account account = accountBO.isValidUser(username, password);
        if (account != null) {
        	session = request.getSession(true);
        	session.setAttribute("username", account.getName());
        	session.setAttribute("accountID", account.getID());
            destination = "/index.jsp";
            rd = getServletContext().getRequestDispatcher(destination);
			rd.forward(request, response);
        } else {
        	request.setAttribute("errorMessage", "Invalid username or password!");
        	destination = "/login.jsp";
        	rd = getServletContext().getRequestDispatcher(destination);
			rd.forward(request, response);
        }
	}

}
