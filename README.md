# CommandWhiteList
轻量的服务器范围内指令白名单。  

## 介绍
该插件适用于Spigot1.8及以上。  

支持令白名单以外的指令不得自动补全。  
**由于相关特性生效于1.13，故应用于自动补全的限制在1.13及以上的版本生效。**  
支持控制整个服务器范围内的指令，仅允许存在于白名单内的指令被执行。  
支持单独控制特定世界进行白名单限制。  
支持使用通配符、单个参数通配符。  
支持使用基于权限节点的白名单组。
## 指令
`/CommandWhiteList` 重载配置。  
**缩写：`/cwlist`**

## 配置文件
插件成功启动后，会在plugins文件夹下生成配置文件，位于 `plugins/CommandWhiteList/config.yml`  

```yaml
# 使用*可以使得允许执行任意数量的任意参数。
# 使用[*]表示单个任意参数
# 例子："reg *"
# 例子："give [*] [*] 1"

# 生效优先级（由高优先级到低优先级）：白名单组、世界、世界中的默认配置。

# 白名单组
# 当玩家拥有commandwhitelist.group.组名时，生效。
CommandWhiteListGroup:
  # 组名
  group1:
    # 指令白名单列表
    whitelist:
      - "111 *"
      - "help [*] 666"
    # 是否启用下面的提示
    enabledMessage: true
    # 执行白名单之外的指令后，向玩家发送的提示。
    message:
      - "hello"
#  group2:
#    enabledMessage: true
#    message:
#      - "hello"
#    whitelist:
#      - "111 *"
#      - "help [*] 666"

Worlds:
  #生效于特定世界：world_a是世界名称
  world_a:
    #指令白名单列表
    Command_WhiteList:
      - "list"
      - "spawn"
    #是否启用下面的提示
    enabledMessage: true
    #执行白名单之外的指令后，向玩家发送的提示。
    CommandNotAllow_Tip:
      - "&a禁止执行"
      - "&cExecution Prohibited"
      - "&b実行禁止"
# 您可以按照如下格式添加配置。
#  world_b:
#    #指令白名单列表
#    Command_WhiteList:
#      - "list"
#      - "spawn"
#    #是否启用下面的提示
#    enabledMessage: true
#    #执行白名单之外的指令后，向玩家发送的提示。
#    CommandNotAllow_Tip:
#      - "&a禁止执行"
#      - "&cExecution Prohibited"
#      - "&b実行禁止"
  DefaultConfig:
    #指令白名单列表
    Command_WhiteList:
      - "l *"
      - "reg *"
      - "login *"
      - "register *"
      - "help"
    #是否启用下面的提示
    enabledMessage: true
    #执行白名单之外的指令后，向玩家发送的提示。
    CommandNotAllow_Tip:
      - "&a禁止执行"
      - "&cExecution Prohibited"
      - "&b実行禁止"
# 默认对所有情况都生效的配置，取自Worlds
Default:
  # 配置名称
  name: "DefaultConfig"
  # 是否启用该配置对所有世界
  enable: true
```

## 权限节点
`commandwhitelist.main` 使用重载指令。  
`commandwhitelist.bypass` 绕过所有白名单限制。  

## 使用方法
1. 将编译完成的jar包放入plugins文件夹中，重启服务器。
