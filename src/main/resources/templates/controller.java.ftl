package ${packagePath.controllerPath};

<#-- 判断是否存在swagger注解，根据swagger引入包-->
<#if systemConfig.swagger>
import io.swagger.annotations.Api;
</#if>
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ${packagePath.servicePath}.${table.upperClassName}Service;


<#-- 对这个类的描述注解 -->
/**
 * @author ${systemConfig.author}
 * @apiNote ${table.comment}控制层
 * @since ${data?string("yyyy-MM-dd HH:mm:ss")}
 */
<#-- 类上注解 -->
<#if systemConfig.swagger>
@Api("${table.comment}控制层")
</#if>
@RestController
@RequestMapping("${pathConfig.moduleName}/${table.lowerClassname}")
<#-- 类 -->
public class ${table.upperClassName}Controller{

    <#-- 自动注入service层 -->
    private final ${table.upperClassName}Service ${table.lowerClassname}Service;

    <#-- 通过构造函数自动注入 -->
    public ${table.upperClassName}Controller(${table.upperClassName}Service ${table.lowerClassname}Service){
        this.${table.lowerClassname}Service = ${table.lowerClassname}Service;
    }

}
