# SpringBoot 全栈监控系统
> Java + OSHI + Vue3 + ECharts 实现的实时CPU监控系统

## 📋 项目简介
本项目是一个基于 SpringBoot 的全栈监控系统，用于实时监控和分析 CPU 性能指标。通过 OSHI 库采集系统硬件信息，结合 Vue3 和 ECharts 实现可视化展示。

## 🛠️ 技术栈
技术	版本	说明
Java	17	后端运行环境
Spring Boot	4.0.1	Web 应用框架
OSHI-core	6.6.5	系统硬件信息采集
Vue 3	Latest	前端框架
ECharts	Latest	数据可视化图表
Axios	Latest	HTTP 客户端
Arco Design Vue	Latest	UI 组件库

## 🛠️ 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 后端运行环境 |
| Spring Boot | 4.0.1 | Web应用框架 |
| OSHI-core | 6.6.5 | 系统硬件信息采集 |
| Vue 3 | Latest | 前端框架 |
| ECharts |	Latest |数据可视化图表 |
| Axios | Latest | HTTP 客户端 |
| Arco Design Vue | Latest | UI 组件库 |

## 🏗️ 项目架构
```text
├── 📦 后端 (SpringBoot)
│   ├── 🏗️ 控制器层 (Controller)
│   ├── 🔧 服务层 (Service)
│   ├── 🗃️ 实体层 (Entity)
│   └── 🛠️ 工具类 (Utils)
│
├── 💻 前端 (Vue3)
│   ├── 📊 视图层 (Views)
│   ├── 🧩 组件层 (Components)
│   ├── 🔌 API 层
│   ├── 🛣️ 路由层
│   └── 📡 状态管理
│
└── 📁 文档资源
```

## 📁 项目结构
```text
oshi-app/
├── src/          
│   └── main/
│       └── java/
│           └── com/baojingyi/oshi/       # 后端源码
│               ├── common/                # 公共类
│               │   └── R.java            # 统一响应类
│               ├── controller/            # 控制器层
│               │   └── CpuLoadRestController.java
│               └── service/               # 服务层
│                   └── CpuLoadMetricsService.java
│
├── pom.xml                               # Maven 配置文件
│
├── frontend-app/                         # 前端项目
│   └── src/
│       ├── api/                          # API 接口
│       │   └── cpuLoadApi.js
│       ├── assets/                       # 静态资源
│       ├── components/                   # 公共组件
│       ├── http/                         # HTTP 配置
│       │   └── index.js
│       ├── router/                       # 路由配置
│       │   └── index.js
│       ├── stores/                       # 状态管理
│       ├── views/                        # 页面视图
│       │   ├── CPU/                      # CPU 监控页面
│       │   │   └── CPULoad.vue
│       │   ├── dropdown-button.vue       # 下拉按钮组件
│       │   ├── HomeView.vue              # 首页
│       │   └── Menu.vue                  # 菜单组件
│       ├── App.vue                       # 根组件
│       └── main.js                       # 入口文件
│
└── docs/                                 # 文档目录
    └── images/                           # 项目截图
        ├── metrics.png                   # API 接口返回数据
        ├── 首页.png                       # 系统首页
        ├── cpuload-10.png                # 10s监控视图
        └── cpuload-30.png                # 30s监控视图
```

## 🚀 快速开始
### 🎯后端部署

#### 1. 环境要求
* JDK 17+
* Maven 3.6+
* Spring Boot 4.0.1

#### 2. 部署
（1）. 创建springboot项目，勾选lombok和spring web  <br>
（2）. `https://mvnrepository.com/`在maven仓库中加载oshi-core依赖，添加到pom.xml中  <br>
（3）. 核心代码  <br>
```java
# 统一响应类 R.java
package com.baojingyi.oshi.common;
import lombok.Data;
@Data
public class R {
    private Integer code;
    private String msg;
    private Object data;

    public R(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static R ok(Object data){
        return new R(200,"操作成功",data);
    }

    public static R ok(){
        return new R(200,"操作成功",null);
    }

    public static R error(String msg){
        return new R(500,msg,null);
    }

    public static R error(){
        return new R(500,"操作失败",null);
    }

    public static R error(Integer code,String msg){
        return new R(code,msg,null);
    }
}
```
```java
# 控制器层 CpuLoadRestController.java
package com.baojingyi.oshi.controller;
import com.baojingyi.oshi.common.R;
import com.baojingyi.oshi.service.CpuLoadMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping ("/metrics")
@RestController
public class CpuLoadRestController {

    @Autowired
    CpuLoadMetricsService cpuLoadMetricsService;

    @GetMapping ("/cpuload")
    public R getCpuLoad(){
        double[] cpuLoad = cpuLoadMetricsService.getCpuLoad();
        return R.ok(cpuLoad);

    }
}
```

```java
# 服务层 CpuLoadMetricsService.java
package com.baojingyi.oshi.service;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

@Service
public class CpuLoadMetricsService {
    //OSHI提供的获取所有数据的入口
    SystemInfo si = new SystemInfo();
    public double[] getCpuLoad() {
        HardwareAbstractionLayer hardware = si.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        double[] cpuLoad = processor.getProcessorCpuLoad(1000);
        return cpuLoad;
    }
}
```

#### 3. 启动后端
```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run

# 或打包运行
mvn clean package
java -jar target/oshi-app-1.0.0.jar
```

### 📊 监控效果
访问`http://localhost:8080/metrics/cpuload`，获取到自己电脑的cpu使用率
![metrics](./docs/images/metrics.png)

### 🎯前端
1.创建项目脚手架：`npm create vite`  <br>
2.安装依赖：`npm install`  <br>
3.获取依赖：`npm install axios echarts @arco-design/web-vue`  <br>
arco-design-vue：ui框架  <br>
axios:发请求  <br>
echarts:画图  <br>
4.启动项目：`npm run dev`  <br>

### 🔍 CPU 监控视图
访问`http://localhost:5173/`
![首页](./docs/images/首页.png)

点击上方菜单栏中'CPU监控'，跳转`http://localhost:5173/CPU`
![cpuload-10](./docs/images/cpuload-10.png)

右上角可选择监控的范围，例如点击30min,则显示30个数据
![cpuload-30](./docs/images/cpuload-30.png)

### 🔄 后续开发计划
* ⬆️ 页面优化
* ✅ 多指标监控：增加内存、磁盘、网络监控
* 📱 响应式设计：适配移动端设备
* 🔔 告警功能：阈值告警和通知机制
* 📊 数据持久化：历史数据存储和查询

⬆️页面优化： <br>
例如：  <br>
1.右上角显示,点击的时间点  <br>
2.点击时间点后，保留部分数据。目前只是watch监听到`cpuAllData.value = [];`刷新数组，丢失历史数据


