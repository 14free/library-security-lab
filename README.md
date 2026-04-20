# 📚 图书管理系统 - 安全漏洞测试靶场
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-2.x-42b883.svg)](https://vuejs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)](https://www.mysql.com/)
[![Security](https://img.shields.io/badge/Security-Educational%20Use%20Only-red.svg)]()
> **⚠️ 警告：本项目仅用于安全学习与授权测试，严禁用于非法用途！**

## 📖 项目介绍

本项目是一个基于 SpringBoot + Vue 的图书管理系统，**故意植入了多个常见 Web 漏洞**，用于：
- 🔍 安全测试学习与实践
- 🎓 代码审计教学
- 💼 安全岗位面试项目展示
- 🛡️ 漏洞修复方案对比

## ⚠️ 免责声明

1. 本项目仅用于**安全学习与教育目的**，请在合法授权环境下使用
2. 严禁利用本项目中的漏洞代码对任何真实系统进行攻击
3. 如因使用本项目造成的任何法律后果，使用者自行承担全部责任
4. 作者不对本项目的使用承担任何法律责任

## 🛠️ 技术栈

### 后端
- Spring Boot 2.7.6
- MyBatis + tk.mybatis
- MySQL 8.0
- PageHelper 分页插件

### 前端
- Vue 2.6
- Element UI
- Axios
- Vue Router

## 🎯 漏洞列表

| 漏洞类型 | 危险等级 | 所在模块 | 文件位置 |
|---------|---------|---------|---------|
| SQL 注入 | 🔴 高危 | 图书搜索 | `BookMapper.java` |
| 水平越权 | 🔴 高危 | 借阅记录 | `BorrowRecordController.java` |
| 敏感信息泄露 | 🟡 中危 | 用户登录 | `UserController.java` |

## 🚀 快速开始

### 环境要求
- JDK 1.8+
- MySQL 8.0+
- Node.js 14+
- Maven 3.6+

### 后端启动
```bash
cd springboot
mvn clean install
java -jar target/springboot-0.0.1-SNAPSHOT.jar
```
或直接在 IDE 中运行 `SpringbootApplication.java`

### 前端启动
```bash
cd vue
npm install
npm run serve
```

### 数据库配置
修改 `springboot/src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/books
    username: root
    password: your_password
```

## 🧪 漏洞复现指南

### 1️⃣ SQL 注入（图书搜索）

**漏洞位置**：`BookMapper.java` 第 12 行

**测试步骤**：
1. 启动项目，访问 `http://localhost:8081`
2. 在图书搜索框输入：`' OR '1'='1' -- -`
3. 使用 Burp Suite 抓包修改参数：
   ```http
   GET /book/getBook?pageNum=1&bookName=%27%20OR%20%271%27%3D%271%27%20--%20- HTTP/1.1
   ```

**完整复现流程**：详见 [漏洞复现文档.md](./基于图书管理项目的漏洞复现.md)

### 2️⃣ 水平越权（借阅记录）

**漏洞位置**：`BorrowRecordController.java` 第 21-29 行

**测试步骤**：
1. 注册两个账号：`user1` (ID=1) 和 `user2` (ID=2)
2. 使用 `user1` 登录
3. 使用 Burp Suite 拦截 `/borrow/records?userId=1` 请求
4. 修改参数为 `userId=2`，成功获取 `user2` 的借阅记录

### 3️⃣ 敏感信息泄露

**漏洞位置**：`UserController.java` 第 18-33 行

**测试步骤**：
1. 登录任意账号
2. 查看登录接口响应：`/user/login`
3. 响应中包含明文密码，未做脱敏处理

## 🛡️ 漏洞修复方案

### SQL 注入修复
将 `${}` 改为 `#{}` 预编译参数：
```java
// ❌ 漏洞代码
@Select("SELECT * FROM books WHERE book_name LIKE '%${bookName}%'")

// ✅ 修复代码
@Select("SELECT * FROM books WHERE book_name LIKE #{bookName}")
```

### 水平越权修复
从 Session 获取真实用户身份，不信任前端参数：
```java
// ❌ 漏洞代码
List<BorrowRecord> records = service.getByUserId(request.userId);

// ✅ 修复代码
User currentUser = session.getAttribute("loginUser");
List<BorrowRecord> records = service.getByUserId(currentUser.getId());
```

### 敏感信息泄露修复
登录成功后对密码字段脱敏：
```java
loginUser.setPassword(null);  // 返回前清除密码
```

## 📁 项目结构
```
Library/
├── springboot/              # 后端代码
│   ├── src/main/java/
│   │   ├── controller/      # 控制器层
│   │   ├── service/         # 业务逻辑层
│   │   ├── mapper/          # 数据访问层
│   │   └── shujvku/         # 实体类
│   └── src/main/resources/
│       └── application.yml  # 配置文件
├── vue/                     # 前端代码
│   ├── src/views/           # 页面组件
│   └── src/router/          # 路由配置
├── images/                  # 文档截图
└── README.md                # 项目说明（本文件）
```

## 📚 学习资源
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)
- [SQL 注入原理详解](https://portswigger.net/web-security/sql-injection)
- [越权漏洞防范指南](https://owasp.org/www-community/controls/Access_Control)

## 🤝 贡献
欢迎提交 Issue 和 Pull Request！

## 📄 许可证
本项目仅用于学习目的，未经授权禁止商用。

---

**⭐ 如果这个项目对你有帮助，欢迎 Star！**
