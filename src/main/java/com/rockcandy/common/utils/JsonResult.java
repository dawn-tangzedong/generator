package com.rockcandy.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote json响应对象
 * @date 2016年10月27日 下午9:59:27
 */
public class JsonResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public JsonResult() {
        put("code", 0);
    }

    public static JsonResult error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static JsonResult error(String msg) {
        return error(500, msg);
    }

    public static JsonResult error(int code, String msg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("code", code);
        jsonResult.put("msg", msg);
        return jsonResult;
    }

    public static JsonResult ok(String msg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("msg", msg);
        return jsonResult;
    }

    public static JsonResult ok(Map<String, Object> map) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.putAll(map);
        return jsonResult;
    }

    public static JsonResult ok() {
        return new JsonResult();
    }

    @Override
    public JsonResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
