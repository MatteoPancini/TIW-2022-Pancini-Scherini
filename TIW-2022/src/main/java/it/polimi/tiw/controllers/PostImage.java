package it.polimi.tiw.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import it.polimi.tiw.beans.User;
import it.polimi.tiw.dao.ImageDAO;
//import it.polimi.tiw.servlets.Exception;
//import it.polimi.tiw.servlets.String;
import it.polimi.tiw.utils.ConnectionHandler;
import it.polimi.tiw.utils.TemplateEngineHandler;

/**
 * Servlet implementation class PostImage
 */
@WebServlet("/PostImage")
@MultipartConfig (
		  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		  maxFileSize = 1024 * 1024 * 10,      // 10 MB
		  maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class PostImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	private TemplateEngine templateEngine;
	private int albumId;
	
	String folderPath = "";


       
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
        folderPath = getServletContext().getInitParameter("folderPath");
    }


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("Arrivo alla GET");
		this.albumId = Integer.parseInt(request.getParameter("album"));
		System.out.println("leggo album: "+ albumId);
		String path = "/WEB-INF/newimageform.html";
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
		webContext.setVariable("album", albumId);
	    templateEngine.process(path, webContext, response.getWriter());
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
        int userId = ((User) session.getAttribute("user")).getIdUser();
        
        
        String title = StringEscapeUtils.escapeJava(request.getParameter("title"));
		String description = StringEscapeUtils.escapeJava(request.getParameter("description"));
		Part filePart = request.getPart("file");
		
		if(title.isEmpty() || title == null || description.isEmpty() || description == null)  {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty credential value");
			return;
    	}
		
		if (filePart == null || filePart.getSize() <= 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing file in request!");
			return;
		}
		
		String contentType = filePart.getContentType();
		System.out.println("Type " + contentType);

		if (!contentType.startsWith("image")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File format not permitted");
			return;
		}

		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		UUID idFile = UUID.randomUUID();
		fileName = idFile.toString() + fileName;

		System.out.println("Filename: " + fileName);

		String outputPath = folderPath + userId + "/" + fileName; //folderPath inizialized in init
		System.out.println("Output path: " + outputPath);

		File file = new File(outputPath);

		try (InputStream fileContent = filePart.getInputStream()) {
			// TODO: WHAT HAPPENS IF A FILE WITH THE SAME NAME ALREADY EXISTS?
			// you could override it, send an error or 
			// rename it, for example, if I need to upload images to an album, and for each image I also save other data, I could save the image as {image_id}.jpg using the id of the db

			Files.copy(fileContent, file.toPath());
			System.out.println("File saved correctly!");
			
			

			} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while saving file");
		}
		
		ImageDAO imageDAO = new ImageDAO(connection);
        try {
			imageDAO.createNewImage(userId, albumId, title, description, fileName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		response.sendRedirect(getServletContext().getContextPath() + "/GetAlbumPage?album=" + albumId + "&page=0");		

		
		
	}
}
