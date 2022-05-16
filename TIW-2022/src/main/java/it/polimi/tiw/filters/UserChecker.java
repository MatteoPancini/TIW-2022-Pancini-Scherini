package it.polimi.tiw.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserChecker implements Filter {       
	
	public UserChecker() {
		// TODO Auto-generated method stub
        super();
    }
	
	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

    
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws ServletException, IOException {
		
		System.out.println("filtering request");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String loginpath = req.getServletContext().getContextPath() + "/index.html";
		
		HttpSession s = req.getSession();
		if (s.isNew() || s.getAttribute("user") == null) {
			res.sendRedirect(loginpath);
			return;
		}
		chain.doFilter(request, response);

	}
	
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
