package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.connection.ConnectionHandler;
import it.polimi.tiw.projects.DAO.UserDAO;

import it.polimi.tiw.projects.exceptions.*;

/**
 * Servlet implementation class GoToHomePage
 */
@WebServlet("/Login")
@MultipartConfig
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
       
    
    public Login() {
        super();
    }
    
    public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("I am in the login servlet...");
		String username, password;
		UserDAO userDao = new UserDAO(connection);
		User user = null;
		
		try {
			username = request.getParameter("username");
			password = request.getParameter("password");
			
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("password"));
			
			if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
				throw new ParameterException();
			}
		}catch (ParameterException e) {
			//a questo punto invia da solo o devo farlo in modo esplicito?
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
			return;
		}
		
		try {
			user = userDao.checkCredentials(username, password);
		} catch (SQLException e) {
			//a questo punto invia da solo o devo farlo in modo esplicito?
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// 500
			return;
		}
		if (user != null) {
			request.getSession().setAttribute("user", user);
			response.setStatus(HttpServletResponse.SC_OK); // 200
		}
		else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
		}
		
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
