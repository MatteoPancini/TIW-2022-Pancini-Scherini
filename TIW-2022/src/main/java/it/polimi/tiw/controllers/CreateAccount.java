package it.polimi.tiw.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO: complete the doPost
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
