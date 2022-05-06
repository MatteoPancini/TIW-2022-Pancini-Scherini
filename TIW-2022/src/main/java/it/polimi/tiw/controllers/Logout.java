package it.polimi.tiw.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import it.polimi.tiw.utils.TemplateEngineHandler;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
   

    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        templateEngine = TemplateEngineHandler.getEngine(getServletContext());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		String path = "/index.html";
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
	    templateEngine.process(path, webContext, response.getWriter());
	}
}