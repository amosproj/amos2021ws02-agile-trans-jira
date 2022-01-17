package de.tuberlin.amos.gr2.planner.main;

import com.atlassian.jira.issue.fields.config.manager.IssueTypeSchemeManager;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import javax.inject.Inject;

import com.atlassian.templaterenderer.TemplateRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet for the main page
 */
public class MainPageServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MainPageServlet.class);

    @ComponentImport
    private final TemplateRenderer renderer;

    @Inject
    @ComponentImport
    private IssueTypeSchemeManager issueTypeSchemeManager;

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
        Collection<IssueType> issueTypes = issueTypeSchemeManager.getIssueTypesForDefaultScheme();
        Map<String, Object> params = new HashMap<>();
        for(IssueType type: issueTypes) {
            if (type.getName().equals("Request")) {
                params.put("request_id", type.getId());
            }
        }
        response.setContentType("text/html;charset=utf-8");
        renderer.render("main_page.vm", params, response.getWriter());
    }

}
