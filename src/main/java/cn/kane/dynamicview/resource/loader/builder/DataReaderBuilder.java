package cn.kane.dynamicview.resource.loader.builder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.entity.TemplateDefinition;
import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;
import cn.kane.dynamicview.resource.entity.DataReader;
import cn.kane.dynamicview.resource.loader.builder.groovy.GroovyBeanFcatory;
import cn.kane.dynamicview.resource.service.DataReadService;


public class DataReaderBuilder implements ResourceBuilder<DataReader> {

    /** Splitor of Groovy-class-name */
    private static final String GROOVY_CLASSNAME_SPLITOR = "_" ;
    @Autowired
    private GroovyBeanFcatory groovyBeanFactory ;
    @Autowired
    private TemplateDefinitionManager templateDefinitionManager ;
    
    @Override
    public DataReader build(DefinitionKey definitionKey) {
        if(null == definitionKey){
            return null;
        }
        return this.buildResourceInstance(definitionKey) ;
    }
    
    private DataReader buildResourceInstance(DefinitionKey definitionKey) {
        //source-code
        String sourceCode = this.getContentByKey(definitionKey) ;
        if(StringUtils.isBlank(sourceCode)){
            return null ;
        }
        //class.newInstance
        String groovyClassName = this.getNameByDefinitionKey(definitionKey) ;
        DataReadService dataReadService = groovyBeanFactory.getBeanIntance(groovyClassName, sourceCode, DataReadService.class) ;
        if(null == dataReadService){
            return null ;
        }
        //init
        DataReader dataReader = new DataReader() ;
        dataReader.setDefinitionKey(definitionKey);
        dataReader.setSourceCode(sourceCode);
        dataReader.setDataReadService(dataReadService);
        return dataReader ;
    }

    private String getNameByDefinitionKey(DefinitionKey definitionkey){
        return new StringBuilder().append(definitionkey.getType()).append(GROOVY_CLASSNAME_SPLITOR)
                .append(definitionkey.getName()).append(GROOVY_CLASSNAME_SPLITOR)
                .append(definitionkey).append(GROOVY_CLASSNAME_SPLITOR)
                .toString() ;
    }

    private String getContentByKey(DefinitionKey key){
        if(null == key){
            return null ;
        }
        TemplateDefinition definition = templateDefinitionManager.get(key) ;
        if(null == definition){
            return null ;
        }
        return definition.getContent() ;
    }
    
}
