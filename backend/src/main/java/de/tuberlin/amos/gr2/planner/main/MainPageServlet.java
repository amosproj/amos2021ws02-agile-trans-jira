package de.tuberlin.amos.gr2.planner.main;

import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.manager.IssueTypeSchemeManager;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.permission.ProjectPermissions;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserProjectHistoryManager;
import com.atlassian.plugin.PluginException;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import static com.atlassian.jira.component.ComponentAccessor.getJiraAuthenticationContext;

import javax.inject.Inject;

import com.atlassian.templaterenderer.TemplateRenderer;

import de.tuberlin.amos.gr2.impl.PluginInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
    private UserProjectHistoryManager userProjectHistoryManager;

    @Inject
    @ComponentImport
    private PermissionManager permissionManager;

    @Inject
    @ComponentImport
    private IssueTypeSchemeManager issueTypeSchemeManager;

    @ComponentImport
    private final CustomFieldManager customFieldManager;

    @Inject
    public MainPageServlet(TemplateRenderer renderer, CustomFieldManager customFieldManager) {
        this.renderer = renderer;
        this.customFieldManager = customFieldManager;
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
        Map<String, Object> params = new HashMap<>();

        ApplicationUser user = getJiraAuthenticationContext().getLoggedInUser();
        //using a deprecated class because there is no such method for getCurrentProject with ProjectPermissionKey yet
        Project currentProject = userProjectHistoryManager.getCurrentProject(Permissions.BROWSE, user);
        Collection<Project> allProjects = permissionManager.getProjects(ProjectPermissions.BROWSE_PROJECTS, user);
        params.put("currentProject", currentProject);
        params.put("allProjects", allProjects);

        Collection<IssueType> issueTypes = issueTypeSchemeManager.getIssueTypesForDefaultScheme();

        for(IssueType type: issueTypes) {
            if (type.getName().equals("Request")) {
                params.put("request_id", type);
            }
        }
        if (!params.containsKey("request_id")) {
            params.put("request_id", "00000");
        }

        params.put("team_field", getAPPCustomField(PluginInitializer.TEAM_FIELD_NAME, PluginInitializer.TEAM_FIELD_DESC));
        params.put("start_field", getAPPCustomField(PluginInitializer.FROM_FIELD_NAME, PluginInitializer.FROM_FIELD_DESC));
        params.put("end_field", getAPPCustomField(PluginInitializer.END_FIELD_NAME, PluginInitializer.END_FIELD_DESC));

        response.setContentType("text/html;charset=utf-8");
        renderer.render("main_page.vm", params, response.getWriter());
    }

    /**
     * Returns custom field required for APP if exists
     * @param name
     * @param desc
     * @return
     */
    private CustomField getAPPCustomField(String name, String desc){
        Collection<CustomField> cFields = this.customFieldManager.getCustomFieldObjectsByName(name);
        for (CustomField field : cFields) {
            if (field.getDescription().matches(desc)) {
                return field;
            }
        }
        return null;

    }

}
