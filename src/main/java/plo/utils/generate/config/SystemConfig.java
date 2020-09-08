package plo.utils.generate.config;

import lombok.Data;
import lombok.experimental.Accessors;
import plo.utils.generate.enums.GenerateModuleEnums;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 系统配置
 * @since 2020-03-05 11:37
 */
@Data
@Accessors(chain = true)
public class SystemConfig {

    /**
     * doc注解中对@author的描述
     */
    private String author;

    /**
     * 是否使用缓存注解
     */
    private Boolean springCache;

    /**
     * 是否使用shiro权限注解
     */
    private Boolean authority;

    /**
     * 是否使用swagger注解
     */
    private Boolean swagger;

    /**
     * 生成指定模块，不指定的时候为生成所有
     */
    private GenerateModuleEnums[] modules;

    /**
     * 初始化函数，所有注解均不使用
     */
    private void init() {
        swagger = authority = springCache = false;
        if (modules == null || modules.length == 0) {
            modules = GenerateModuleEnums.values();
        }
    }

    /**
     * 构造函数，函数调用初始化
     */
    public SystemConfig() {
        init();
    }

    public void setModules(GenerateModuleEnums[] modules) {
        this.modules = modules == null || modules.length == 0 ? GenerateModuleEnums.values() : modules;
    }
}
