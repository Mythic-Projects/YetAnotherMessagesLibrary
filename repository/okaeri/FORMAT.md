Example format of serialized SendableMessage using `yaml` file format

### Simple Example
```yaml
example:
  chat:
  - Example Chat
  - console: true
    message: Example Console Only
  actionbar: Example ActionBar
  title:
    title: Example Title
    subtitle: Example SubTitle
    fade-in: 10
    stay: 20
    fade-out: 10
  bossbar:
  - name: Example BossBar
    color: GREEN
    overlay: PROGRESS
    progress: 0.5
    stay: 20
  - name: Example BossBar2
    color: GREEN
    overlay: PROGRESS
    progress: 0.5
    stay: 20
  sound:
    name: minecraft:ui.button.click
    source: MASTER
    volume: 1.0
    pitch: 1.0
```

---

### Only Chat Message
When we only have 1 Chat Message everything will be serialized to much simpler form:
```yaml
# simple string
example: Example Chat

# or list of strings
example:
- Example Chat 1
- Example Chat 2
  
# or if we want to send message only to console
example:
  console: true
  message: Example Console Only

# or if we want to send some message to console only and other to everyone
example:
- Example Chat
- console: true
  message: Example Console Only
```

This mean old formats that uses Strings and List of Strings will be compatible and serialized properly.

---

### One and Multiple holders 
When we only have 1 holder of specific type everything will be serialized as object:
```yaml
example:
  bossbar:
    name: Example BossBar
    color: GREEN
    overlay: PROGRESS
    progress: 0.5
    stay: 20
```

When we have multiple holders of specific type everything will be serialized as list of objects:
```yaml
example:
  bossbar:
  - name: Example BossBar
    color: GREEN
    overlay: PROGRESS
    progress: 0.5
    stay: 20
  - name: Example BossBar2
    color: GREEN
    overlay: PROGRESS
    progress: 0.5
    stay: 20
```

<u>Every</u> holder type can be represented both as object and list of objects (Yeah even Action Bar & Title).

---
This whole file is W.I.P. but I think its good representation of what this serializer can do... 