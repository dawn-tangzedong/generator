package ${packagePath.mapperPath};

<#--导包-->
<#if basicBeanConfig.mapper??>
import ${basicBeanConfig.mapper.packagePath};
import ${packagePath.domainPath}.${table.upperClassName};
</#if>

/**
 * @author ${systemConfig.author}
 * @apiNote ${table.comment}持久层
 * @since ${data?string("yyyy-MM-dd HH:mm:ss")}
 */
public interface ${table.upperClassName}Mapper <#if basicBeanConfig.mapper??>extends ${basicBeanConfig.mapper.className}<${table.upperClassName}></#if>{

}
