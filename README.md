# xcsbotcontrol

xcsbotcontrol is a command line tool to do CRUD operations on Xcode Server bots. It uses the [Xcode Server REST API](https://developer.apple.com/library/archive/documentation/Xcode/Conceptual/XcodeServerAPIReference/).

## Build
Checkout the repository and run `./gradlew jar`. The jar file will at generated at `build/libs/xcsbotcontrol.jar`

## Usage

### List bots
Lists the configuration of all bots on a given Xcode Server.

```java -jar xcsbotcontrol.jar -a list -s <Xcode Server host>```

e.g.

```java -jar xcsbotcontrol.jar -a list -s xcodeserver.mycompany.com```

### Create a bot
```java -jar xcsbotcontrol.jar -a create myBotConfig.json```

Creates a bot on the Xcode Server. By providing a space-delimited list of config files, many bots can be created with one call.

If you want to create a single bot based on a template config file, but override things like the bot name or the branch (e.g. for integration with your CI workflow), you can do so by specifying additional arguments:
```java -jar xcsbotcontrol.jar -a create -n myBotName -b "feature/myBranch" myBotConfig.json```

### Delete a bot
```java -jar xcsbotcontrol.jar -a delete -n myBotName -s xcodeserver.mycompany.com```

Deletes the bot with the given name on the Xcode Server.

### Delete bots based on config files
```java -jar xcsbotcontrol.jar -a delete myBotConfig.json```

Deletes the bot specified in the config file on the Xcode Server. By providing a space-delimited list of config files, many bots can be deleted with one call.

### Start the integration for a bot
```java -jar xcsbotcontrol.jar -a integrate -n myBotName -s xcodeserver.mycompany.com```

Starts the integration of the bot with the given name on the Xcode Server.

### Start the integration based on config files
```java -jar xcsbotcontrol.jar -a integrate myBotConfig.json```

Starts the integration of the bot specified in the config file on the Xcode Server. By providing a space-delimited list of config files, many bots can be started with one call.

### Write an example config
```java -jar xcsbotcontrol.jar -a example```

This generates `example.json` in the current directory.

### Secret handling

#### Login for Xcode Server
The credentials for restricted operations (create, delete, integrate) are evaluated in the following order:
- command line
- bot configuration file
- macOS keychain

##### Command line
```-u <username> -p [password]```
Use the `-p` flag without password for interactive password input.

##### Bot configuration file
Specify fields `xcodeServerUsername` and `xcodeServerPassword` in the bot configuration JSON file.

##### macOS Keychain 
Use "Keychain Access" or `security` to create a local keychain entry of type "application password" for the host.
```
Name: <as configured in `xcodeServerHost` in bot config file>
Account: <username>
Password: <password>
```

#### Login to repositories
xcsbotcontrol currently only supports Git access using SSH keys. You can either provide the SSH private key:
- by referencing a keychain item using the key `sshPrivateKeyKeyChainItem` (recommended)
- or clear text in the bot config file in the repository section using the key `sshPrivateKey`

When using the keychain, create a local keychain entry of type "application password" for the repository's private key as configured in `sshPrivateKeyKeyChainItem`:
```
Name: <as configured in `sshPrivateKeyKeyChainItem` in bot config file>
Account: <username>, 
Password: <full private SSH key incl. "-----BEGIN RSA PRIVATE KEY-----">
```

## DISCLAIMER
The Xcode Server REST API is documented poorly and many things have been guessed by experimenting with the API. Chances for a case of "You're holding it wrong" are high.

## Contributing
Bug reports and pull requests are welcome.

## License
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)
The project is available as open source under the terms of the [MIT License](./LICENSE).

## Acknowledgements
- Thanks to [Buildasaur](https://github.com/buildasaurs) for the initial inspiration
