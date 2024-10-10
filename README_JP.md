# CommandWhiteList
軽量のサーバー全体で使用できるコマンドホワイトリスト。  
[ [中文](https://github.com/reuAC/CommandWhiteList/blob/reuAC/README.md) | [English](https://github.com/reuAC/CommandWhiteList/blob/reuAC/README_EN.md) | 日本語 ]

## 紹介
このプラグインはSpigot1.8以降に対応しています。  

サーバー全体で使用できるコマンドを制御し、ホワイトリストに含まれるコマンドのみ実行可能にします。
## コマンド
`/CommandWhiteList` 設定をリロードします。  
**省略形：`/cwlist`**

## 設定ファイル
プラグインの起動後、 `plugins/CommandWhiteList/config.yml` に設定ファイルが生成されます。  

```yaml
// コマンドホワイトリスト
Command_WhiteList:
  - "help"
  - "list"
  - "spawn"

// 以下のメッセージを有効にするかどうか
enabledMessage: false

// ホワイトリストにないコマンドを実行した際に、プレイヤーに送信されるメッセージ。
CommandNotAllow_Tip:
  - "&a禁止执行"
  - "&cExecution Prohibited"
  - "&b実行禁止"
```

## 権限ノード
`commandwhitelist.main` リロードコマンドを使用可能。  
`commandwhitelist.bypass` ホワイトリストの制限をバイパス。

## 使用方法
1. コンパイル済みのjarファイルをpluginsフォルダに入れ、サーバーを再起動します。