### 🔆 JEss ◆ Manager features at your server
_The plugin will be expanded and improved with each new update._

- **Core:** Paper / Spigot  
- **Version:** 1.16 - 1.21+  
- **Java**: 16+  

### ⚙️ Possibilities (for now)
_The plugin will have full customization and detailed settings for every aspect._  

| **Features**      | **Description**                           |
|-------------------|-------------------------------------------|
| `Server MOTD`     | Your project design in the server list    |
| `Greetings`       | Player join/quit/death messages           |

### ⌨️ Commands

| **Commands**      | **Description**                           | **Permissions**       |
|-------------------|-------------------------------------------|-----------------------|
| `/jessreload`     | Reloading the plugin                      | `jess.reload`         |

### ✍️ Configuration

```yml

#                                                                                #
#    ░░░░░██╗███████╗░██████╗░██████╗                                            #
#    ░░░░░██║██╔════╝██╔════╝██╔════╝    Manager features at your server         #
#    ░░░░░██║█████╗░░╚█████╗░╚█████╗░    Plugin version: JESS-v1.0               #
#    ██╗░░██║██╔══╝░░░╚═══██╗░╚═══██╗    Author: jFrostyBoy                      #
#    ╚█████╔╝███████╗██████╔╝██████╔╝    Website: https://jfrostyboy.42web.io    #
#    ░╚════╝░╚══════╝╚═════╝░╚═════╝░                                            #
#                                                                                #

#    ----------------------    #
#    Plugin prefix             #
#    Leave blank to disable    #
#    ----------------------    #
prefix: "&8[&bJEss&8] "

#    --------------------    #
#    Main plugin messages    #
#    --------------------    #

main-messages:
  no-permission: "&fYou &cdon't have &fpermission"
  reload-usage: "&fUsage: &b/jessreload"
  reloaded: "&fPlugin successfully &areloaded"

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
  first-join-message: "&a[-] &fPlayer &a%player% &fnewbie on the server"

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

```
