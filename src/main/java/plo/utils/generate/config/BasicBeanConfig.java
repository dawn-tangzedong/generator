package plo.utils.generate.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import plo.utils.generate.dto.BasicBeanDTO;

import java.util.Objects;

/**
 * @author tangzedong.programmer@gamil.com
 * @apiNote 生成基类配置
 * @since 2020-03-05 10:28
 */
@Data
@Accessors(chain = true)
public class BasicBeanConfig {

    /**
     * 实体类基类
     */
    private String basicDomain;

    /**
     * 持久层基类
     */
    private String basicMapper;

    /**
     * 服务层基类
     */
    private String basicService;

    private BasicBeanDTO domain;
    private BasicBeanDTO mapper;
    private BasicBeanDTO service;

    private static String BASIC_SERVICE = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";
    private static String BASIC_MAPPER = "com.baomidou.mybatisplus.core.mapper.BaseMapper";

    /**
     * 初始化函数，初始化service的基类是 Mybatis-Plus的ServiceImpl
     */
    private void init() {
        if (StringUtils.isBlank(this.basicService)) {
            BasicBeanConfig.analyze(BASIC_SERVICE, this.service = new BasicBeanDTO());
        }
        if (StringUtils.isBlank(this.basicMapper)) {
            BasicBeanConfig.analyze(BASIC_MAPPER, this.mapper = new BasicBeanDTO());
        }
    }

    private static void analyze(String path, BasicBeanDTO bean) {
        if (Objects.isNull(bean)) {
            throw new RuntimeException("");
        }
        if (StringUtils.isBlank(path)) {
            return;
        }
        if (ObjectUtils.isEmpty(bean)) {
            bean = new BasicBeanDTO();
        }
        int lastPointIndex = path.lastIndexOf(".");
        bean.setPackagePath(path);
        bean.setClassName(path.substring(lastPointIndex + 1));
    }

    public BasicBeanConfig() {
        init();
    }

    public void setBasicDomain(String basicDomain) {
        analyze(basicDomain, this.domain = new BasicBeanDTO());
    }

    public void setBasicMapper(String basicMapper) {
        analyze(StringUtils.isBlank(basicMapper) ? BASIC_MAPPER : basicMapper, this.mapper = new BasicBeanDTO());
    }

    public void setBasicService(String basicService) {
        analyze(StringUtils.isBlank(basicService) ? BASIC_SERVICE : basicService, this.service = new BasicBeanDTO());
    }
}
