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
  
# or if we want to send it to console only
example:
  console: true
  message: Example Console Only

# or if we want send some messages to console only and some to everyone
example:
- Example Chat
- console: true
  message: Example Console Only
```
This mean the whole serializer will probably be compatible with old formats that actually only uses Strings and Lists of Strings.

---

### One and Multiple holders 
When we only have 1 holder of specific type everything would be serialized as object:
```yaml
example:
  bossbar:
    name: Example BossBar
    color: GREEN
    overlay: PROGRESS
    progress: 0.5
    stay: 20
```

But when we have multiple holders of specific type everything would be serialized as list of objects:
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

Every holder could be represented both as object and list of objects (Yeah even Action Bar & Title)

---
This whole section is W.I.P. but I think its good representation of what this serializer can do.