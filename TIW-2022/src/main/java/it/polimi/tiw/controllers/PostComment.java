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

import it.polimi.tiw.beans.User;
import it.polimi.tiw.dao.CommentDAO;
import it.polimi.tiw.utils.ConnectionHandler;


@WebServlet("/PostComment")
public class PostComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;


    public PostComment() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        int userId = ((User) session.getAttribute("user")).getIdUser();
        
        
        String comment = null;
        String finalComment = null;
        int imgID = 0;
        int albumID = 0;
		boolean badRequest = false;

        
        try {
        	imgID = Integer.parseInt(request.getParameter("image"));
    		albumID = Integer.parseInt(request.getParameter("album"));
        	comment = StringEscapeUtils.escapeJava(request.getParameter("comment"));
        	// regex pattern to replace \r\n in comments
        	comment = comment.replaceAll("(\\\\r\\\\n|\\\\n)", "\\\n");
        	finalComment = comment.replaceAll("\\\n", "<br />");
        	if(comment.equals("")) badRequest = true;
        }catch(NullPointerException e) {
        	badRequest = true;
        }
        
        if(badRequest == true) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or incorrect parameters");
		}
        
        CommentDAO commentDAO = new CommentDAO(connection);
        try {
			commentDAO.createNewComment(imgID, albumID, userId, finalComment);
			System.out.println(finalComment);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String ctxpath = getServletContext().getContextPath();
		String path = ctxpath + "/ShowImageDetails?album="+albumID+"&image="+imgID;
		response.sendRedirect(path);
	}

}
