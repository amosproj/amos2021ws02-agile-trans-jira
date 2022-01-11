package de.tuberlin.amos.gr2.impl;


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
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import org.springframework.beans.factory.InitializingBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@ExportAsService({PluginInitializer.class})
@Named("pluginInitializer")
public class PluginInitializer implements InitializingBean {

    @ComponentImport
    private final CustomFieldManager customFieldManager;

    @ComponentImport
    private final FieldScreenManager fieldScreenManager;

    @Inject
    public PluginInitializer(CustomFieldManager customFieldManager,
                          FieldScreenManager fieldScreenManager) {
        this.customFieldManager = customFieldManager;
        this.fieldScreenManager = fieldScreenManager;
    }

    //For conventions, see
    static final String TEAM_FIELD_NAME = "Associated Team";
    static final String TEAM_FIELD_SEARCHER_NAME = "com.atlassian.jira.plugin.system.customfieldtypes:textfield\"";
    static final String TEAM_FIELD_TYPE_NAME = "com.atlassian.jira.plugin.system.customfieldtypes:textfield";
    static final String TEAM_FIELD_DESC = "Agile Planning Team customfield used by the Agile Platform plugin. Do not edit this field.";

    @Override
    public void afterPropertiesSet() throws Exception {

        boolean team_found = false;

        Collection<CustomField> cFields = this.customFieldManager.getCustomFieldObjectsByName(PluginInitializer.TEAM_FIELD_NAME);

        //Match desciption to check if this is "our field"
        for(CustomField field : cFields){
            if (field.getDescription().matches(TEAM_FIELD_DESC)){
                team_found = true;
            }
        }

        if(!team_found){

            List<IssueType> issueTypes = new ArrayList<>();
            issueTypes.add(null);

            //Create a list of project contexts for which the custom field needs to be available
            List<JiraContextNode> contexts = new ArrayList<>();
            contexts.add(GlobalIssueContext.getInstance());

            CustomFieldType  type = this.customFieldManager.getCustomFieldType(TEAM_FIELD_TYPE_NAME);
            CustomFieldSearcher searcher = this.customFieldManager.getCustomFieldSearcher(TEAM_FIELD_SEARCHER_NAME);

            //Add custom field
            CustomField cField = this.customFieldManager.createCustomField( TEAM_FIELD_NAME, TEAM_FIELD_DESC, type, searcher, contexts, issueTypes);

            // Add field to default Screen
            FieldScreen defaultScreen = fieldScreenManager.getFieldScreen
                    (FieldScreen.DEFAULT_SCREEN_ID);
            if (!defaultScreen.containsField(cField.getId())) {
                FieldScreenTab firstTab = defaultScreen.getTab(0);
                firstTab.addFieldScreenLayoutItem(cField.getId());
            }
        }
    }
}
