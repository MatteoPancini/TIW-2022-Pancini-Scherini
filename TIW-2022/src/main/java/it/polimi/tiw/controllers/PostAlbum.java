package it.polimi.tiw.controllers;

import it.polimi.tiw.beans.User;
import it.polimi.tiw.dao.AlbumDAO;
import it.polimi.tiw.utils.ConnectionHandler;
import it.polimi.tiw.utils.TemplateEngineHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/PostAlbum")
public class PostAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;

    private static Connection connection;
	private TemplateEngine templateEngine;


    @Override
    public void init() throws ServletException{
        connection = ConnectionHandler.getConnection(getServletContext());
        templateEngine = TemplateEngineHandler.getEngine(getServletContext());
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String path = "/WEB-INF/newalbumform.html";
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
	    templateEngine.process(path, webContext, response.getWriter());
	}
    

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = null;
        
        try {
        	title = StringEscapeUtils.escapeJava(request.getParameter("title"));
        	System.out.println("TITLE: " + title);
        	if(title.isEmpty() || title == null) {
        		throw new Exception("Missing or empty credential value");
        	}
        } catch(Exception e) {
        	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing title parameter");
            return;
		}

       
     	AlbumDAO albumDAO = new AlbumDAO(connection);
		HttpSession session = request.getSession();
		int idUser = ((User) session.getAttribute("user")).getIdUser();
     	
        try {
            albumDAO.createNewAlbum(idUser, title);
        }
        catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in creating the album in the database");
			return;
        }
        response.sendRedirect(getServletContext().getContextPath() + "/GetHomePage");
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