### 🔆 JEss ◆ Manager features at your server
_The plugin will be expanded and improved with each new update._

- **Core:** Paper / Spigot  
- **Version:** 1.16 - 1.21+  
- **Java**: 16+  

### ⚙️ Possibilities (for now)
_The plugin will have full customization and detailed settings for every aspect._  

| **Features**       | **Description**                             |
|--------------------|---------------------------------------------|
| `Server MOTD`      | Your project design in the server list      |
| `Greetings`        | Player join/quit/death messages             |
| `Heal & Feed`      | Heal & Feed - self and other                |
| `Fly` & `FlySpeed` | Flight mode & Flight speed - self and other |

### ⌨️ Commands

| **Commands**                                       | **Description**                                                                 | **Permissions**                         |
|----------------------------------------------------|---------------------------------------------------------------------------------|-----------------------------------------|
| `/jessreload`                                      | Reloading the plugin                                                            | `jess.reload`                           |
| `/heal`<p>`/heal [nickname]`                       | Heal yourself<p>Heal other players                                              | `jess.heal`<p>`jess.heal.other`         |
| `/feed`<p>`/feed [nickname]`                       | Feed yourself<p>Feed other players                                              | `jess.feed`<p>`jess.feed.other`         |
| `/fly`<p>`/fly [nickname]`                         | Activate/Deactivate flight mode<p>Activate/Deactivate flight mode other players | `jess.fly`<p>`jess.fly.other`           |
| `/flyspeed [1-10]`<p>`/flyspeed [nickname] [1-10]` | Set fly speed to yourself<p>Set fly speed to other players                      | `jess.flyspeed`<p>`jess.flyspeed.other` |

### ✍️ Configuration

```yml

#                                                                                #
#    ░░░░░██╗███████╗░██████╗░██████╗                                            #
#    ░░░░░██║██╔════╝██╔════╝██╔════╝    Manager features at your server         #
#    ░░░░░██║█████╗░░╚█████╗░╚█████╗░    Plugin version: JESS-v1.3               #
#    ██╗░░██║██╔══╝░░░╚═══██╗░╚═══██╗    Author: jFrostyBoy                      #
#    ╚█████╔╝███████╗██████╔╝██████╔╝    Website: https://jfrostyboy.42web.io    #
#    ░╚════╝░╚══════╝╚═════╝░╚═════╝░                                            #
#                                                                                #

#    ----------------------    #
#    Plugin prefix             #
#    Leave blank to disable    #
#    ----------------------    #
prefix: "&8[&bJESS&8] "

#    ------------------------------------------    #
#    Formatting information about your             #
#    server in the server list                     #
#    (there is currently no support for            #
#    #HEX colors and gradients - will be           #
#    added in future releases and updates)         #
#                                                  #
#    Placeholders that can be used:                #
#      - %online% - displays the current online    #
#      - %max% - displays the maximum online       #
#    ------------------------------------------    #

motd:
  enable: true

  line-1: "    &bServerName &7( 1.16+ - 1.21+ )  &8|  &fPlaying now: &b%online%"
  line-2: "                    &6Welcome to the server"

#    ----------------------------------------------------------------    #
#    Custom player join/quit/death messages and sounds that can be       #
#    easily changed or disabled by setting the value to enable: false    #
#    Messages and sounds will be seen and heard by all players           #
#                                                                        #
#    Placeholder that is used:                                           #
#      - %player% - displays the player's nickname                       #
#    ----------------------------------------------------------------    #
#    List of sounds that can be used:                                    #
#    https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html      #
#                                                                        #
#    Minimum/maximum values:                                             #
#      - volume: 0.1 - 1.0        - pitch: 0.5 - 2.0                     #
#    ----------------------------------------------------------------    #

join:
  enable: true
  join-message: "&b[+] &fPlayer &b%player% &fjoined the server"
  first-join-message: "&a[+] &fPlayer &a%player% &fnewbie on the server"

  sounds:
    join:
      enable: true
      sound: "entity.player.levelup"
      volume: 1.0
      pitch: 2.0
    first-join:
      enable: true
      sound: "block.beacon.activate"
      volume: 1.0
      pitch: 1.0

quit:
  enable: true
  message: "&c[-] &fPlayer &c%player% &fleft the server"

  sound:
    enable: true
    sound: "block.anvil.place"
    volume: 1.0
    pitch: 1.5

death:
  enable: true
  message: "&6[x] &fPlayer &6%player% &fdied"

  sound:
    enable: true
    sound: "entity.wither.death"
    volume: 1.0
    pitch: 1.1

#    ----------------------------------------------------------------    #
#    Heal & Feed - self and other                                        #
#                                                                        #
#    Personalized command delays for different donation groups           #
#    (edit existing groups and add new ones as needed)                   #
#                                                                        #
#    Placeholder that is used:                                           #
#      - %player% - displays the player's nickname                       #
#      - %sec% - displays the remaining cooldown time                    #
#    ----------------------------------------------------------------    #
#    List of sounds that can be used:                                    #
#    https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html      #
#                                                                        #
#    Minimum/maximum values:                                             #
#      - volume: 0.1 - 1.0        - pitch: 0.5 - 2.0                     #
#    ----------------------------------------------------------------    #

heal:
  enable: true
  cooldowns:
    default: 300
    vip: 180
    premium: 60

  cooldown: "&fPlease wait &c%sec% sec. &fbefore using again"
  success: "&fYou have &asuccessfully &frestored your health"
  success-healed: "&fYou were &ahealed &fby &b%player%"
  success-other: "&fYou have &asuccessfully &fhealed &b%player%"

  heal-sound:
    enable: true
    sound: "entity.player.levelup"
    volume: 1.0
    pitch: 2.0

heal-other:
  enable: true

feed:
  enable: true
  cooldowns:
    default: 300
    vip: 180
    premium: 60

  cooldown: "&fPlease wait &c%sec% sec. &fbefore using again"
  success: "&fYou have &asuccessfully &ffeeded your hunger"
  success-feeded: "&fYou were &afeeded &fby &b%player%"
  success-other: "&fYou have &asuccessfully &ffeeded &b%player%"

  feed-sound:
    enable: true
    sound: "entity.player.levelup"
    volume: 1.0
    pitch: 2.0

feed-other:
  enable: true

#    ----------------------------------------------------------------    #
#    Flight mode & Flight speed - self and other                         #
#                                                                        #
#    Placeholder that is used:                                           #
#      - %player% - displays the player's nickname                       #
#      - %executor% - displays the nickname of the person                #
#                     who changed the player's speed                     #
#      - %speed% - shows the set speed value                             #
#    ----------------------------------------------------------------    #
#    List of sounds that can be used:                                    #
#    https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html      #
#                                                                        #
#    Minimum/maximum values:                                             #
#      - volume: 0.1 - 1.0        - pitch: 0.5 - 2.0                     #
#    ----------------------------------------------------------------    #

fly:
  enable: true

  enabled: "&fFlight successfully &aactivated"
  disabled: "&fFlight successfully &cdeactivated"
  enabled-other: "&fYour flight was &aactivated &fby &b%player%"
  disabled-other: "&fYour flight was &cdeactivated &fby &b%player%"
  enabled-other-success: "&fFlight &aactivated &ffor &b%player%"
  disabled-other-success: "&fFlight &cdeactivated &ffor &b%player%"

  fly-sound:
    enable: true
    sound: "entity.player.levelup"
    volume: 1.0
    pitch: 2.0

fly-other:
  enable: true

flyspeed:
  enable: true
  flyspeed-usage: "&fUsage: &b/flyspeed [1-10] &for &b/flyspeed [nickname] [1-10]"
  no-value: "&fEnter a number from &c1 &fto &c10"
  success-self: "&fYour fly speed has been set to &a%speed%"
  success-other: "&fFly speed of &b%player% &fhas been set to &a%speed%"
  success-for-you: "&fFly speed set to &a%speed% &fby &b%executor%"

  flyspeed-sound:
    enable: true
    sound: "entity.player.levelup"
    volume: 1.0
    pitch: 2.0

flyspeed-other:
  enable: true

#    --------------------    #
#    Main plugin messages    #
#    --------------------    #

main-messages:
  no-permission: "&fYou &cdon't have &fpermission"
  reload-usage: "&fUsage: &b/jessreload"
  reloaded: "&fPlugin successfully &areloaded"
  disabled-command: "&fThis command is &cdisabled"
  not-found: "&fPlayer &cnot found!"
  only-players: "&fOnly &cplayers &fcan use this command"
```

<img width="958" height="144" alt="image" src="https://github.com/user-attachments/assets/e2675fe7-bbd6-4e6c-a8bf-6c22d1d60298" />



