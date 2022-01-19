package main.java.de.tuberlin.amos.gr2.impl;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.query.Query;
import com.atlassian.query.order.SortOrder;
import main.java.de.tuberlin.amos.gr2.api.IssueManager;

import java.util.Collections;
import java.util.List;

public class IssueManagerImpl implements IssueManager {


    @Override
    public List<Issue>  getIssuesInProject(ApplicationUser user) throws SearchException {
        JqlQueryBuilder builder = JqlQueryBuilder.newBuilder();
        builder.where().assigneeIsCurrentUser();
//        builder.where().project("mckup").and().assigneeIsCurrentUser();
        builder.orderBy().assignee(SortOrder.ASC);
        Query query = builder.buildQuery();
        SearchService searchService = ComponentAccessor.getComponent(SearchService.class);
        SearchResults results = searchService.search(user, query, PagerFilter.getUnlimitedFilter());

        return results.getResults();

    }

    @Override
    public List<Issue> getIssuesInProjectsWithEmptyUsers(ApplicationUser user) throws SearchException {
        JqlQueryBuilder builder = JqlQueryBuilder.newBuilder();

        builder.where().sub().assigneeIsEmpty().or().reporterIsEmpty().endsub();
        builder.orderBy().assignee(SortOrder.ASC);
        Query query = builder.buildQuery();
        SearchService searchService = ComponentAccessor.getComponent(SearchService.class);
        SearchResults results = searchService.search(user, query, PagerFilter.getUnlimitedFilter());
        return results.getResults();
    }

    @Override
    public List<Issue> getIssuesInQuery(ApplicationUser user) throws SearchException {
        SearchService searchService = ComponentAccessor.getComponent(SearchService.class);
        String jqlQuery = "project = \"mckup\" and assignee = currentUser()";
        SearchService.ParseResult parseResult = searchService.parseQuery(user, jqlQuery);
        if (parseResult.isValid()) {
            Query query = parseResult.getQuery();

            //IssueSearchParameters params = SearchService.IssueSearchParameters.builder().query(query).build();
            //String queryPath = searchService.getIssueSearchPath(user, params);
            //System.out.println("Query Path:"+queryPath);

            SearchResults results = searchService.search(user, query, PagerFilter.getUnlimitedFilter());
            return results.getResults();
        } else {
            System.out.println("Error parsing query:" + jqlQuery);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Issue> getIssuesInProjectsForCustomer(ApplicationUser user) throws SearchException {
        JqlQueryBuilder builder = JqlQueryBuilder.newBuilder();
        builder.where().assignee().in("admin1", "admin").and().customField(10000L)
                .eq("admin");
        builder.orderBy().assignee(SortOrder.ASC);
        Query query = builder.buildQuery();
        SearchService searchService = ComponentAccessor.getComponent(SearchService.class);
        SearchResults results = searchService.search(user, query, PagerFilter.getUnlimitedFilter());
        return results.getResults();

    }
}
