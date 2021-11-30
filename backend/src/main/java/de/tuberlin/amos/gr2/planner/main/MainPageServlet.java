package de.tuberlin.amos.gr2.planner.main;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import javax.inject.Inject;

import com.atlassian.templaterenderer.TemplateRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for the main page
 */
public class MainPageServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MainPageServlet.class);
    @ComponentImport
    private final TemplateRenderer renderer;

    @Inject
    public MainPageServlet(TemplateRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Loads velocity template for the main page
     * @param req
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException
    {
        log.debug("MainPageServlet loaded");
        response.setContentType("text/html;charset=utf-8");
        renderer.render("main_page.vm", response.getWriter());
    }
}
