# HMall Nginx Dockerfile
# 作者: jeanAulis
# 日期: 2025-07-06

# 基于官方nginx镜像
FROM nginx:1.29.0

# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 复制nginx配置文件
COPY conf/nginx.conf /etc/nginx/nginx.conf

# 复制静态文件
COPY html/ /usr/share/nginx/html/

# 创建日志目录
RUN mkdir -p /var/log/nginx

# 暴露端口
EXPOSE 80 18080 18081 18082

# 启动nginx
CMD ["nginx", "-g", "daemon off;"]