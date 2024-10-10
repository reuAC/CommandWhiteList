# CommandWhiteList
轻量的服务器范围内指令白名单。  
[ 中文 | [English](https://github.com/reuAC/CommandWhiteList/blob/reuAC/README_EN.md) | [日本語](https://github.com/reuAC/CommandWhiteList/blob/reuAC/README_JP.md) ]

## 介绍
该插件适用于Spigot1.8及以上。  

支持控制整个服务器范围内的指令，仅允许存在于白名单内的指令被执行。
## 指令
`/CommandWhiteList` 重载配置。  
**缩写：`/cwlist`**

## 配置文件
插件成功启动后，会在plugins文件夹下生成配置文件，位于 `plugins/CommandWhiteList/config.yml`  

```yaml
// 指令白名单列表
Command_WhiteList:
  - "help"
  - "list"
  - "spawn"

// 是否启用下面的提示
enabledMessage: false

// 执行白名单之外的指令后，向玩家发送的提示。
CommandNotAllow_Tip:
  - "&a禁止执行"
  - "&cExecution Prohibited"
  - "&b実行禁止"
```

## 权限节点
`commandwhitelist.main` 使用重载指令。  
`commandwhitelist.bypass` 绕过白名单限制。

## 使用方法
1. 将编译完成的jar包放入plugins文件夹中，重启服务器。