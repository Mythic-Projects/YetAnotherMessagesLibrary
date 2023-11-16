YetAnotherMessagesLibrary (YAML)
===========
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Version](https://repo.mythicprojects.org/api/badge/latest/releases/org/mythicprojects/yetanothermessageslibrary/core?color=42c611&name=Releases&prefix=v)
![Version](https://repo.mythicprojects.org/api/badge/latest/snapshots/org/mythicsprojects/yetanothermessageslibrary/core?color=d45f48&name=Snapshots&prefix=v)
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
- [okaeri-configs](https://github.com/OkaeriPoland/okaeri-configs) - Transformers & Serializers for every `YAML` message type and utility classes. See how it looks like [here](https://github.com/P3ridot/YetAnotherMessagesLibrary/blob/master/repository/okaeri/FORMAT.md).

### Replaceable
Implementations of `Replaceable` interface to easily replace placeholders in messages
- [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)
- [okaeri-placeholders **\[EXPERIMENTAL\]**](https://github.com/OkaeriPoland/okaeri-placeholders)


## Maven/Gradle

### Maven
```xml
<!-- Releases -->
<repository>
    <id>mythic-releases</id>
    <url>https://repo.mythicprojects.org/releases</url>
</repository>
<!-- Snapshots -->
<repository>
    <id>mythic-snapshots</id>
    <url>https://repo.mythicprojects.org/snapshots</url>
</repository>
```

```xml

<dependency>
    <groupId>org.mythicprojects.yetanothermessageslibrary</groupId>
    <artifactId>[module]</artifactId>
    <version>[version]</version>
</dependency>
```

### Gradle
```groovy
repositories {
    // Releases
    maven {
        name = "mythic-releases"
        url = "https://repo.mythicprojects.org/releases"
    }
    // Snapshots
    maven {
        name = "mythic-snapshots"
        url = "https://repo.mythicprojects.org/snapshots"
    }
}
```

```groovy
dependencies {
    implementation 'org.mythicprojects.yetanothermessageslibrary:[module]:[version]'
}
```
