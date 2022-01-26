package de.tuberlin.amos.gr2.planner.settings.project;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.user.ApplicationUser;
import com.google.gson.Gson;
import main.java.de.tuberlin.amos.gr2.api.IssueManager;
import main.java.de.tuberlin.amos.gr2.api.JsonFormatTool;
import main.java.de.tuberlin.amos.gr2.impl.IssueManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueMockUpServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(de.tuberlin.amos.gr2.planner.settings.project.ProjectSettingsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {

        IssueManager issueManager = new IssueManagerImpl();
        JsonFormatTool jsontool = new JsonFormatTool();

        final PrintWriter w = res.getWriter();
        w.write("<h1>Issue Mockup Service - get issue for current user</h1>");

        ApplicationUser loggedInUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

        w.println("The logged in user name:  "+ loggedInUser+ "<br>");

        try {

            List<Issue> issues = issueManager.getIssuesInProject(loggedInUser);

            for (Issue issue : issues) {
                w.println("The Logged In User Got Issue:  " + issue.getKey() +"  Summary:  "+ issue.getSummary()+ " "+ "Assignee: "+ issue.getAssignee()+"  CreatedDate: "+ issue.getCreated()+  "  DueDate: "+ issue.getDueDate()+ "<br>");
            }

            List<Map> IssueInfoList = jsontool.getHashMap(issues);
            Gson gson = new Gson();
            String issueJson = gson.toJson(IssueInfoList);

            for(Map item:IssueInfoList){
                w.println(item.toString()+"<br>");
            }

            w.println("IssueJson:  "+issueJson+"<br>");

        } catch (SearchException e) {
            e.printStackTrace();
        }

        w.println("<br>Issue with empty assignee<br><br>");

        try {
            List<Issue> issueswith_empty_assignee = issueManager.getIssuesInProjectsWithEmptyUsers(loggedInUser);
            for (Issue issue : issueswith_empty_assignee) {
                w.println("Got Issue:  " + issue.getKey() +"  Summary:  "+ issue.getSummary()+ " "+ "Assignee: "+ issue.getAssignee()+"  CreatedDate: "+ issue.getCreated()+ "  DueDate: "+ issue.getDueDate()+ "<br>");
            }

        } catch (SearchException e) {
            e.printStackTrace();
        }

        log.debug("ProjectSettingsServlet loaded");
        res.setContentType("text/html;charset=utf-8");

        w.close();

    }

}
