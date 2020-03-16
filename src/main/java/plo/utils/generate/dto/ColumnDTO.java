package plo.utils.generate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 数据库列信息
 * @since 2020-03-05 14:33
 */
@Data
@Accessors(chain = true)
public class ColumnDTO {
    /**
     * 列名
     */
    private String name;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 列注释
     */
    private String comment;
    /**
     * 键
     */
    private String columnKey;

    private String extra;
    // ======================= 拓展字段 =======================

    /**
     * 属性名称(第一个字母大写)，如：user_name => UserName
     */
    private String upperAttrName;

    /**
     * 属性名称(第一个字母小写)，如：user_name => userName
     */
    private String lowerAttrName;

    /**
     * 属性类型
     */
    private String attrType;

    /**
     * 版本控制
     */
    private Boolean version;

    /**
     * 逻辑删除标识
     */
    private Boolean delFlag;

    public ColumnDTO() {
        delFlag = version = false;
    }
}
