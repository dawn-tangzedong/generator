package com.rockcandy.common.handler;

import com.rockcandy.common.exception.ServiceException;
import com.rockcandy.common.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote 全局异常拦截处理
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    public JsonResult handleRRException(ServiceException e) {
        JsonResult r = new JsonResult();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());

        return r;
    }

    @ExceptionHandler(Exception.class)
    public JsonResult handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return JsonResult.error();
    }
}
