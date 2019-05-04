package com.rockcandy.common.vo;

import lombok.Data;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote 基类配置名称
 * @since 2019/5/4 16:36
 */
@Data
public class BaseClassConfigVo {
    /**
     * 是否存在基类，默认fasle
     */
    private boolean exist = false;
    /**
     * 包相对路径
     */
    private String packageRelativePath;
    /**
     * 类名称
     */
    private String className;

    /**
     * 获取实例
     */
    private String getInstance;
}
