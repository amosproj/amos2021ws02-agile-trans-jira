package de.tuberlin.amos.gr2.planner.settings.project;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.templaterenderer.TemplateRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for the project settings page
 */
public class ProjectSettingsServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ProjectSettingsServlet.class);
    @ComponentImport
    private final TemplateRenderer renderer;

    @Inject
    public ProjectSettingsServlet(TemplateRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Loads velocity template for the project settings page
     * @param req
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException
    {
        log.debug("ProjectSettingsServlet loaded");
        response.setContentType("text/html;charset=utf-8");
        renderer.render("project_settings.vm", response.getWriter());
    }
}
