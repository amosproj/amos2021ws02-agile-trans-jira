package de.tuberlin.amos.gr2.planner.settings.general;

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

public class GeneralSettingsServlet extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(GeneralSettingsServlet.class);
    @ComponentImport
    private final TemplateRenderer renderer;

    @Inject
    public GeneralSettingsServlet(TemplateRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Loads velocity template for the general settings page
     * @param req
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException
    {
        log.debug("GeneralSettingsServlet loaded");
        response.setContentType("text/html;charset=utf-8");
        renderer.render("general-settings/general_settings.vm", response.getWriter());
    }
}

