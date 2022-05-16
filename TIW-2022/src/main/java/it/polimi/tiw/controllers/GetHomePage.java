package it.polimi.tiw.controllers;


import it.polimi.tiw.utils.ConnectionHandler;
import it.polimi.tiw.utils.TemplateEngineHandler;

import it.polimi.tiw.beans.Album;
import it.polimi.tiw.dao.AlbumDAO;
import it.polimi.tiw.beans.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;


@WebServlet("/GetHomePage")
public class GetHomePage extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private Connection connection = null;
  private TemplateEngine templateEngine;


    public GetHomePage() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    public void init() throws ServletException {
      connection = ConnectionHandler.getConnection(getServletContext());
      templateEngine = TemplateEngineHandler.getEngine(getServletContext());
    }
    

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    
    List<Album> userAlbumList = null;
    List<Album> otherUserAlbumList = null;
      
    AlbumDAO albumDAO = new AlbumDAO(connection);
    int userId = ((User) session.getAttribute("user")).getIdUser();
      
    try {
      userAlbumList = albumDAO.findUserAlbums(userId);
      otherUserAlbumList = albumDAO.findOtherAlbums(userId);

    } catch(SQLException e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error in retrieving the album list!");
      return;
    }
      
    String homePath = "/WEB-INF/home.html";
    WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
    webContext.setVariable("userAlbumList", userAlbumList);
    webContext.setVariable("otherUserAlbumList", otherUserAlbumList);
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