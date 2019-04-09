//
//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableLogic;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
///**
// * @author tangzedong
// * @apiNote 基础对象子类，在原基础上增加creation by、modification by、remakes以及del_flag
// * @since 2018/12/29 17:20
// */
//@Data
//public class DataEntity extends BaseEntity {
//    @ApiModelProperty("创建人")
//    private Long creationBy;
//
//    @ApiModelProperty("修改人")
//    private Long modificationBy;
//
//    @ApiModelProperty("备注")
//    private String remakes;
//
//    @ApiModelProperty("删除标识(1删除 0正常)")
//    @TableField(fill = FieldFill.INSERT)
//    @TableLogic
//    private Boolean delFlag;
//}
