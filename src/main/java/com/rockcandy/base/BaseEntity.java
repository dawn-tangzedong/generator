
//
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * @author tangzedong
// * @apiNote 基础对象
// * @since 2018/12/13 16:17
// */
//@Data
//public class BaseEntity implements Serializable {
//
//    @ApiModelProperty("主键")
//    @TableId(type = IdType.ID_WORKER)
//    private Long id;
//
//    @ApiModelProperty("创建时间")
//    @TableField(fill = FieldFill.INSERT)
//    private Date creationTime;
//
//    @ApiModelProperty("修改时间")
//    @TableField(fill = FieldFill.UPDATE)
//    private Date modificationTime;
//
//}
