# BungeeSpider-Suite
- - - -
The BungeeSpider Suite is a Minecraft network suite to send information to Network Adminsistators/Moderators.
BungeeSpider Server(BSS) uses sockets to send information about each server back and forth to keep track for their status. BungeeSpider Client(BSC)
 is the client tat is on each server instance that connects back to BSS to relay information. You can add Addons to both BSS and BSC to build off 
of the server/client. 
~~~
~~~
_Why not use the PluginMessagingChannels that are already built into Spigot/BungeeCord?_
~~~
~~~
We have a project that uses the PluginMessagingChannels but they require that a player be on the server to send messages. That typically means
 you run into stability issues.
- - - -
## BungeeSpider Server Versions 

#### BungeeSpider-Server(BungeeCord Plugin)
The [BungeeCord](https://github.com/mcSw4p/BungeeSpider-Suite/tree/master/BungeeSpider-Server) version of BSS runs as a plugin on your BungeeCord Proxy. 
It then creates a Socket Listener on a port on the same server as the Proxy 

#### BungeeSpider-Server(Standalone)
This BSS version is not yet created but will run independently from all other Minecraft related servers. 
- - - -
## BungeeSpider Client Versions

#### BungeeSpider-Client(Spigot/CraftBukkit)
The [Spigot/CraftBukkit](https://github.com/mcSw4p/BungeeSpider-Suite/tree/master/BungeeSpider-Client) version of BSC runs as a plugin on each instance of
 your servers and allows you to load addons and relay info back to the BungeeSpider Server.

#### BungeeSpider-Client(BungeeCord)
This BSC plugin is not yet created but will be for connecting multiple BungeeCord Proxies with the BungeeSpider Server.
- - - -
## Server Messages
You can take a look at all the valid Server Messages in the "[Server Messages.txt](https://github.com/mcSw4p/BungeeSpider-Suite/blob/master/Server%20Messages.txt)".
 These allow the Server to retrieve the info the clients are sending.

 - - - -
## Authors
* **Sw4p** - [mcSw4p](https://github.com/mcSw4p)