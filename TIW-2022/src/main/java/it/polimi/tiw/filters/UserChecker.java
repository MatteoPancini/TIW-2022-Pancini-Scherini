package it.polimi.tiw.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminChecker")
public class UserChecker extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	public UserChecker() {
		// TODO Auto-generated method stub
        super();
    }
	
	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

    
	protected void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws ServletException, IOException {
		
		System.out.println("filter");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String loginpath = req.getServletContext().getContextPath() + "/index.html";
		
		HttpSession s = req.getSession();
		if (s.isNew() || s.getAttribute("user") == null) {
			res.sendRedirect(loginpath);
			return;
		}
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
