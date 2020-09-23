# VerifyTS (TeamSpeak Verification System)

[![GitHub issues](https://img.shields.io/github/issues/illuminator3/VerifyTS)](https://github.com/illuminator3/VerifyTS/issues) [![GitHub forks](https://img.shields.io/github/forks/illuminator3/VerifyTS)](https://github.com/illuminator3/VerifyTS/network) [![GitHub stars](https://img.shields.io/github/stars/illuminator3/VerifyTS)](https://github.com/illuminator3/VerifyTS/stargazers) [![GitHub license](https://img.shields.io/github/license/illuminator3/VerifyTS)](https://github.com/illuminator3/VerifyTS/blob/master/LICENSE) ![dependencies](https://img.shields.io/badge/dependencies-spigot-yellow) [![contact](https://img.shields.io/badge/contact-jonas%40blockrain.net-blueviolet)](mailto:jonas@blockrain.net) ![Hits](https://hitcounter.pythonanywhere.com/count/tag.svg?url=https%3A%2F%2Fgithub.com%2Filluminator3%2Fverifyts)

## Installation

To install **VerifyTS** you'll first have to build the jar container, follow the steps described in [Building](#Building). If that's done all you have to do is to put the generated jar into your plugins folder and write (or use an existing) implementation of the API (example code can be found [here](#API Example Code)).

## Building

Building the project is really easy. First you'll have to clone the repository: ``git clone https://github.com/illuminator3/VerifyTS`` after that's done building the project can be done by running ``gradle :shadowJar`` (not ``build`` but ``:shadowJar``!).

## Dependency installation

### Maven

Add the jitpack repository:

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Find the correct commit version (e.g. 7c52229960) (can also be found [here](https://jitpack.io/#illuminator3/VerifyTS)). Use it as the dependency version:

```xml
<dependency>
    <groupId>com.github.illuminator3</groupId>
    <artifactId>VerifyTS</artifactId>
    <version>[version]</version>
</dependency>
```

---

### Gradle
Register the jitpack repository:

```groovy
maven { url 'https://jitpack.io' }
```

Find the version [here](https://jitpack.io/#illuminator3/VerifyTS) and add the dependency:

```groovy
implementation 'com.github.illuminator3:PacketTransfer:<version>'
```

## API Usage

Getting started is easy by importing the Settings class:

```java
import net.blockrain.verifyts.settings.Settings;
```

You can now proceed by setting the permission test function (or none/null if you want to use the default bukkit implementation (``Player#hasPermission``)):

```java
Settings.setRankFunction(BiFunction<Player, String, Boolean>);
```

You can add a permission for a TeamSpeak group with

```java
Settings.setRankSetting(Map<String, Integer>);
```

## API Example Code

Taken from my SkyPvP plugin:

```java
Settings.setRankFunction(PermissionSystem::hasPermission);

Map<String, Integer> map = new HashMap<>();
 
map.put("teamSpeak.admin", 14);
map.put("teamSpeak.dev", 84);
map.put("teamSpeak.content", 85);
map.put("teamSpeak.mod+", 87);
map.put("teamSpeak.mod", 88);
map.put("teamSpeak.sup", 89);
map.put("teamSpeak.builder", 86);
map.put("teamSpeak.freund", 98);
map.put("teamSpeak.yt", 91);
map.put("teamSpeak.block", 104);
map.put("teamSpeak.mvp", 103);
map.put("teamSpeak.vip", 92);

Settings.setRankSetting(map);
```

## Contribution

You can contribute by creating an issue or pull request. Please keep the code clean and readable. All contributed code must be in the already present code format. Consider using the groovy language (e.g. Closure instead of a Runnable/Consumer/Function). Contributors will be listed here.

### Contributors

- illuminator3
- FastFoodFighter3/flywinghd (partially)

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](https://github.com/illuminator3/VerifyTS/blob/master/LICENSE) file for details

### Permissions & Limitations

#### Permissions

-  Commercial use
-  Modification
-  Distribution
-  Patent use
-  Private use

#### Limitations

-  Trademark use
-  Liability
-  Warranty