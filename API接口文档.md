# HMall商城系统接口文档

## 系统概述

HMall是一个基于Spring Boot + Vue.js的电商系统，采用前后端分离架构，通过Nginx进行反向代理和静态资源服务。

### 系统架构
- **前端**: Vue.js + Element UI
- **后端**: Spring Boot + MyBatis Plus
- **数据库**: MySQL
- **部署**: Docker + Nginx
- **认证**: JWT Token

### 部署信息

#### 端口配置
- `80`: 默认Nginx页面
- `18080`: 商城前台 (hmall-portal)
- `18081`: 管理后台 (hmall-admin)
- `18082`: 刷新管理后台 (hm-refresh-admin)
- `8080`: 后端API服务

#### 访问地址
- 商城前台: http://localhost:18080
- 管理后台: http://localhost:18081
- 刷新管理后台: http://localhost:18082
- API接口: http://localhost:8080

## API接口列表

### 1. 用户管理接口

#### 1.1 用户登录
- **接口地址**: `/users/login`
- **提交方式**: `POST`
- **请求参数**:
```json
{
  "username": "string",
  "password": "string"
}
```
- **响应数据**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "userId": 1,
    "username": "string",
    "balance": 1000,
    "token": "string"
  }
}
```

#### 1.2 用户注册
- **接口地址**: `/users/register`
- **提交方式**: `POST`
- **请求参数**:
```json
{
  "username": "string",
  "password": "string",
  "confirmPassword": "string",
  "phone": "string"
}
```
- **响应数据**:
```json
{
  "code": 200,
  "message": "注册成功"
}
```

#### 1.3 获取当前用户信息
- **接口地址**: `/users/me`
- **提交方式**: `GET`
- **请求头**: `Authorization: Bearer {token}`
- **响应数据**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "userId": 1,
    "username": "string",
    "balance": 1000
  }
}
```

#### 1.4 用户登出
- **接口地址**: `/users/logout`
- **提交方式**: `POST`
- **请求头**: `Authorization: Bearer {token}`
- **响应数据**:
```json
{
  "code": 200,
  "message": "登出成功"
}
```

#### 1.5 测试接口（无需登录）
- **接口地址**: `/users/test`
- **提交方式**: `GET`
- **响应数据**:
```json
{
  "code": 200,
  "message": "测试接口调用成功"
}
```

#### 1.6 扣减余额
- **接口地址**: `/users/money/deduct`
- **提交方式**: `PUT`
- **请求参数**:
  - `pw`: 支付密码 (string)
  - `amount`: 支付金额 (integer)
- **响应数据**:
```json
{
  "code": 200,
  "message": "扣款成功"
}
```

### 2. 商品管理接口

#### 2.1 分页查询商品
- **接口地址**: `/items/page`
- **提交方式**: `GET`
- **请求参数**:
  - `pageNo`: 页码 (integer)
  - `pageSize`: 每页大小 (integer)
- **响应数据**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [
      {
        "id": 1,
        "name": "商品名称",
        "category": "分类",
        "brand": "品牌",
        "price": 100,
        "image": "图片URL",
        "spec": "规格信息",
        "status": 1
      }
    ],
    "total": 100
  }
}
```

#### 2.2 根据ID查询商品
- **接口地址**: `/items/{id}`
- **提交方式**: `GET`
- **请求参数**: 路径参数 `id`
- **响应数据**: 商品详情对象

#### 2.3 根据ID批量查询商品
- **接口地址**: `/items?ids={id1,id2,id3}`
- **提交方式**: `GET`
- **请求参数**: 查询参数 `ids` (多个ID用逗号分隔)
- **响应数据**: 商品列表

#### 2.4 新增商品
- **接口地址**: `/items`
- **提交方式**: `POST`
- **请求参数**:
```json
{
  "name": "商品名称",
  "category": "分类",
  "brand": "品牌",
  "price": 100,
  "stock": 1000,
  "image": "图片URL",
  "spec": "规格信息",
  "isAD": false
}
```

#### 2.5 更新商品
- **接口地址**: `/items`
- **提交方式**: `PUT`
- **请求参数**: 同新增商品

#### 2.6 更新商品状态
- **接口地址**: `/items/status/{id}/{status}`
- **提交方式**: `PUT`
- **请求参数**: 路径参数 `id` 和 `status`

#### 2.7 删除商品
- **接口地址**: `/items/{id}`
- **提交方式**: `DELETE`
- **请求参数**: 路径参数 `id`

#### 2.8 批量扣减库存
- **接口地址**: `/items/stock/deduct`
- **提交方式**: `PUT`
- **请求参数**:
```json
[
  {
    "itemId": 1,
    "num": 2
  }
]
```

### 3. 购物车接口

#### 3.1 添加商品到购物车
- **接口地址**: `/carts`
- **提交方式**: `POST`
- **请求参数**:
```json
{
  "itemId": 1,
  "num": 2
}
```

#### 3.2 查询购物车列表
- **接口地址**: `/carts`
- **提交方式**: `GET`
- **响应数据**:
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "itemId": 1,
      "itemName": "商品名称",
      "itemImage": "图片URL",
      "price": 100,
      "num": 2,
      "totalPrice": 200
    }
  ]
}
```

#### 3.3 更新购物车
- **接口地址**: `/carts`
- **提交方式**: `PUT`
- **请求参数**: 购物车对象

#### 3.4 删除购物车商品
- **接口地址**: `/carts/{id}`
- **提交方式**: `DELETE`
- **请求参数**: 路径参数 `id`

#### 3.5 批量删除购物车商品
- **接口地址**: `/carts?ids={id1,id2,id3}`
- **提交方式**: `DELETE`
- **请求参数**: 查询参数 `ids`

### 4. 订单管理接口

#### 4.1 创建订单
- **接口地址**: `/orders`
- **提交方式**: `POST`
- **请求参数**:
```json
{
  "addressId": 1,
  "itemIds": [1, 2, 3],
  "payType": 1
}
```
- **响应数据**: 订单ID

#### 4.2 根据ID查询订单
- **接口地址**: `/orders/{id}`
- **提交方式**: `GET`
- **请求参数**: 路径参数 `id`
- **响应数据**: 订单详情

#### 4.3 标记订单已支付
- **接口地址**: `/orders/{orderId}`
- **提交方式**: `PUT`
- **请求参数**: 路径参数 `orderId`

### 5. 收货地址接口

#### 5.1 查询当前用户地址列表
- **接口地址**: `/addresses`
- **提交方式**: `GET`
- **响应数据**: 地址列表

#### 5.2 根据ID查询地址
- **接口地址**: `/addresses/{addressId}`
- **提交方式**: `GET`
- **请求参数**: 路径参数 `addressId`
- **响应数据**: 地址详情

### 6. 支付接口

#### 6.1 生成支付单
- **接口地址**: `/pay-orders`
- **提交方式**: `POST`
- **请求参数**:
```json
{
  "orderId": 1,
  "payType": 1
}
```
- **响应数据**: 支付单号

#### 6.2 尝试基于用户余额支付
- **接口地址**: `/pay-orders/{id}`
- **提交方式**: `POST`
- **请求参数**: 路径参数 `id` 和支付表单数据

### 7. 搜索接口

#### 7.1 搜索商品
- **接口地址**: `/search/list`
- **提交方式**: `GET`
- **请求参数**:
  - `key`: 搜索关键词
  - `pageNo`: 页码
  - `pageSize`: 每页大小
- **响应数据**: 分页商品列表

## 认证机制

### JWT Token认证
- 用户登录后获取JWT Token
- 后续请求在Header中携带: `Authorization: Bearer {token}`
- 管理后台请求携带: `Authorization: admin`

### 拦截器配置
- 登录拦截器验证用户身份
- 管理后台需要管理员权限

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 部署说明

### Docker部署
```bash
# 启动服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs nginx
```

### 手动部署
1. 启动后端服务 (端口8080)
2. 配置Nginx反向代理
3. 访问对应端口的前端页面

## 注意事项

1. 所有API请求需要携带正确的认证信息
2. 管理后台接口需要管理员权限
3. 商品状态: 1-正常, 2-下架
4. 支付类型: 1-微信支付, 2-支付宝
5. 用户状态: 1-正常, 2-禁用

## 测试说明

### 测试用户账号
系统启动时会自动创建以下测试账号：

| 用户名 | 密码 | 角色 | 余额 |
|--------|------|------|------|
| test | 123456 | 普通用户 | 10000 |
| admin | admin123 | 管理员 | 50000 |
| user | 123456 | 普通用户 | 2000 |

### 登录测试页面
访问 `http://localhost/test-login.html` 可以使用测试页面进行登录功能测试。

### 测试步骤
1. 启动后端服务
2. 访问测试页面
3. 使用上述测试账号进行登录测试
4. 测试获取用户信息、登出等功能

## 更新日志

- v1.0.0: 初始版本，包含基础商城功能
- 支持用户管理、商品管理、购物车、订单、支付等核心功能
- 提供管理后台和用户前台两个界面
- v1.1.0: 完善登录功能，添加用户注册、登出、获取用户信息等接口 