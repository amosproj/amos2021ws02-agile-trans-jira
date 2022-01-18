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
    static final String TEAM_FIELD_TYPE_NAME = "com.atlassian.jira.plugin.system.customfieldtypes:select";
    static final String TEAM_FIELD_DESC = "Agile Planning Team custom field used by the Agile Platform plugin. Do not edit this field.";

    static final String FROM_FIELD_NAME = "Start of Work";
    static final String FROM_FIELD_TYPE_NAME = "com.atlassian.jira.plugin.system.customfieldtypes:datepicker";
    static final String FROM_FIELD_DESC = "Agile Planning Start custom field used by the Agile Platform plugin. Do not edit this field.";

    static final String END_FIELD_NAME = "End of Work";
    static final String END_FIELD_TYPE_NAME = "com.atlassian.jira.plugin.system.customfieldtypes:datepicker";
    static final String END_FIELD_DESC = "Agile Planning End custom field used by the Agile Platform plugin. Do not edit this field.";


    @Override
    public void afterPropertiesSet() {

        eventPublisher.register(this);
        log.debug("Listen to Plugin Start Event");
    }

    @Override
    public void destroy() {
        eventPublisher.unregister(this);
    }

    @EventListener
    public void onPluginStarted(final PluginEnabledEvent pluginEnabledEvent) {
        log.debug("new Plugin is now started, key "+pluginEnabledEvent.getPlugin().getKey());
        String startUpPluginKey = pluginEnabledEvent.getPlugin().getKey();

        if (PLUGIN_KEY.equals(startUpPluginKey)) {

            log.debug("Our plugin is now Started! Check existing custom fields");

            if(this.customFieldNotPresent(PluginInitializer.TEAM_FIELD_NAME, PluginInitializer.TEAM_FIELD_DESC)){
                log.debug(TEAM_FIELD_NAME+" field not present, try set it up..");
                this.createCustomField(PluginInitializer.TEAM_FIELD_NAME, PluginInitializer.TEAM_FIELD_DESC, PluginInitializer.TEAM_FIELD_TYPE_NAME);
            }
            if(this.customFieldNotPresent(PluginInitializer.FROM_FIELD_NAME, PluginInitializer.FROM_FIELD_DESC)){
                log.debug(FROM_FIELD_NAME+" field not present, try set it up..");
                this.createCustomField(PluginInitializer.FROM_FIELD_NAME, PluginInitializer.FROM_FIELD_DESC, PluginInitializer.FROM_FIELD_TYPE_NAME);
            }
            if(this.customFieldNotPresent(PluginInitializer.END_FIELD_NAME, PluginInitializer.END_FIELD_DESC)){
                log.debug(END_FIELD_NAME+" field not present, try set it up..");
                this.createCustomField(PluginInitializer.END_FIELD_NAME, PluginInitializer.END_FIELD_DESC, PluginInitializer.END_FIELD_TYPE_NAME);
            }

            log.debug("Custom Field Check complete.");

            //TODO Further Initializations go here.

            log.debug("Success, shut down routine.");
        }
    }


    /**
     * Returns if a custom field is present in Jira or not, based on its name and description.
     * @param name The name of the custom field to search
     * @param desc The description to map
     * @return true, if valid custom Field has been found, otherwise false.
     *
     */
    private boolean customFieldNotPresent(String name, String desc) {


        Collection<CustomField> cFields = this.customFieldManager.getCustomFieldObjectsByName(name);

        //Match description to check if this is "our field"
        for (CustomField field : cFields) {
            if (field.getDescription().matches(desc)) {
                return false;
            }
        }
        return true;
    }

    /**
     *  Creates a new custom field in the Jira instance. The Context ist preset to global and cannot be modified.
     *  Furthermore, the Method adds the custom field to the apps default scheme.
     *
     * @param name Name of the Custom Field
     * @param desc Description of the Custom field
     * @param typeClass Class of the custom field Type to be created.
     */
    @SuppressWarnings("rawtypes")
    private void createCustomField(String name, String desc, String typeClass ){

        //Create empty List of available issue types.
        List<IssueType> issueTypes = new ArrayList<>();
        issueTypes.add(null);

        //Get the global project context to make custom fields available in all projects
        List<JiraContextNode> contexts = new ArrayList<>();
        contexts.add(GlobalIssueContext.getInstance());

        //Setup custom field type and searcher
        CustomFieldType type = this.customFieldManager.getCustomFieldType(typeClass);
        CustomFieldSearcher searcher = this.customFieldManager.getCustomFieldSearcher(typeClass);

        //Add custom field
        CustomField cField = null;

        try {
            cField = this.customFieldManager.createCustomField(name, desc, type, searcher, contexts, issueTypes);
        } catch (GenericEntityException e) {
            log.error("FATAL: Was unable to setup custom field "+name+", resulting error was:");
            log.error(e.toString());
        }

        // Add field to default Screen
        FieldScreen defaultScreen = fieldScreenManager.getFieldScreen(FieldScreen.DEFAULT_SCREEN_ID);
        if (cField != null && !defaultScreen.containsField(cField.getId())) {
            FieldScreenTab firstTab = defaultScreen.getTab(0);
            firstTab.addFieldScreenLayoutItem(cField.getId());
        }


    }


}
