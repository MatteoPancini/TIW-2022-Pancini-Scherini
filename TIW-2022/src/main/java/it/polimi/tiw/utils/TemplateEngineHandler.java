package it.polimi.tiw.utils;

import java.sql.Connection;


import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class TemplateEngineHandler {

	public static TemplateEngine getEngine(ServletContext context) throws UnavailableException {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(context);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
		return templateEngine;
	}
	
	public static Connection connectionInit(ServletContext context) {
        Connection connection = null;
        try {
            connection = ConnectionHandler.getConnection(context);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }

}