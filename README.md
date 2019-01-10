# generator
java基础代码生成器（在renren开源项目上做了调整），基于mybatis-plus 3.0.5

# 项目需要引入jar包
swagger（版本可以自定义）

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
# 另外还需要文件 base、config 中的文件

包含基类 baseEnitiy，包含常用字段id，creationDate、modificationDate、creationBy、modificationBy、delFlag、status、remakes这些字段生成工具不会自动生成

包含mybatis-plus的自动注入器 CommonMetaObjectHandler，对基类字段初始化，status默认初始值为1

包含controller层返回的视图对象 JsonResultVo

包含mybatis-plus的配置文件 MybatisPlusConfig
# 需要调整的配置文件
在generator.properties文件修改你需要调整的信息，比如

tablePrefix 忽略表前缀

author 作者信息

package 包路径等

moduleName 模块名称

在application.yml文件修改你的数据连接信息
