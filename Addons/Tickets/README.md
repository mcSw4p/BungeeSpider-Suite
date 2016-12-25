# BungeeSpider-Tickets
The BungeeSpider-Tickets(BST) addon is a Addon for BungeeSpider Server. It collects Tickets from Players/Staff and puts them
in a Google Sheets Doc. 
```
Why a Google Sheets Doc? 
```
There is a Software called Appsheet that creates a app from Excel/Sheets Doc along with others. This way Administrators/
Moderators can view Tickets from a smart phone over Wireless or Cellular Data. That app can be created just for your 
Network or you could use my Default template.
 
 **This addon is still WIP**
 
## BungeeSpider-Tickets Server 
This addon goes in the addons folder on the BungeeSpider Server. It recieves the information sent from the client and exports it to 
Google Sheets.  

## BungeeSpider-Tickets Client
This addon goes in the addons folder on the BungeeSpider Client. It sends information about the ticket once a player or staff has created 
it and sends it to the server to be exported. Also allows staff members to Claim, Close, Comment, and view tickets. 

## Server Messages
* 'ticket'
 * Ticket id(int)
 * Player UUID(String)
 * Created Date
 * Location
 * Message
 * Any 'Action Dates'

* 'ticketcm' - Action Date
 * Ticket Comment
 * Ticket id(int)

* 'ticketcl' - Action Date
 * Claimed Date
 * Ticket id(int)

* 'ticketcd' - Aciton Date
 * Closed Date
 * Ticket id(int)
 
## Authors
* **Sw4p** - [mcSw4p](https://github.com/mcSw4p)