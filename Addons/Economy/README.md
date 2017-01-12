# BungeeSpider-Economy
The BungeeSpider-Economy Addon creates a cross network economy for players. It registers a Message Listener with BungeeSpider-Server and creates a database to store all players balances.
The Client then asks the Server to update/retrieve the players balance each time it is needed. This does introduce lag but if setup correctly then the lag is manageable for the benefits. 
 
 ## How to install
Enter this command from the console or a player with permission:
BungeeSpider-Server:
`
  bss.server.installaddon
  /bsc iadn <direct url to addon jar>
`
BungeeSpider-Client:
`
  bsc.cmd.installaddon
  /bsc iadn <direct url to addon jar>
`
 
## Commands
* 'econ'
 * Displays your current balance.
 
* 'econ add [playername] [amount]'
 * Adds an amount the a players balance
 
* 'econ remove [playername] [amount]'
 * Removes an amount from a players balance
 
* 'econ [playername]'
 * Displays a players current balance.
 
## Authors
* **Sw4p** - [mcSw4p](https://github.com/mcSw4p)