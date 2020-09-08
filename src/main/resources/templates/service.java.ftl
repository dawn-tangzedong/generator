package ${packagePath.servicePath};

<#--导包-->
import org.springframework.stereotype.Service;
<#if systemConfig.springCache>
import org.springframework.cache.annotation.CacheConfig;
</#if>
<#if basicBeanConfig.service??>
import ${basicBeanConfig.service.packagePath};
import ${packagePath.mapperPath}.${table.upperClassName}Mapper;
import ${packagePath.domainPath}.${table.upperClassName};
</#if>

/**
 * @author ${systemConfig.author}
 * @apiNote ${table.comment}业务逻辑层
 * @since ${data?string("yyyy-MM-dd HH:mm:ss")}
 */
<#if systemConfig.springCache>
@CacheConfig(cacheNames = "AccountInfoService")
</#if>
@Service
public class ${table.upperClassName}Service <#if basicBeanConfig.service??>extends ${basicBeanConfig.service.className}<${table.upperClassName}Mapper,${table.upperClassName}></#if>{

}
