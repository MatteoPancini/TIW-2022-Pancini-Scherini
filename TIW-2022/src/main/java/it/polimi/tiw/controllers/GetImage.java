package it.polimi.tiw.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Servlet implementation class GetImage
 */
@WebServlet("/GetImage")
public class GetImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String folderPath = "";

	
	public void init() throws ServletException {
		folderPath = getServletContext().getInitParameter("folderPath");
	}

    public GetImage() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fileName = StringEscapeUtils.escapeJava(request.getParameter("fileName"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		String userFolderPath = folderPath + userId + "/";
		
		
		
		File file = new File(userFolderPath, fileName);
		
		if (!file.exists() || file.isDirectory()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not present");
			return;
		}
		
		Files.copy(file.toPath(), response.getOutputStream());
		
	}

	

}
