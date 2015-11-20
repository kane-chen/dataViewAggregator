package cn.kane.web.view.aggregator.util;

import org.apache.commons.lang.StringUtils;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

public class DefinitionKeyUtils {

    private static final String SPLITOR = "O_O" ;
    
    public static String format(DefinitionKey key){
        if(null == key){
            return null ;
        }
        return new StringBuilder().append(key.getType()).append(SPLITOR)
                .append(key.getName()).append(SPLITOR)
                .append(key.getVersion())
                .toString();
    }
    
    public static String formatStr(String type,String name,String version){
    	if(StringUtils.isBlank(type) || StringUtils.isBlank(name) || StringUtils.isBlank(version)){
    		return null ;
    	}
    	return new StringBuilder().append(type).append(SPLITOR)
                .append(name).append(SPLITOR)
                .append(version)
                .toString();
    }
    
    public static DefinitionKey parse(String formatedKey){
        if(StringUtils.isBlank(formatedKey)){
            return null ;
        }
        String[] strArr = formatedKey.split(SPLITOR) ;
        if(strArr.length !=3 ){
            return null ;
        }
        DefinitionKey key = new DefinitionKey() ;
        key.setType(strArr[0]);
        key.setName(strArr[1]);
        key.setVersion(strArr[2]);
        return key ;
    }
    
}
