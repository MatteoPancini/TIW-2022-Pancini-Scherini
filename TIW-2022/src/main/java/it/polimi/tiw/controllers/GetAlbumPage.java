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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.beans.Image;
import it.polimi.tiw.dao.ImageDAO;
import it.polimi.tiw.utils.ConnectionHandler;
import it.polimi.tiw.utils.TemplateEngineHandler;

/**
 * Servlet implementation class GetAlbumPage
 */
@WebServlet("/GetAlbumPage")
public class GetAlbumPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection = null;
	private TemplateEngine templateEngine;

    public GetAlbumPage() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	connection = ConnectionHandler.getConnection(getServletContext());
        templateEngine = TemplateEngineHandler.getEngine(getServletContext());
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int albumId = Integer.parseInt(request.getParameter("album"));
		int pageId = Integer.parseInt(request.getParameter("page"));

		ImageDAO imageDAO = new ImageDAO(connection);
		
		List<Image> albumImages = null;
		
		try {
			albumImages = imageDAO.findAllAlbumImages(albumId);
			
		} catch(SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve album images");
            return;
		}
		if(((pageId+1)*5)<albumImages.size() && request.getParameter("dir")!=null && request.getParameter("dir").equals("next")) 
			pageId++;
        else if(pageId>0 && request.getParameter("dir")!=null && request.getParameter("dir").equals("prev")) 
        	pageId--;
        int firstImageId = pageId * 5;
        int lastImageId = firstImageId + 4;
        List<Image> imagesToShow;
        imagesToShow = new ArrayList<>();
        for (int i = firstImageId; i <= lastImageId; i++) {
            if (albumImages.size() > i) {
            	imagesToShow.add(albumImages.get(i));
            }

        }
        redirect(request, response, pageId, albumImages, imagesToShow, albumId);
    }
	
	
	public void redirect(HttpServletRequest request, HttpServletResponse response, int pageId, List<Image>albumImages, List<Image>imagesToShow, int albumId) throws IOException {
        WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
        webContext.setVariable("pageId", pageId);
        webContext.setVariable("imagesToShowList", imagesToShow);
        webContext.setVariable("imageFullList", albumImages);
        webContext.setVariable("album", albumId);
        String albumPath = "/WEB-INF/albumpage.html";
        templateEngine.process(albumPath, webContext, response.getWriter());
    }
}
