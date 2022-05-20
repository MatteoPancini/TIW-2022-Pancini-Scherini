package it.polimi.tiw.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.beans.Comment;
import it.polimi.tiw.beans.Image;
import it.polimi.tiw.dao.CommentDAO;
import it.polimi.tiw.dao.ImageDAO;
import it.polimi.tiw.utils.ConnectionHandler;
import it.polimi.tiw.utils.TemplateEngineHandler;

/**
 * Servlet implementation class ShowImage
 */
@WebServlet("/ShowImageDetails")
public class ShowImageDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	  private Connection connection = null;
	  private TemplateEngine templateEngine;

    public ShowImageDetails() {
        super();
    }
    
    public void init() throws ServletException{
    	System.out.println("Entered showDetails");
    	connection = ConnectionHandler.getConnection(getServletContext());
        templateEngine = TemplateEngineHandler.getEngine(getServletContext());
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int imgID = Integer.parseInt(request.getParameter("imageID"));
		System.out.println(imgID);
		List<Comment> comments = new ArrayList<>();
		Image imgDetails = null;
		ImageDAO imageDAO = new ImageDAO(connection);
		CommentDAO commentDAO = new CommentDAO(connection);
		try {
			imgDetails = imageDAO.getImageFromId(imgID);
			System.out.println("id: "+ imgDetails.getIdImage() + " title: "+imgDetails.getTitle());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String path = "/WEB-INF/albumpage.html";
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
		webContext.setVariable("imageDetails", imgDetails);
	    templateEngine.process(path, webContext, response.getWriter());
	}


}
