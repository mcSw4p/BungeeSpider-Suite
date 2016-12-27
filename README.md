# BungeeSpider-Suite
- - - -
The BungeeSpider Suite is a Minecraft network suite to send information to Network Adminsistators/Moderators.
BungeeSpider Server(BSS) uses sockets to send information about each server back and forth to keep track for their status. BungeeSpider Client(BSC)
 is the client tat is on each server instance that connects back to BSS to relay information. You can add [Addons](https://github.com/mcSw4p/BungeeSpider-Suite/tree/master/Addons)
 to both BSS and BSC to build off of the server/client. 
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
## Addons
You can look at the Addons [here](https://github.com/mcSw4p/BungeeSpider-Suite/tree/master/Addons). Some addons require that you have its client on each BungeeSpider-Client 
server and its server on the BungeeSpider-Server.
- - - -
## Server Messages
You can take a look at all the valid Server Messages in the "[Server Messages.txt](https://github.com/mcSw4p/BungeeSpider-Suite/blob/master/Server%20Messages.txt)".
 These allow the Server to retrieve the info the clients are sending.
 - - - -
## Questions
> Why not use the PluginMessagingChannels that are already built into Spigot/BungeeCord?
>> We have a project that uses the PluginMessagingChannels but they require that a player be on the server to send messages. That typically means
 you run into stability issues.

##
 
> Will this cause my servers to lag more?
>> Both the BungeeSpider-Server and the BungeeSpider-Client are actually pretty lightweight. The Server does nothing more than sit and wait to be told what 
to do by the client, so as long as the clients are not stressing the server everything is ok. The Client does a bit more though, so naturally it is a bit 
more reasource intensive. But it is still very lightweight, not adding much to an existing spigot server. Ofcourse like you always hear, it all depends on 
how many plugins/addons you have.

##

> Can I create a custom addon for my server?
>> Yes. Both BungeeSpider-Server and BungeeSpider-Client can be added to your build path so you can build off of them. You can view the examples. You can also add Spigot or BungeeCord
jars if you need to access those files in your addon. You just need to extend the Addon class like this:
~~~java
//For a BungeeSpider-Server Addon
public class ExampleAddon extends net.wynsolutions.bss.Addon{
~~~
You will also need a 'spider.yml' in your project aswell. Look in the examples to find the proper formatting.

##

> Does the server port need to be open?
>> Yes. The Port needs to be open for the clients to be able to connect. You can block/allow ip address in the ip_table Config to help with extra security. If you are hosting all the servers 
on the same computer then the BungeeSpider-Server port does not need to be open. It will work just aswell.

## Authors
* **Sw4p** - [mcSw4p](https://github.com/mcSw4p)