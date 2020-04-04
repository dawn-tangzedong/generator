### 介绍

#### 描述

java工具包：可支持自动生成xml/mapper/service/controller/domain/serviceFactory等于项目中，不同于生成到本地文件再copy到对应目录的生成工具，可有效的减少机械式开发作业。

#### 核心

基于项目架构：核心框架Spring / 持久层 MyBatis-Plus  

✔  支持生成项目到指定文件目录中，默认是引用本项目

✔  支持自定义是否生成swagger2的注解

✔  支持自定义是否生成Service层Spring-Cache的注解

✔  支持生成xml/mapper/service/controller/domain/serviceFactory等模块基础java代码`关于serviceFactory是为了解决service之间互相调用，互相依赖注入的问题`

✔  支持自定义domain/mapper/service的基类[[了解更多配置信息点这里！](https://gitee.com/Pitta-Brachyura/plo-utils-generate/blob/master/src/main/resources/application-config.yml)]

✔  支持Mysql、Oracle、SqlServer、PostgreSql等数据库

✔  表、列前缀多条件忽略，

✔  支持Version版本控制、delFlag逻辑删除字段配置

✔  支出忽略java bean基础属性

### 安装教程

1. maven项目加载到本地项目中`请在正式版本发布时删除该仓库`

```xml
<!-- 需要指定gitee仓库，和dependencies同级别 -->
<repositories>
    <repository>
        <id>gitee-maven</id>
        <url>https://gitee.com/Pitta-Brachyura/plo-utils-generate/tree/master/release</url>
    </repository>
</repositories>
<!-- 加载工具jir包 -->
<dependencies>
    <dependency>
        <groupId>cn.org.cnplo</groupId>
        <artifactId>plo-utils-generate</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

2. 下载jar包到本地 [[点击去下载！](https://gitee.com/Pitta-Brachyura/plo-utils-generate/raw/master/jar/plo-utils-generate-0.0.1-SNAPSHOT.jar)]

### 使用说明

#### 配置文件说明

新建`application-config.yml`文件在`resources`下。[[了解更多配置信息点这里！](https://gitee.com/Pitta-Brachyura/plo-utils-generate/blob/master/src/main/resources/application-config.yml)]

```yml
# 这里只描述重要参数
# 数据库相关配置
database:
  # 表前缀过滤，支持多过滤条件
  table-prefix:
    -
  # 列前缀过滤，支持多过滤条件
  column-prefix:
    -
  # 【！必须参数】生成表名称，支持多表同时生成
  generator-table-name:
    -
  # 方言，仅支持Mysql、Oracle、SqlServer、PostgreSql
  # 默认值：Mysql
  dialect: Mysql
mybatis:
  # 【！必须参数】数据库密码
  password:
  # 【！必须参数】数据库链接地址
  url:
  # 【！必须参数】数据库用户名
  username:
# 路径配置
path:
  # 项目路径，默认为项目的根目录
  root-path: /
  # 【！建议参数】包路径
  package-path:
  # 【！建议参数】模块名称
  module-name:
  # 支持xml/mapper/service/controller/domain/serviceFactory的自定义路径
  # 默认情况下是${rootPath} / ${packagePath} / ${moduleName} / module[mapper/service/domain...]
```

#### 生成代码

配置完成后，使用main方法执行生成函数

```java
// 执行文件生成
public static void main(String[] args) {
	GenerateUtils.generate();
}
```

