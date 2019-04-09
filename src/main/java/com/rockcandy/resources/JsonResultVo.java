//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//
///**
// * @author TangZedong
// * @apiNote 请求结果
// * @since 2018/9/4 16:20
// */
//@ApiModel("操作返回结果")
//public class JsonResultVo<T> {
//    private static final short SUCCESS_CODE = 0;
//    private static final short FAILED_CODE = -1;
//
//    private static final boolean SUCCESS_STATUS = true;
//    private static final boolean FAILED_STATUS = false;
//
//    private static final String NULL_MESSAGE = null;
//    private static final Object NULL_DATA = null;
//
//    @ApiModelProperty("操作结果代码")
//    private short code;
//    @ApiModelProperty("操作结果提示信息")
//    private String message;
//    @ApiModelProperty("操作结果是否成功")
//    private boolean success;
//    @ApiModelProperty("操作结果数据内容")
//    private T data;
//
//    // 构造方法
//    private JsonResultVo(short code, String message, boolean success, T data) {
//        this.code = code;
//        this.message = message;
//        this.success = success;
//        this.data = data;
//    }
//
//    // get方法
//    public short getCode() {
//        return code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    // 获取实例
//    public static <T> JsonResultVo getFailedInstance(short code, String message, T data) {
//        return new JsonResultVo(code, message, FAILED_STATUS, data);
//    }
//
//    public static JsonResultVo getFailedInstance(short code, String message) {
//        return getFailedInstance(code, message, NULL_DATA);
//    }
//
//    public static JsonResultVo getFailedInstance(String message) {
//        return getFailedInstance(FAILED_CODE, message);
//    }
//
//    public static <T> JsonResultVo getSuccessInstance(short code, String message, T data) {
//        return new JsonResultVo(code, message, SUCCESS_STATUS, data);
//    }
//
//    public static <T> JsonResultVo getSuccessInstance(short code, T data) {
//        return getSuccessInstance(code, NULL_MESSAGE, data);
//    }
//
//    public static <T> JsonResultVo getSuccessInstance(T data) {
//        return getSuccessInstance(SUCCESS_CODE, data);
//    }
//
//    public static <T> JsonResultVo getSuccessInstance(String msg) {
//        return getSuccessInstance(SUCCESS_CODE, msg, NULL_DATA);
//    }
//
//
//}
