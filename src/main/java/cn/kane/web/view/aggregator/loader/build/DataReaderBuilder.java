package cn.kane.web.view.aggregator.loader.build;

import org.apache.commons.lang3.StringUtils;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.pojo.model.DataReader;
import cn.kane.web.view.aggregator.service.inner.DataReadService;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.aggregator.util.groovy.GroovyBeanFcatory;



public class DataReaderBuilder implements ResourceBuilder<DataReader> {

    /** Splitor of Groovy-class-name */
    private static final String GROOVY_CLASSNAME_SPLITOR = "_" ;
    
    private GroovyBeanFcatory groovyBeanFactory ;
    private StringResourceDefinitionManager stringResourceDefinitionManager ;
    
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
        StringResourceDefinition definition = stringResourceDefinitionManager.get(key) ;
        if(null == definition){
            return null ;
        }
        return definition.getContent() ;
    }
    
    public void setGroovyBeanFactory(GroovyBeanFcatory groovyBeanFactory) {
        this.groovyBeanFactory = groovyBeanFactory;
    }

    public void setStringResourceDefinitionManager(StringResourceDefinitionManager stringResourceDefinitionManager) {
        this.stringResourceDefinitionManager = stringResourceDefinitionManager;
    }

}
