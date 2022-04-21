package it.polimi.tiw.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.dao.AlbumDAO;
import it.polimi.tiw.utils.ConnectionHandler;

/**
 * Servlet implementation class PostAlbum
 */
@WebServlet("/PostAlbum")
public class PostAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostAlbum() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
    	connection = ConnectionHandler.getConnection(getServletContext());
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AlbumDAO albumDAO = new AlbumDAO(connection);
		
        String newAlbumTitle = request.getParameter("title");
        
        if(newAlbumTitle.equals("")) {
        	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Attention: title must not be empty!");
            response.sendRedirect(getServletContext().getContextPath() + "/GetHomePage");
            return;
        }
        try {
            albumDAO.createNewAlbum(newAlbumTitle);
        }
        catch (SQLException e) {
			e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "\"Error in creating the album in the database\"");
            return;
        }
        response.sendRedirect(getServletContext().getContextPath() + "/GetHomePage");
    }
}


