package cn.kane.dynamicview.definition.entity;

import java.io.Serializable;
import java.util.List;

public class PageDefinition extends TemplateDefinition implements Serializable {

    private static final long serialVersionUID = -3023186254155823374L;
    /**
     * data-read-service-definitions
     */
    private List<DefinitionKey> dataReaderDefinitions ;
    /**
     * widget-definitions
     */
    private List<DefinitionKey> widgetDefinitions ;
    /**
     * layout-definition
     */
    private DefinitionKey layoutDefinition ;
    /**
     * css-definition
     */
    private DefinitionKey cssDefinition ;
    /**
     * js-definition
     */
    private DefinitionKey jsDefinition ;
    /**
     * status
     */
    private String status ;
    
    public List<DefinitionKey> getDataReaderDefinitions() {
        return dataReaderDefinitions;
    }
    
    public void setDataReaderDefinitions(List<DefinitionKey> dataReaderDefinitions) {
        this.dataReaderDefinitions = dataReaderDefinitions;
    }
    
    public List<DefinitionKey> getWidgetDefinitions() {
        return widgetDefinitions;
    }
    
    public void setWidgetDefinitions(List<DefinitionKey> widgetDefinitions) {
        this.widgetDefinitions = widgetDefinitions;
    }
    
    public DefinitionKey getLayoutDefinition() {
        return layoutDefinition;
    }
    
    public void setLayoutDefinition(DefinitionKey layoutDefinition) {
        this.layoutDefinition = layoutDefinition;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public DefinitionKey getCssDefinition() {
        return cssDefinition;
    }

    public void setCssDefinition(DefinitionKey cssDefinition) {
        this.cssDefinition = cssDefinition;
    }

    public DefinitionKey getJsDefinition() {
        return jsDefinition;
    }

    public void setJsDefinition(DefinitionKey jsDefinition) {
        this.jsDefinition = jsDefinition;
    }
    
}
