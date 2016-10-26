# discord-the100bot
The 100 bot for Discord

## Usage

1. If you are going to use Maven for Execution, make sure you change the settings inside the pom.xml
2. Optionally you can also change BotClient.java and The100Facade.java if you want to run the application without any arguments given

## Running the bot

To run using maven execute the following command: 

``mvn exec:java -Dexec.mainClass="org.ketfelgamal.bots.BotClient"``

## Commands

The commands are as follows:

``@The100Bot sessions [TimeZone / ± Hours] ``   

``@The100Bot session session_id [TimeZone / ± Hours] ``  

``@The100Bot link session_id``

### TimeZone Rules

TimeZone can be 'Z', the result is ZoneOffset.UTC. (That is the default if you omit TimeZone)
TimeZone can start with '+' or '-' and then the number of hours for precise offset.
TimeZone can be 'GMT', 'UTC' or 'UT'.
TimeZone can start with 'UTC+', 'UTC-', 'GMT+', 'GMT-', 'UT+' or 'UT-' then the ID is a prefixed offset-based ID.
Otherwise the TimeZone is following the IANA Time Zone Database (TZDB). This has region IDs of the form '{area}/{city}', such as 'Europe/Paris' or 'America/New_York'.

## Changing the Code

This project can be opened using IntelliJ Idea, so load the project and start playing :)
