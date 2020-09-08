package ${packagePath.factoryPath};

import ${packagePath.servicePath}.*;
import org.springframework.stereotype.Component;

/**
 * @author ${author}
 * @apiNote ${module}工厂类
 * @since ${data?string("yyyy-MM-dd HH:mm:ss")}
 */
@Component
public class ${fileName}{
<#list tables as table>
    public static ${table.upperClassName}Service ${table.lowerClassname}Service;
</#list>

    public ${fileName}(<#list tables as table>${table.upperClassName}Service ${table.lowerClassname}Service<#if table_has_next>,</#if></#list>){
<#list tables as table>
        ${fileName}.${table.lowerClassname}Service = ${table.lowerClassname}Service;
</#list>
    }

}
