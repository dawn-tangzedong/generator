package com.rockcandy;

import com.rockcandy.common.utils.GenUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tangzedong.programmer@gmail.com
 * @apiNote generator sever start
 * @since 2019/4/9 15:42
 */
@SpringBootApplication
public class GeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
        GenUtils.generatorCode();
        System.exit(0);
    }
}
