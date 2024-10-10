# CommandWhiteList
A lightweight server-wide command whitelist.  
[ [中文](https://github.com/reuAC/CommandWhiteList/blob/reuAC/README.md) | English | [日本語](https://github.com/reuAC/CommandWhiteList/blob/reuAC/README_JP.md) ]

## Introduction
This plugin is compatible with Spigot 1.8 and above.  

It allows controlling server-wide commands, only permitting those present in the whitelist to be executed.
## Commands
`/CommandWhiteList` reloads the configuration.  
**Alias：`/cwlist`**

## Configuration File
After successfully starting the plugin, the configuration file will be generated under the `plugins/CommandWhiteList/config.yml`  

```yaml
// Command whitelist
Command_WhiteList:
  - "help"
  - "list"
  - "spawn"

// Enable the following messages
enabledMessage: false

// Message sent to players when trying to execute commands not in the whitelist.
CommandNotAllow_Tip:
  - "&a禁止执行"
  - "&cExecution Prohibited"
  - "&b実行禁止"
```

## Permission Nodes
`commandwhitelist.main` for using the reload command.  
`commandwhitelist.bypass` to bypass the whitelist restriction.

## Usage
1. Place the compiled jar file into the plugins folder and restart the server.