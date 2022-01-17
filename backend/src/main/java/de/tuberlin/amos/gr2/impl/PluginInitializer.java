package de.tuberlin.amos.gr2.impl;


import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.context.GlobalIssueContext;
import com.atlassian.jira.issue.context.JiraContextNode;
import com.atlassian.jira.issue.customfields.CustomFieldSearcher;
import com.atlassian.jira.issue.customfields.CustomFieldType;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.screen.FieldScreen;
import com.atlassian.jira.issue.fields.screen.FieldScreenManager;
import com.atlassian.jira.issue.fields.screen.FieldScreenTab;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.plugin.event.events.PluginEnabledEvent;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import de.tuberlin.amos.gr2.planner.main.MainPageServlet;
import org.ofbiz.core.entity.GenericEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@ExportAsService({PluginInitializer.class})
@Named("pluginInitializer")
public class PluginInitializer implements InitializingBean, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(PluginInitializer.class);

    @ComponentImport
    private final EventPublisher eventPublisher;

    @ComponentImport
    private final CustomFieldManager customFieldManager;

    @ComponentImport
    private final FieldScreenManager fieldScreenManager;

    @Inject
    public PluginInitializer(CustomFieldManager customFieldManager,
                          FieldScreenManager fieldScreenManager,
                             EventPublisher eventPublisher) {
        this.customFieldManager = customFieldManager;
        this.fieldScreenManager = fieldScreenManager;
        this.eventPublisher = eventPublisher;
    }


    static final String PLUGIN_KEY = "de.tuberlin.amos.gr2.backend";


    //For conventions, see
    static final String TEAM_FIELD_NAME = "Associated Team";
    static final String TEAM_FIELD_SEARCHER_NAME = "com.atlassian.jira.plugin.system.customfieldtypes:textfield\"";
    static final String TEAM_FIELD_TYPE_NAME = "com.atlassian.jira.plugin.system.customfieldtypes:textfield";
    static final String TEAM_FIELD_DESC = "Agile Planning Team customfield used by the Agile Platform plugin. Do not edit this field.";

    @Override
    public void afterPropertiesSet() {

        eventPublisher.register(this);
        log.debug("-----------------------------------------------------------Listen to Plugin Start Event");
    }

    @Override
    public void destroy() {
        eventPublisher.unregister(this);
    }

    @EventListener
    public void onPluginStarted(final PluginEnabledEvent pluginEnabledEvent)
    {
        log.debug("-------------------------------------------------------------------new Plugin is now started, key "+pluginEnabledEvent.getPlugin().getKey());
        String startUpPluginKey = pluginEnabledEvent.getPlugin().getKey();

        if (PLUGIN_KEY.equals(startUpPluginKey)){

            log.debug("Our plugin is now Started! Check existing Customfields");

            boolean team_found = false;

            Collection<CustomField> cFields = this.customFieldManager.getCustomFieldObjectsByName(PluginInitializer.TEAM_FIELD_NAME);

            //Match desciption to check if this is "our field"
            for(CustomField field : cFields){
                if (field.getDescription().matches(TEAM_FIELD_DESC)){
                    team_found = true;
                }
            }

            if(!team_found) {
                log.debug("Our Custom fields are not present. Try to set them up...");

                List<IssueType> issueTypes = new ArrayList<>();
                issueTypes.add(null);

                //Create a list of project contexts for which the custom field needs to be available
                List<JiraContextNode> contexts = new ArrayList<>();
                contexts.add(GlobalIssueContext.getInstance());

                CustomFieldType type = this.customFieldManager.getCustomFieldType(TEAM_FIELD_TYPE_NAME);
                CustomFieldSearcher searcher = this.customFieldManager.getCustomFieldSearcher(TEAM_FIELD_SEARCHER_NAME);

                //Add custom field
                CustomField cField = null;
                try {
                    cField = this.customFieldManager.createCustomField(TEAM_FIELD_NAME, TEAM_FIELD_DESC, type, searcher, contexts, issueTypes);
                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }

                // Add field to default Screen
                FieldScreen defaultScreen = fieldScreenManager.getFieldScreen
                        (FieldScreen.DEFAULT_SCREEN_ID);
                if (cField != null && !defaultScreen.containsField(cField.getId())) {
                    FieldScreenTab firstTab = defaultScreen.getTab(0);
                    firstTab.addFieldScreenLayoutItem(cField.getId());
                }

                log.debug("Success, shut down routine.");
            }

        }


    }
}
