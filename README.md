# HMall 商城系统

## 📖 项目简介

HMall 是一个基于 Spring Boot + Vue.js 的现代化电商系统，采用微服务架构设计，支持 Docker 容器化部署。

### ✨ 主要特性

- 🛒 **完整的电商功能**：商品管理、购物车、订单处理、支付集成
- 👥 **多端支持**：用户端、管理端、刷新管理端
- 🔐 **安全认证**：JWT 令牌认证，权限控制
- 🐳 **容器化部署**：Docker + Docker Compose 一键部署
- 📱 **响应式设计**：支持多设备访问
- 🔍 **搜索功能**：商品搜索和分类管理

## 🚀 快速开始

### 环境要求

- Docker 20.10+
- Docker Compose 2.0+
- 4GB+ 可用内存

### 一键部署

```bash
# 克隆项目
git clone <repository-url>
cd hmall-docker

# 启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps
```

### 访问地址

| 服务 | 地址 | 说明 |
|------|------|------|
| 用户端 | http://localhost:18080 | 商城前台 |
| 管理端 | http://localhost:18081 | 后台管理 |
| 刷新管理端 | http://localhost:18082 | 数据刷新管理 |
| 后端API | http://localhost:8080 | RESTful API |
| Redis | localhost:6379 | 缓存服务 |

## 📁 项目结构

```
hmall-docker/
├── docker-compose.yaml          # Docker 编排配置
├── README.md                    # 项目说明
├── API.md                       # API 文档
├── .gitignore                   # Git 忽略文件
├── hmall-nginx/                 # Nginx 配置和静态文件
│   ├── conf/
│   │   └── nginx.conf          # Nginx 配置文件
│   ├── html/                    # 前端静态资源
│   │   ├── hmall-portal/       # 用户端
│   │   ├── hmall-admin/        # 管理端
│   │   └── hm-refresh-admin/   # 刷新管理端
│   └── logs/                    # Nginx 日志
└── hmall/                       # 后端服务
    ├── hm-common/              # 公共模块
    ├── hm-service/             # 业务服务
    └── hmall.sql              # 数据库脚本
```

## 🛠️ 开发指南

### Java 项目打包

```bash
# 进入后端项目目录
cd hmall

# 清理并打包（跳过测试）
mvn clean package -DskipTests

# 或者完整打包（包含测试）
mvn clean package

# 查看打包结果
ls -la hm-service/target/
```

### 打包后部署

```bash
# 停止现有容器
docker-compose down

# 清理旧的 target 目录（如果存在权限问题）
sudo rm -rf hmall/hm-service/target

# 重新打包
cd hmall && mvn clean package -DskipTests

# 启动容器（会自动挂载新的 jar 包）
cd .. && docker-compose up -d

# 查看服务状态
docker-compose ps
```

### 自动化打包脚本

创建 `build-and-deploy.sh` 脚本：

```bash
#!/bin/bash
echo "开始构建和部署..."

# 停止容器
docker-compose down

# 清理并打包
cd hmall
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "打包失败！"
    exit 1
fi

# 启动容器
cd ..
docker-compose up -d

echo "部署完成！"
echo "访问地址："
echo "- 用户端: http://localhost:18080"
echo "- 管理端: http://localhost:18081"
echo "- 后端API: http://localhost:8080"
```

### 本地开发环境

```bash
# 启动后端服务
cd hmall
mvn spring-boot:run

# 启动前端服务（可选）
# 前端文件已预编译，直接通过 Nginx 访问
```

### 数据库初始化

```bash
# 导入数据库脚本
mysql -u root -p < hmall/hmall.sql
```

## 📋 功能模块

### 用户端 (hmall-portal)
- 用户注册/登录
- 商品浏览和搜索
- 购物车管理
- 订单创建和支付
- 个人中心

### 管理端 (hmall-admin)
- 用户管理
- 商品管理
- 订单管理
- 数据统计

### 刷新管理端 (hm-refresh-admin)
- 数据刷新
- 缓存管理
- 系统监控

## 🔧 配置说明

### 端口配置

| 服务 | 容器端口 | 主机端口 | 说明 |
|------|----------|----------|------|
| Nginx | 80 | 18080-18082 | 前端服务 |
| Spring Boot | 8080 | 8080 | 后端API |

### 环境变量

```yaml
# docker-compose.yaml 中的环境变量
SPRING_PROFILES_ACTIVE: dev
MYSQL_HOST: mysql
MYSQL_PORT: 3306
```

## 🐛 故障排除

### 常见问题

1. **端口冲突**
   ```bash
   # 检查端口占用
   lsof -i :18080
   
   # 修改 docker-compose.yaml 中的端口映射
   ```

2. **服务启动失败**
   ```bash
   # 查看详细日志
   docker-compose logs -f
   
   # 重新构建
   docker-compose down
   docker-compose up -d --build
   ```

3. **数据库连接问题**
   ```bash
   # 检查数据库服务状态
   docker-compose ps mysql
   
   # 查看数据库日志
   docker-compose logs mysql
   ```

### 日志查看

```bash
# 查看所有服务日志
docker-compose logs

# 查看特定服务日志
docker-compose logs nginx
docker-compose logs hm-service

# 实时查看日志
docker-compose logs -f
```

## 📝 常用命令

```bash
# 服务管理
docker-compose up -d              # 启动服务
docker-compose down               # 停止服务
docker-compose restart            # 重启服务
docker-compose ps                 # 查看状态

# 日志管理
docker-compose logs               # 查看日志
docker-compose logs -f            # 实时日志
docker-compose logs --tail=100    # 查看最近100行

# 容器操作
docker-compose exec nginx bash    # 进入容器
docker-compose exec hm-service bash
docker-compose exec redis redis-cli -a 123456  # 进入Redis

# 更新部署
docker-compose pull               # 拉取最新镜像
docker-compose up -d --build      # 重新构建并启动

# Nginx 构建（生产环境）
./build-nginx.sh                  # 构建自定义nginx镜像
```

## 🔄 更新部署

### 代码更新
```bash
# 拉取最新代码
git pull

# 重新构建并启动
docker-compose down
docker-compose up -d --build
```

### Nginx 部署模式切换

#### 开发模式（默认）
- 使用卷挂载，文件修改即时生效
- 便于调试和开发

#### 生产模式
```bash
# 1. 构建自定义nginx镜像
./build-nginx.sh

# 2. 修改 docker-compose.yaml
# 注释掉 nginx 的 volumes 配置
# 取消注释 build 配置

# 3. 重新启动服务
docker-compose up -d
```

### 配置更新
```bash
# 更新 Nginx 配置后重启
docker-compose restart nginx

# 更新应用配置后重启
docker-compose restart hm-service
```

## 📚 相关文档

- [API 文档](./API.md) - 详细的 API 接口说明
- [部署指南](./docs/deployment.md) - 详细的部署说明
- [开发指南](./docs/development.md) - 开发环境搭建

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 📞 联系我们

- 项目主页：[GitHub Repository](https://github.com/JeanAulis/hmall-docker)
- 问题反馈：[Issues](https://github.com/JeanAulis/hmall-docker/issues)
- 邮箱：null

---

**注意**：本项目仅供学习和演示使用，生产环境部署请根据实际需求调整配置。 
