package plo.utils.generate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 数据库表信息
 * @since 2020-03-05 17:37
 */
@Data
@Accessors(chain = true)
public class TableDTO {

    /**
     * 表名
     */
    private String name;

    /**
     * 标注是
     */
    private String comment;

    /**
     * 创建时间
     */
    private String createTime;

    private String engine;

    // ======================= 拓展字段 =======================

    /**
     * 主键
     */
    private ColumnDTO pk;

    /**
     * 类名(第一个字母大写)，如：sys_user => SysUser
     */
    private String upperClassName;

    /**
     * 类名(第一个字母小写)，如：sys_user => sysUser
     */
    private String lowerClassname;

    /**
     * 列
     */
    private List<ColumnDTO> columns;

    /**
     * 判断是否存在BigDecimal
     */
    private Boolean hasBigDecimal;

    /**
     * 判断是否存在版本控制字段
     */
    private Boolean hasVersion;

    /**
     * 判断是否存在逻辑删除字段
     */
    private Boolean hasDelFlag;

    public TableDTO() {
        this.hasBigDecimal = false;
        this.hasVersion = false;
        this.hasDelFlag = false;
    }
}
