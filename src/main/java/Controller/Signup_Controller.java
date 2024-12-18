package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.bean.Account;
import Model.bo.AccountBO;

/**
 * Servlet implementation class Signup_Controller
 */
@WebServlet("/Signup_Controller")
public class Signup_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup_Controller() {
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
		String destination = null;
		RequestDispatcher rd;
		String fullname = request.getParameter("fullname");
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        AccountBO accountBO = new AccountBO();
        Account newAccount = new Account();
        newAccount.setName(fullname);
        newAccount.setUsername(username);
        newAccount.setPassword(password);
        boolean isValid = accountBO.addAccount(newAccount);
        if (isValid) {
            destination = "/login.jsp";
            rd = getServletContext().getRequestDispatcher(destination);
			rd.forward(request, response);
        } else {
        	request.setAttribute("errorMessage", "Username already exists!");
        	destination = "/signup.jsp";
        	rd = getServletContext().getRequestDispatcher(destination);
			rd.forward(request, response);
        }
	}

}
