# generator
java基础代码生成器，基于mybatis-plus 3.0.5，根据配置文件直接生成代码，现支持Mysql、Oracle数据库生成

##启动方式
GeneratorApplication main方法启动

##配置文件描述
resources/template velocity模板，包含代码生成的模板

resources/application.yml 数据库等springboot基础配置

resources/application-config 代码生成等基础参数配置

**Generator.properties 数据库字段对应生成配置文件

##项目需要引入jar包
sagger（版本可以自定义）

    <dependency>
      <groupId>io.springfox</groupId>  
      <artifactId>springfox-swagger2</artifactId>  
      <version>2.7.0</version>  
    </dependency>  
    <dependency>  
      <groupId>io.springfox</groupId>  
      <artifactId>springfox-swagger-ui</artifactId>  
      <version>2.7.0</version>  
    </dependency>
lombok（版本可以自定义）

    <dependency>
	    <groupId>org.projectlombok</groupId>  
	    <artifactId>lombok</artifactId>  
	    <version>1.16.20</version>  
	    <scope>provided</scope>  
	</dependency>
mybatis-plus（版本应该是需要在3.0以上）

    <dependency>
        <groupId>com.baomidou</groupId>  
        <artifactId>mybatis-plus</artifactId>  
        <version>3.0.5</version>  
    </dependency>  
    <dependency>  
        <groupId>com.baomidou</groupId>  
        <artifactId>mybatis-plus-boot-starter</artifactId>  
        <version>3.0.5</version>  
    </dependency>
    
## 另外你可能还需要文件 java/com/rockcandy/resources 中的文件

包含基类 BaseEntity、DataEntity，包含常用字段id，creationTime、modificationTime、creationBy、modificationBy、delFlag、status、remakes这些字段生成工具不会自动生成

包含mybatis-plus的自动注入器 CommonMetaObjectHandler，对基类字段初始化，status默认初始值为1

包含controller层返回的视图对象 JsonResultVo

包含mybatis-plus的配置文件 MybatisPlusConfig

