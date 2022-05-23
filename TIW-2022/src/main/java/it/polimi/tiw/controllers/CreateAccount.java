package it.polimi.tiw.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.dao.UserDAO;
import it.polimi.tiw.utils.ConnectionHandler;
import it.polimi.tiw.utils.TemplateEngineHandler;

@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine;
	
       
    public CreateAccount() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init() throws ServletException{
		connection = ConnectionHandler.getConnection(getServletContext());
        templateEngine = TemplateEngineHandler.getEngine(getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		 String path = "/WEB-INF/register.html";
		 WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
	     templateEngine.process(path, webContext, response.getWriter());
	     
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = null;
		String username = null;
		String password = null;
		String confirmedPassword = null;
		boolean badRequest = false;
		try {
			email = StringEscapeUtils.escapeJava(request.getParameter("email"));
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			password = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
			confirmedPassword = StringEscapeUtils.escapeJava(request.getParameter("confirmed_pwd"));
			
			if(email == null || username == null || password == null || confirmedPassword == null) {
				badRequest = true;
			}
			}catch(NullPointerException e) {
				badRequest = true;
			}
		
		if(badRequest == true) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or incorrect parameters");
		}
		
		System.out.println("New user email: "+email+" username: "+username+" isPswEquals: "+password.equals(confirmedPassword));
	
				
		UserDAO userDAO = new UserDAO(connection);
		try {
			// First check if the password and confirmed password are equals
			if(!password.equals(confirmedPassword)) {
				ServletContext servletContext = getServletContext();
				final WebContext webcontext = new WebContext(request, response, servletContext, request.getLocale());
				webcontext.setVariable("errorMsg", "Password fields don't match");
				String path = "/WEB-INF/register.html";
				templateEngine.process(path, webcontext, response.getWriter());
			}
			
			// Second check if username is not in the DB
			List<String> usernameList = userDAO.findAllUsernames();
			
			if(usernameList.contains(username)) {
				ServletContext servletContext = getServletContext();
				final WebContext webcontext = new WebContext(request, response, servletContext, request.getLocale());
				webcontext.setVariable("errorMsg", "Username already in use");
				String path = "/WEB-INF/register.html";
				templateEngine.process(path, webcontext, response.getWriter());
			}
			
			// Third check email validity structure
			if(!mailSyntaxCheck(email)) {
				ServletContext servletContext = getServletContext();
				final WebContext webcontext = new WebContext(request, response, servletContext, request.getLocale());
				webcontext.setVariable("errorMsg", "email pattern does not exists");
				String path = "/WEB-INF/register.html";
				templateEngine.process(path, webcontext, response.getWriter());
			}
			
			// Fourth create new User
			userDAO.createUser(email, username, password);
			
			int newUserId = userDAO.getIdFromUsername(username);
			
			//provo a creare una cartella in img
			if(newUserId != -1) {
				String folderPath = getServletContext().getInitParameter("folderPath");
				File file = new File(folderPath + newUserId);
				boolean bool = file.mkdir();
			      if(bool){
			         System.out.println("Directory created successfully");
			      }else{
			         System.out.println("Sorry couldn�t create specified directory");
			      }
			}
			
			String ctxpath = getServletContext().getContextPath();
			String path = ctxpath + "/CheckLogin";
			response.sendRedirect(path);
			
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in creating the product in the database");
			return;
		}
		
	}
	
	@Override
	public void destroy() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e){
				System.err.println(e.getMessage());
			}
		}
	}
	
	private boolean mailSyntaxCheck(String email)
	   {
	        // Create the Pattern using the regex
	        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	 
	        // Match the given string with the pattern
	        Matcher m = p.matcher(email);
	 
	        // check whether match is found
	        boolean matchFound = m.matches();
	 
	        StringTokenizer st = new StringTokenizer(email, ".");
	        String lastToken = null;
	        while (st.hasMoreTokens()) {
	            lastToken = st.nextToken();
	        }
	 
	    // validate the country code
	        if (matchFound && lastToken.length() >= 2
	                && email.length() - 1 != lastToken.length()) {
	 
	            return true;
	        } else {
	            return false;
	        }
	 
	    }

}
