package org.ketfelgamal.bots;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by Waseem on 23-Aug-16.
 */

public class BotClient {
    public static IDiscordClient client;

    public static void main(String[] args) throws DiscordException, RateLimitException {
        String discordToken = "<Default Discord API Token goes here>";
        String the100Token="<Default the100.io Token goes here>";
        String groupId = "<Default the100.io group ID goes here>";
        if(args.length > 0){
            if(args.length==3){
                discordToken = args[0];
                the100Token = args[1];
                groupId = args[2];
            }
        }
        client = new ClientBuilder().withToken(discordToken).login();
        client.getDispatcher().registerListener(new AnnotationListener(the100Token,groupId));
    }
}
