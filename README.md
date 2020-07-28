# Generator
根据数据库表结构和模版代码生成代码的工具。

## 如何使用

### 模版解析器
模版解析器采用velocity，因此在模版中需要使用velocity语法。
当然可以实现`TemplateEngine`来扩展模版引擎。

### 数据库支持
目前仅支持mysql，可以实现`DataBase`来扩展数据库支持

### 内置变量
这些变量可以在模版中和文件路径中使用

#### 基本信息
- targetProject 生成代码根目录
- basePackage 基础包名
- moduleName 模块名
- author 作者

#### 数据库相关

##### Table 表信息对象
- tableName 表名

##### Column列信息对象  
- columnName 列名


#### 其他
- now 生成代码的时间

### 内置工具类
- dateFormat org.apache.commons.lang3.time.DateFormatUtils.DateFormatUtils