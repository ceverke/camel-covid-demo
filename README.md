# camel-covid-demo
Demo application introducing Apache Camel. A detailed introduction (German only) describing the purposes of this project is published in "Entwickler Magazin", a German magazine for software developers.

More details: https://entwickler.de/programmierung/ein-kamel-macht-noch-keine-wuste

## Overview
This demo application queries Covid-19 data from Johns-Hopkins-University (JHU CSSE COVID-19 Data, URL https://github.com/CSSEGISandData/COVID-19). After preparing the data, it will sent to a Telegram chat bot. 


## Configuration
You need to configure _covid.properties_ and insert your own authid and chatid for using the Telegram bot. More details: https://core.telegram.org/bots/#3-how-do-i-create-a-bot

 
## How to build

To build this project on your local machine use

    mvn install

## How to run

You can run this example using the Apache Maven command

    mvn camel:run
    
## About the author
Christian Everke (christian.everke [repalce_with_at@] adesso.de).

Twitter:  https://twitter.com/christianeverke<BR>
XING: https://www.xing.com/profile/Christian_Everke/<BR>
LinkedIn: https://www.linkedin.com/in/christian-everke-a77a9b192/<BR>

http://blog.adesso.de<BR>
www.adesso.de
