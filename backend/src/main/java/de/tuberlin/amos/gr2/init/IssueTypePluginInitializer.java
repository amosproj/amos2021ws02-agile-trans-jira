package de.tuberlin.amos.gr2.init;

import com.atlassian.jira.avatar.AvatarManager;
import com.atlassian.jira.config.IssueTypeManager;
import com.atlassian.jira.icon.IconType;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.jira.issue.fields.config.manager.IssueTypeSchemeManager;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.lifecycle.LifecycleAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A class used for running functions on plugin init, e.g., add Request issue type
 */
@ExportAsService({LifecycleAware.class})
@Named("issueTypePluginInitializer")
public class IssueTypePluginInitializer implements LifecycleAware {
    private static final Logger log = LoggerFactory.getLogger(IssueTypePluginInitializer.class);

    @ComponentImport
    private final IssueTypeManager issueTypeManager;
    @ComponentImport
    private final IssueTypeSchemeManager issueTypeSchemeManager;
    @ComponentImport
    private final AvatarManager avatarManager;

    @Inject
    public IssueTypePluginInitializer(IssueTypeManager issueTypeManager,
                                      IssueTypeSchemeManager issueTypeSchemeManager,
                                      AvatarManager avatarManager) {
        this.issueTypeManager = issueTypeManager;
        this.issueTypeSchemeManager = issueTypeSchemeManager;
        this.avatarManager = avatarManager;
    }

    @Override
    public void onStart() {
        log.debug("Enabling plugin");
        addIssueTypeToScheme();
    }

    @Override
    public void onStop() {

    }

    /**
     * Adds a new issue type "Request" to the default Issue Type Scheme if none exists yet
     */
    private void addIssueTypeToScheme() {
        Collection<IssueType> issueTypes = issueTypeSchemeManager.getIssueTypesForDefaultScheme();
        if (issueTypes.stream().noneMatch(x -> x.getName().equals("Request"))) {
            log.debug("Request issue type not found");
            String name = "Request";
            String description = "Required for Agile Planning Platform.";
            long icon = avatarManager.getAllSystemAvatars(IconType.ISSUE_TYPE_ICON_TYPE).get(5).getId();
            FieldConfigScheme issueTypeScheme = issueTypeSchemeManager.getDefaultIssueTypeScheme();
            IssueType issueType = issueTypeManager.createIssueType(name, description, icon);
            Collection<String> options = new ArrayList<>();
            options.add(issueType.getId());
            issueTypeSchemeManager.update(issueTypeScheme,options);
            log.debug("Request issue type created");
        } else {
            log.debug("Request issue type already exists");
        }

    }


}