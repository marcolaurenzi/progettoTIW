package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.tiw.projects.connection.ConnectionHandler;
import it.polimi.tiw.projects.DAO.UserDAO;

import it.polimi.tiw.projects.exceptions.*;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/SignUp")
@MultipartConfig
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
    
    
    public SignUp() {
        super();
    }
    
	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(getServletContext().getContextPath() + "/SignUpPage.html");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// obtain parameters and check validity
				String username = null;
				String password = null;
				String address = null;
				
				try {
					username = StringEscapeUtils.escapeJava(request.getParameter("username"));
					password = StringEscapeUtils.escapeJava(request.getParameter("password"));
					address = StringEscapeUtils.escapeJava(request.getParameter("address"));
							
				if (username == null || password == null || address == null ||
						username.isBlank() || password.isBlank() || address.isBlank()) {
					throw new ParameterException();
				}

				} catch (ParameterException e) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}

				//insert the new user in the DB
				UserDAO userDao = new UserDAO(connection);
				try {
					userDao.createUser(username, password, address);
				} catch (SQLException e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}
				//redirect to login page
				response.setStatus(HttpServletResponse.SC_OK);
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
