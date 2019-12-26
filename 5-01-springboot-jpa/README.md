# Spring Data JPA

JPA 是 Java Persistence API 的简称， 中文名是Java持久层API，是JDK5.0注解
或XML描述对象-关系表的映射关系，并将运行期的实体对象持久化到数据库中。

JPA 是一种数据接口规范标准， Hibernate、Toplink 与 OpenJPA 等具体框架是对 JPA 的技术实现。

## Hibernate 优缺点
Hibernate优点：
- 面向对象
- 更好的一致性
- 开发效率高

Hibernate缺点：
- 运行效率慢
- 结构臃肿
- JPQL/HQL存在硬伤
使用建议：
- 建议Hibernate用在用户量不大，或需要敏捷开发的企业级应用
- 互联网项目慎用、慎用、慎用！！！
