package it.polimi.tiw.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.beans.User;
import it.polimi.tiw.dao.UserDAO;
import it.polimi.tiw.utils.ConnectionHandler;
import it.polimi.tiw.utils.TemplateEngineHandler;


@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine;
	
	public CheckLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException{
		connection = ConnectionHandler.getConnection(getServletContext());
        //templateEngine = TemplateEngineHandler.getEngine(getServletContext());
        System.out.println("fine init");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String username = null;
		String password = null;
		
		try {
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("pwd"));

			if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}
		}catch(Exception e) {
			//e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		
		System.out.println(username+password);

		
		UserDAO userDao = new UserDAO(connection);
		User user = null;
		
		
		try {
			user = userDao.checkLoginCredentials(username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not Possible to check credentials");
			return;
		}
		String path;
		if (user == null) {
			ServletContext servletContext = getServletContext();
			final WebContext webcontext = new WebContext(request, response, servletContext, request.getLocale());
			webcontext.setVariable("errorMsg", "Incorrect username or password");
			path = "/index.html";
			templateEngine.process(path, webcontext, response.getWriter());
		} else {
			request.getSession().setAttribute("user", user);
			System.out.println(request.getSession().getAttribute("user"));
			path = getServletContext().getContextPath() + "/GetHomePage";
			response.sendRedirect(path);
		}
	}
	
	public void destroy() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e){
				System.err.println(e.getMessage());
			}
		}
	}
}