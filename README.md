# HMall Docker 部署指南

## 📋 项目说明
这是一个基于Docker的HMall商城前端部署项目，使用nginx作为Web服务器。

## 🚀 快速部署

### 1. 启动服务
```bash
# 启动nginx服务
docker-compose up -d nginx

# 或者启动所有服务（包括后端）
docker-compose up -d
```

### 2. 查看服务状态
```bash
docker-compose ps
```

### 3. 查看日志
```bash
# 查看nginx日志
docker-compose logs nginx

# 实时查看日志
docker-compose logs -f nginx
```

### 4. 停止服务
```bash
docker-compose down
```

## 🌐 访问地址

- **默认页面**: http://localhost
- **用户端**: http://localhost:18080 (hmall-portal)
- **管理端**: http://localhost:18081 (hmall-admin)
- **刷新管理端**: http://localhost:18082 (hm-refresh-admin)
- **后端API**: http://localhost:8080 (如果启动后端服务)

## 📁 项目结构

```
hmall-docker/
├── docker-compose.yaml              # Docker编排文件
├── README.md                        # 说明文档
├── hmall-nginx/
│   ├── conf/
│   │   ├── nginx.conf              # 原始nginx配置
│   │   └── nginx-docker.conf       # Docker环境nginx配置
│   ├── html/                       # 前端静态文件
│   │   ├── hmall-portal/           # 用户端
│   │   ├── hmall-admin/            # 管理端
│   │   └── hm-refresh-admin/       # 刷新管理端
│   └── logs/                       # nginx日志
└── hmall/                          # 后端服务（可选）
    └── hm-service/
        └── target/
```

## ⚙️ 配置说明

### 端口映射
- `80`: 默认nginx欢迎页面
- `18080`: hmall-portal前端（用户端）
- `18081`: hmall-admin前端（管理端）
- `18082`: hm-refresh-admin前端（刷新管理端）

### 卷挂载
- `./hmall-nginx/html` → `/usr/share/nginx/html`: 静态文件
- `./hmall-nginx/logs` → `/var/log/nginx`: 日志文件
- `./hmall-nginx/conf/nginx-docker.conf` → `/etc/nginx/nginx.conf`: nginx配置

## 🔧 故障排除

### 1. 404错误
如果遇到404错误，请检查：
```bash
# 检查容器状态
docker-compose ps

# 检查nginx配置
docker exec nginx nginx -t

# 检查文件挂载
docker exec nginx ls -la /usr/share/nginx/html/
```

### 2. 端口冲突
如果端口被占用，修改 `docker-compose.yaml` 中的端口映射：
```yaml
ports:
  - "8080:80"      # 改为其他端口
```

### 3. 权限问题
如果遇到权限问题：
```bash
# 重新构建并启动
docker-compose down
docker-compose up -d --build
```

## 📝 常用命令

```bash
# 启动服务
docker-compose up -d

# 停止服务
docker-compose down

# 重启服务
docker-compose restart

# 查看日志
docker-compose logs

# 进入容器
docker-compose exec nginx bash

# 更新配置后重启
docker-compose restart nginx
```

## 🔄 更新部署

1. 更新静态文件后，无需重启容器
2. 更新nginx配置后，需要重启容器：
   ```bash
   docker-compose restart nginx
   ```

## 📞 技术支持

如果遇到问题，请检查：
1. Docker和Docker Compose版本
2. 文件路径是否正确
3. 端口是否被占用
4. nginx配置文件语法是否正确

