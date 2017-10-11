=======================本框架概述  auhtor：li_xiaodong、createdate：20160119--20160315========================================================
开发框架：spring、spring mvc、mybatis;
架构风格：RESTful;
架构工具：Apache Tomcat8.0、JDK8.0、Maven、SQLserver;
架构分层：应用端（PC端/IOS端/android端）、服务端（给应用端提供rest服务）;
架构分包：core全局核心包、 base基础工具包、common业务公共包、dto数据传输对象包;


一、MyBatis generator：代码自动生成插件，它自动扫描数据库中的所有表，并生成Bean类和对应的xml配置文件.
二、Apache ActiveMQ ：即时通讯服务 ：ActiveMQ 是Apache出品，最流行的、功能强大的即时通讯和集成模式的开源服务器。
三、EHCache：Java开源缓存框架，缓存在内存和磁盘存储可以伸缩到数G，Ehcache为大数据存储做过优化。
四、Apache Shiro： 是一个框架，用于身份验证和授权;
五、Mybatis PagingPlugin：分页插件;
六、quartz：开源作业调度框架。
七、AuditLogPlugin：全局操作表日志插件，支持拦截表配置；

八、
LogHelper：日志封装帮助类，错误日志，会被记入错误日志表。
UserInfoHolder：用户信息holder，用户后台程序使用;
SpringConfigHelper：Spring的ApplicationContext对象工具类；

FileHelper:文件帮助类，提供文件上传、预览、下载。

十、ApplicationFilter：过滤器，拦截所有request请求；

十一
appMsg.properties：后端msg配置文件；
systemCfg.properties：系统配置文件

十二、
IDao ： DAO类的基类接口；
BaseModel:model类的抽象基类;
BaseController：Controller 基类;
BaseService：调用REST服务类的基类;



十三、
ReturnView：应用端  返回 封装类;
ResultData：服务端  返回 封装类;
ReturnCode：共通返回-Code;
Constant：共通常量接口;

十四、

DateUtil：
JsonUtil：
SqlUtil：
StringUtil：
WebUtil：
MapToEntityConvertUtil：REST传输Map转对象类

十五、
common.js：前端ajax交互 共通js,提供前端全局性交互;

十六、接外部服务
FileUtil：文件服务

十七、
模块demo：student