package it.polimi.tiw.controllers;

import it.polimi.tiw.beans.User;
import it.polimi.tiw.dao.AlbumDAO;
import it.polimi.tiw.utils.ConnectionHandler;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/PostAlbum")
public class PostAlbum extends HttpServlet {
    private static Connection connection;

    @Override
    public void init() throws ServletException{
        connection = ConnectionHandler.getConnection(getServletContext());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = null;
        boolean badRequest = false;
        
        
        try {
        	title = request.getParameter("title");
        	
        	if(title.isEmpty()) {
        		badRequest = true;
        	}
        	}
        catch (NullPointerException e) {
        	badRequest = true;
        }
        
        if(badRequest == true) {
        	//è possibile fare più di una response????
        	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing title parameter");
        	response.sendRedirect(getServletContext().getContextPath() + "/GoToHomepage");
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
}