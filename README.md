YetAnotherMessagesLibrary (YAML)
===========
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Version](https://repo.titanvale.net/api/badge/latest/releases/pl/peridot/yetanothermessageslibrary/api?color=42c611&name=Releases&prefix=v)
![Version](https://repo.titanvale.net/api/badge/latest/snapshots/pl/peridot/yetanothermessageslibrary/api?color=d45f48&name=Snapshots&prefix=v)
![GitHub repo size](https://img.shields.io/github/repo-size/P3ridot/YetAnotherMessagesLibrary)

Lightweight and modular *Minecraft Messages Library* based on [adventure](https://github.com/KyoriPowered/adventure).

## Support

### Platforms
General implementations of API for specific platforms
- Bukkit/Spigot/Paper - Using [adventure-platform-bukkit](https://docs.adventure.kyori.net/platform/bukkit.html)
- BungeeCord/Waterfall - Using [adventure-platform-bungeecord](https://docs.adventure.kyori.net/platform/bungeecord.html)
- Velocity 3.x - Using native adventure implementation

### Repositories
Implementations of values/messages suppliers or utilities class to easily add support for `YAML` message types
- [okaeri-configs](https://github.com/OkaeriPoland/okaeri-configs) - Transformers & Serializers for every `YAML` message type and utility classes

### Replaceable
Implementations of `Replaceable` interface to easily replace for e.g. placeholders in messages
- [okaeri-placeholders](https://github.com/OkaeriPoland/okaeri-placeholders)
- [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)


## Maven/Gradle

### Maven
```xml
<!-- Releases -->
<repository>
    <id>titanvale-releases</id>
    <url>https://repo.titanvale.net/releases</url>
</repository>
<!-- Snapshots -->
<repository>
    <id>titanvale-snapshots</id>
    <url>https://repo.titanvale.net/snapshots</url>
</repository>
```

```xml
<dependency>
    <groupId>pl.peridot.yetanothermessageslibrary</groupId>
    <artifactId>[module]</artifactId>
    <version>[version]</version>
</dependency>
```

### Gradle
```groovy
repositories {
    // Releases
    maven {
        name = "titanvale-releases"
        url = "https://repo.titanvale.net/releases"
    }
    // Snapshots
    maven {
        name = "titanvale-snapshots"
        url = "https://repo.titanvale.net/snapshots"
    }
}
```

```groovy
dependencies {
    implementation 'pl.peridot.yetanothermessageslibrary:[module]:[version]'
}
```
