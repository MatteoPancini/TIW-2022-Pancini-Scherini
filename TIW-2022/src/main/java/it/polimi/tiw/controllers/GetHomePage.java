package it.polimi.tiw.controllers;

import it.polimi.tiw.utils.TemplateEngineHandler;

import it.polimi.tiw.beans.Album;
import it.polimi.tiw.dao.AlbumDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;


/**
 * Servlet implementation class GetHomePage
 */
@WebServlet("/GetHomePage")
public class GetHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection = null;
	private TemplateEngine templateEngine;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetHomePage() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    public void init() {
    	connection = TemplateEngineHandler.connectionInit(getServletContext());
        templateEngine = TemplateEngineHandler.templateEngineInit(getServletContext());
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Album> albumList = null;
		
		AlbumDAO albumDAO = new AlbumDAO(connection);
		
		try {
			albumList = albumDAO.findAllAlbums();
		} catch(SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in retrieving the album list!");
			return;
		}
		
		String homePath = "/WEB-INF/Home.html"; //TODO: QUI CI VA IL PATH DELLA HOMEPAGE
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
		webContext.setVariable("albumList", albumList);
		templateEngine.process(homePath, webContext, response.getWriter());
		
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
	

}
