package plo.utils.generate.dto;

import lombok.Data;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 基类
 * @since 2020-03-05 10:40
 */
@Data
public class BasicBeanDTO {

    /**
     * 类名称
     */
    private String className;

    /**
     * 包路径
     */
    private String packagePath;
}
