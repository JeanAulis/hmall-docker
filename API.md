# HMall API 文档

## 📋 概述

HMall API 是基于 RESTful 设计原则的电商系统接口，提供完整的电商功能支持。

### 基础信息

- **基础URL**: `http://localhost:8080`
- **认证方式**: JWT Token
- **数据格式**: JSON
- **字符编码**: UTF-8

### 通用响应格式

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

### 状态码说明

| 状态码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 🔐 认证相关

### 请求头格式

需要认证的接口请在请求头中添加：
```
Authorization: Bearer <token>
```

---

## 🔐 认证接口

### 用户登录
- **提交方式**: `POST`
- **接口地址**: `/api/auth/login`
- **请求参数**:
```json
{
  "username": "string",
  "password": "string"
}
```
- **响应示例**:
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "id": 1,
      "username": "admin",
      "email": "admin@example.com",
      "role": "ADMIN"
    }
  }
}
```

### 用户注册
- **提交方式**: `POST`
- **接口地址**: `/api/auth/register`
- **请求参数**:
```json
{
  "username": "string",
  "password": "string",
  "email": "string",
  "phone": "string"
}
```
- **响应示例**:
```json
{
  "code": 200,
  "message": "注册成功",
  "data": {
    "id": 1,
    "username": "newuser",
    "email": "newuser@example.com"
  }
}
```

### 获取用户信息
- **提交方式**: `GET`
- **接口地址**: `/api/users/profile`
- **说明**: 获取当前登录用户的详细信息
- **响应示例**:
```json
{
  "code": 200,
  "message": "获取成功",
  "data": {
    "id": 1,
    "username": "admin",
    "email": "admin@example.com",
    "phone": "13800138000",
    "avatar": "http://example.com/avatar.jpg",
    "createTime": "2024-01-01T00:00:00Z"
  }
}
```

### 更新用户信息
- **提交方式**: `PUT`
- **接口地址**: `/api/users/profile`
- **请求参数**:
```json
{
  "email": "newemail@example.com",
  "phone": "13800138001",
  "avatar": "http://example.com/new-avatar.jpg"
}
```

## 🛍️ 商品管理

### 获取商品列表
- **提交方式**: `GET`
- **接口地址**: `/api/items`
- **查询参数**:
  - `page`: 页码 (默认: 1)
  - `size`: 每页数量 (默认: 10)
  - `category`: 分类ID
  - `keyword`: 搜索关键词
  - `minPrice`: 最低价格
  - `maxPrice`: 最高价格
  - `sort`: 排序方式 (price_asc, price_desc, create_time_desc)
- **响应示例**:
```json
{
  "code": 200,
  "message": "获取成功",
  "data": {
    "total": 100,
    "pages": 10,
    "current": 1,
    "size": 10,
    "records": [
      {
        "id": 1,
        "name": "商品名称",
        "price": 99.99,
        "originalPrice": 129.99,
        "image": "http://example.com/image.jpg",
        "category": "电子产品",
        "stock": 100,
        "sales": 50,
        "createTime": "2024-01-01T00:00:00Z"
      }
    ]
  }
}
```

### 获取商品详情
- **提交方式**: `GET`
- **接口地址**: `/api/items/{id}`
- **说明**: 根据商品ID获取商品详细信息
- **响应示例**:
```json
{
  "code": 200,
  "message": "获取成功",
  "data": {
    "id": 1,
    "name": "商品名称",
    "description": "商品详细描述",
    "price": 99.99,
    "originalPrice": 129.99,
    "images": [
      "http://example.com/image1.jpg",
      "http://example.com/image2.jpg"
    ],
    "category": {
      "id": 1,
      "name": "电子产品"
    },
    "stock": 100,
    "sales": 50,
    "specifications": [
      {
        "name": "颜色",
        "value": "黑色"
      },
      {
        "name": "尺寸",
        "value": "L"
      }
    ],
    "createTime": "2024-01-01T00:00:00Z"
  }
}
```

### 创建商品 (管理员)
- **提交方式**: `POST`
- **接口地址**: `/api/admin/items`
- **请求参数**:
```json
{
  "name": "商品名称",
  "description": "商品描述",
  "price": 99.99,
  "originalPrice": 129.99,
  "categoryId": 1,
  "stock": 100,
  "images": ["http://example.com/image1.jpg"],
  "specifications": [
    {
      "name": "颜色",
      "value": "黑色"
    }
  ]
}
```

### 更新商品 (管理员)
- **提交方式**: `PUT`
- **接口地址**: `/api/admin/items/{id}`
- **请求参数**: 同创建商品

### 删除商品 (管理员)
- **提交方式**: `DELETE`
- **接口地址**: `/api/admin/items/{id}`

## 🛒 购物车

### 获取购物车
- **提交方式**: `GET`
- **接口地址**: `/api/cart`
- **说明**: 获取当前用户的购物车商品列表
- **响应示例**:
```json
{
  "code": 200,
  "message": "获取成功",
  "data": [
    {
      "id": 1,
      "itemId": 1,
      "itemName": "商品名称",
      "itemImage": "http://example.com/image.jpg",
      "price": 99.99,
      "quantity": 2,
      "selected": true
    }
  ]
}
```

### 添加商品到购物车
- **提交方式**: `POST`
- **接口地址**: `/api/cart`
- **请求参数**:
```json
{
  "itemId": 1,
  "quantity": 2
}
```

### 更新购物车商品数量
- **提交方式**: `PUT`
- **接口地址**: `/api/cart/{id}`
- **请求参数**:
```json
{
  "quantity": 3
}
```

### 删除购物车商品
- **提交方式**: `DELETE`
- **接口地址**: `/api/cart/{id}`

### 清空购物车
- **提交方式**: `DELETE`
- **接口地址**: `/api/cart`

## 📦 订单管理

### 创建订单
- **提交方式**: `POST`
- **接口地址**: `/api/orders`
- **请求参数**:
```json
{
  "addressId": 1,
  "itemIds": [1, 2],
  "remark": "订单备注"
}
```
- **响应示例**:
```json
{
  "code": 200,
  "message": "订单创建成功",
  "data": {
    "orderId": "ORD202401010001",
    "totalAmount": 199.98,
    "payUrl": "http://example.com/pay"
  }
}
```

### 获取订单列表
- **提交方式**: `GET`
- **接口地址**: `/api/orders`
- **查询参数**:
  - `page`: 页码
  - `size`: 每页数量
  - `status`: 订单状态 (PENDING, PAID, SHIPPED, COMPLETED, CANCELLED)
- **响应示例**:
```json
{
  "code": 200,
  "message": "获取成功",
  "data": {
    "total": 10,
    "pages": 1,
    "current": 1,
    "size": 10,
    "records": [
      {
        "id": 1,
        "orderNo": "ORD202401010001",
        "status": "PENDING",
        "totalAmount": 199.98,
        "createTime": "2024-01-01T00:00:00Z",
        "items": [
          {
            "itemName": "商品名称",
            "price": 99.99,
            "quantity": 2
          }
        ]
      }
    ]
  }
}
```

### 获取订单详情
- **提交方式**: `GET`
- **接口地址**: `/api/orders/{id}`
- **说明**: 根据订单ID获取订单详细信息
- **响应示例**:
```json
{
  "code": 200,
  "message": "获取成功",
  "data": {
    "id": 1,
    "orderNo": "ORD202401010001",
    "status": "PENDING",
    "totalAmount": 199.98,
    "address": {
      "name": "张三",
      "phone": "13800138000",
      "address": "北京市朝阳区xxx街道"
    },
    "items": [
      {
        "itemName": "商品名称",
        "price": 99.99,
        "quantity": 2,
        "image": "http://example.com/image.jpg"
      }
    ],
    "createTime": "2024-01-01T00:00:00Z",
    "payTime": null,
    "shipTime": null,
    "completeTime": null
  }
}
```

### 取消订单
- **提交方式**: `PUT`
- **接口地址**: `/api/orders/{id}/cancel`

## 💳 支付管理

### 申请支付
- **提交方式**: `POST`
- **接口地址**: `/api/pay/apply`
- **请求参数**:
```json
{
  "orderId": 1,
  "payType": "ALIPAY"
}
```
- **响应示例**:
```json
{
  "code": 200,
  "message": "支付申请成功",
  "data": {
    "payOrderId": "PAY202401010001",
    "payUrl": "https://openapi.alipay.com/gateway.do?xxx",
    "qrCode": "data:image/png;base64,xxx"
  }
}
```

### 查询支付状态
- **提交方式**: `GET`
- **接口地址**: `/api/pay/status/{payOrderId}`
- **说明**: 根据支付订单ID查询支付状态
- **响应示例**:
```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "payOrderId": "PAY202401010001",
    "status": "SUCCESS",
    "payTime": "2024-01-01T00:00:00Z",
    "amount": 199.98
  }
}
```

## 📍 地址管理

### 获取地址列表
- **提交方式**: `GET`
- **接口地址**: `/api/addresses`
- **说明**: 获取当前用户的收货地址列表
- **响应示例**:
```json
{
  "code": 200,
  "message": "获取成功",
  "data": [
    {
      "id": 1,
      "name": "张三",
      "phone": "13800138000",
      "province": "北京市",
      "city": "北京市",
      "district": "朝阳区",
      "detail": "xxx街道xxx号",
      "isDefault": true
    }
  ]
}
```

### 添加地址
- **提交方式**: `POST`
- **接口地址**: `/api/addresses`
- **请求参数**:
```json
{
  "name": "张三",
  "phone": "13800138000",
  "province": "北京市",
  "city": "北京市",
  "district": "朝阳区",
  "detail": "xxx街道xxx号",
  "isDefault": true
}
```

### 更新地址
- **提交方式**: `PUT`
- **接口地址**: `/api/addresses/{id}`
- **请求参数**: 同添加地址

### 删除地址
- **提交方式**: `DELETE`
- **接口地址**: `/api/addresses/{id}`

### 设置默认地址
- **提交方式**: `PUT`
- **接口地址**: `/api/addresses/{id}/default`

## 🔍 搜索功能

### 商品搜索
- **提交方式**: `GET`
- **接口地址**: `/api/search`
- **查询参数**:
  - `keyword`: 搜索关键词
  - `category`: 分类ID
  - `minPrice`: 最低价格
  - `maxPrice`: 最高价格
  - `sort`: 排序方式
  - `page`: 页码
  - `size`: 每页数量
- **响应示例**:
```json
{
  "code": 200,
  "message": "搜索成功",
  "data": {
    "total": 50,
    "pages": 5,
    "current": 1,
    "size": 10,
    "records": [
      {
        "id": 1,
        "name": "商品名称",
        "price": 99.99,
        "image": "http://example.com/image.jpg",
        "category": "电子产品",
        "highlight": {
          "name": "商品<em>名称</em>"
        }
      }
    ]
  }
}
```

## 📊 数据统计 (管理员)

### 获取销售统计
- **提交方式**: `GET`
- **接口地址**: `/api/admin/stats/sales`
- **查询参数**:
  - `startDate`: 开始日期 (YYYY-MM-DD)
  - `endDate`: 结束日期 (YYYY-MM-DD)
  - `type`: 统计类型 (daily, weekly, monthly)
- **响应示例**:
```json
{
  "code": 200,
  "message": "获取成功",
  "data": {
    "totalSales": 9999.99,
    "totalOrders": 100,
    "totalUsers": 50,
    "chartData": [
      {
        "date": "2024-01-01",
        "sales": 999.99,
        "orders": 10
      }
    ]
  }
}
```

### 获取商品统计
- **提交方式**: `GET`
- **接口地址**: `/api/admin/stats/items`
- **响应示例**:
```json
{
  "code": 200,
  "message": "获取成功",
  "data": {
    "totalItems": 100,
    "lowStockItems": 5,
    "topSellingItems": [
      {
        "id": 1,
        "name": "热销商品",
        "sales": 100
      }
    ]
  }
}
```

## 🛠️ 系统管理

### 获取系统信息
- **提交方式**: `GET`
- **接口地址**: `/api/admin/system/info`
- **响应示例**:
```json
{
  "code": 200,
  "message": "获取成功",
  "data": {
    "version": "1.0.0",
    "uptime": "7天",
    "memory": "512MB",
    "disk": "10GB",
    "cpu": "2.5%"
  }
}
```

### 刷新缓存
- **提交方式**: `POST`
- **接口地址**: `/api/admin/cache/refresh`
- **请求参数**:
```json
{
  "type": "ALL" // ALL, ITEMS, USERS, ORDERS
}
```

---

## 📝 错误码说明

| 错误码 | 说明 | 解决方案 |
|--------|------|----------|
| 1001 | 用户名或密码错误 | 检查用户名和密码 |
| 1002 | 用户不存在 | 检查用户ID是否正确 |
| 1003 | 用户已存在 | 使用其他用户名 |
| 2001 | 商品不存在 | 检查商品ID是否正确 |
| 2002 | 商品库存不足 | 减少购买数量或选择其他商品 |
| 3001 | 订单不存在 | 检查订单ID是否正确 |
| 3002 | 订单状态不允许操作 | 检查订单当前状态 |
| 4001 | 支付失败 | 检查支付参数和网络连接 |
| 5001 | 地址不存在 | 检查地址ID是否正确 |

## 🔧 开发工具

### API 测试

推荐使用以下工具进行 API 测试：

- **Postman**: 功能强大的 API 测试工具
- **Insomnia**: 轻量级的 API 客户端
- **curl**: 命令行工具

### 示例请求

```bash
# 用户登录 (POST)
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'

# 获取商品列表 (GET)
curl -X GET "http://localhost:8080/api/items?page=1&size=10" \
  -H "Authorization: Bearer <token>"

# 添加商品到购物车 (POST)
curl -X POST http://localhost:8080/api/cart \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{"itemId":1,"quantity":2}'

# 更新购物车商品数量 (PUT)
curl -X PUT http://localhost:8080/api/cart/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{"quantity":3}'

# 删除购物车商品 (DELETE)
curl -X DELETE http://localhost:8080/api/cart/1 \
  -H "Authorization: Bearer <token>"
```

## 📞 技术支持

如有 API 相关问题，请联系：

- 邮箱：api-support@hmall.com
- 文档更新：每次 API 变更都会更新此文档
- 版本控制：API 版本通过 URL 路径控制

---

**注意**：本 API 文档会随着系统更新而更新，请定期查看最新版本。 