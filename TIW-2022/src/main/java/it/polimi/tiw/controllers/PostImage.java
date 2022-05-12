package it.polimi.tiw.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.beans.User;
import it.polimi.tiw.dao.ImageDAO;
import it.polimi.tiw.utils.ConnectionHandler;
import it.polimi.tiw.utils.TemplateEngineHandler;

/**
 * Servlet implementation class PostImage
 */
@WebServlet("/PostImage")
public class PostImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	private TemplateEngine templateEngine;
	private int albumId;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    @Override
    public void init() throws ServletException {
        connection = ConnectionHandler.getConnection(getServletContext());
        templateEngine = TemplateEngineHandler.getEngine(getServletContext());
    }


	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		System.out.println("Arrivo alla GET");
		this.albumId = Integer.parseInt(request.getParameter("album"));
		System.out.println("leggo album: "+albumId);
		String path = "/WEB-INF/newimageform.html";
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
		webContext.setVariable("album", albumId);
	    templateEngine.process(path, webContext, response.getWriter());
	}
	
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        int userId = ((User) session.getAttribute("user")).getIdUser();

    	String title = null;
    	String description = null;
    	String path = null;
    	
    	try {
    		title = StringEscapeUtils.escapeJava(request.getParameter("title"));
    		description = StringEscapeUtils.escapeJava(request.getParameter("description"));
    		path = StringEscapeUtils.escapeJava(request.getParameter("path"));
    		
    		if(title.isEmpty() || title == null || description.isEmpty() || description == null || path.isEmpty() || path == null)  {
        		throw new Exception("Missing or empty credential value");
        	}
    		
    	} catch(Exception e) {
    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing title parameter");
            return;
		}
    	
    	//MANCA DOVE PRENDERE ALBUM ID
    	//int albumId = Integer.parseInt(request.getParameter("album"));
    	ImageDAO imageDAO = new ImageDAO(connection);
        try {
			imageDAO.createNewImage(userId, albumId, title, description, path);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        response.sendRedirect(getServletContext().getContextPath() + "/GetAlbumPage?album=" + albumId + "&page=0");
    }
}
