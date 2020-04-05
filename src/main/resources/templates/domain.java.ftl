package ${packagePath.domainPath};

<#--导包-->
<#if basicBeanConfig.domain??>
import ${basicBeanConfig.domain.packagePath};
</#if>
<#-- 判断是否存在swagger注解，根据swagger引入包-->
<#if systemConfig.swagger>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if table.hasBigDecimal>
import java.math.BigDecimal
</#if>
<#if table.hasVersion>
import com.baomidou.mybatisplus.annotation.Version;
</#if>
<#if table.hasDelFlag>
import com.baomidou.mybatisplus.annotation.TableLogic
</#if>
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author ${systemConfig.author}
 * @apiNote ${table.comment}实体
 * @since ${data?string("yyyy-MM-dd HH:mm:ss")}
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
<#if systemConfig.swagger>
@TableName("t_account")
@ApiModel("${table.comment}实体")
</#if>
public class ${table.upperClassName} <#if basicBeanConfig.domain??>extends ${basicBeanConfig.domain.className}</#if>{

<#list table.columns as column>
    <#-- 判断是否被忽略生成字段 -->
    <#if ((databaseConfig.ignoreAttributes![])?seq_contains(column.name)) == false>
    <#-- 判断是否使用swagger -->
    <#if systemConfig.swagger>
    @ApiModelProperty(value = "${column.comment}")
    </#if>
    <#if column.version>
    @Version
    </#if>
    <#if column.delFlag>
    @TableLogic
    </#if>
    <#-- 判断是否是主键 -->
    <#if column.name == table.pk.name>
    @TableId(value = "pk_uuid", type = IdType.UUID)
    <#else >
    @TableField("${column.name}")
    </#if>
    private ${column.attrType} ${column.lowerAttrName};

    </#if>
</#list>
}
