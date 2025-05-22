# Block Breaker 弹幕碰撞游戏

## 项目简介

本项目是一个基于 Java Swing 的弹幕碰撞小游戏。玩家通过键盘控制准星方向，发射子弹，破坏方块得分。项目采用多线程实现弹幕生成、移动和碰撞检测，系个人课程实验作业。

## 主要功能

- 玩家准星方向可在 -1 到 1 之间自动摆动，松开空格即可锁定方向并发射子弹
- 支持多线程弹幕移动与碰撞检测
- 碰撞判定与反弹机制
- 计时器系统、得分系统
- 支持重玩
- 基于 Java Swing 图形界面

## 运行环境

- JDK 8 及以上
- 推荐使用 IntelliJ IDEA 打开本项目

## 如何运行

1. 克隆或下载本项目
2. 用 IntelliJ IDEA 打开项目根目录（`Block Breaker/`）
3. 直接运行 `src/Main.java` 即可启动游戏

## 主要文件说明

- `src/Main.java`：程序入口
- `src/MainWindow.java`：主窗口
- `src/GamePanel.java`：游戏主面板，负责绘制、主循环和线程管理
- `src/ShotingSpot.java`：玩家准星
- `src/GunSight.java`：准星方向控制线程
- `src/Bullet.java`：子弹/弹幕
- `src/BulletMover.java`：子弹移动线程
- `src/BlockGridPanel.java`：方块网格管理与绘制
- `src/BulletBlockCollisionThread.java`：子弹与方块的碰撞检测线程
- `src/Score.java`：分数管理
- `src/Timer.java`：倒计时管理

## 操作说明

- 按住空格键：游戏开始
- 松开空格键：锁定当前方向并发射子弹

## ToDo List
1. 子弹发射 （✔️）
2. 发射方向改变（✔️）
3. 碰撞墙壁反弹（✔️）
4. 方块生成（✔️）
5. 方块碰撞和消失（✔️）
6. 得分系统（✔️）
7. 玩家成长系统（ ）
8. 关卡扩展（ ）
