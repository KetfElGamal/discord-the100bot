package org.ketfelgamal.bots;

import org.ketfelgamal.bots.BotClient;
import org.ketfelgamal.the100.apis.The100Facade;
import org.ketfelgamal.the100.apis.dto.GamingSession;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MentionEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import javax.naming.NamingException;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Amr on 23-Aug-16.
 */
public class AnnotationListener {
    private final String the100Token;
    private final String groupId;

    public AnnotationListener(String the100Token, String groupId){
        this.the100Token = the100Token;
        this.groupId=groupId;
    }

    @EventSubscriber
    public void onMention(MentionEvent event) {
        String originalContent = event.getMessage().getContent();
        String replacedContent = originalContent.replaceFirst("<.*>", "").trim();
        System.out.println(replacedContent);

        try {
            String[] parameters = replacedContent.split("\\s");
            String command = parameters[0];

            The100Facade facade = new The100Facade(the100Token,groupId);

            List<GamingSession> sessions = facade.getCurrentGamingSessions();

            String timezone = "UTC";

            if (command.equalsIgnoreCase("sessions")) {
                if(parameters.length > 1){
                    timezone = parameters[1];
                }
                for(GamingSession session: sessions) {
                    new MessageBuilder(BotClient.client).withChannel(event.getMessage().getChannel()).withContent(facade.getFormattedCurrentGamingSession(session, timezone, true)).build();
                }
            } else if (command.equalsIgnoreCase("link")) {
                String gamingId = parameters[1];
                new MessageBuilder(BotClient.client).withChannel(event.getMessage().getChannel()).withContent("https://www.the100.io/game/" + gamingId).build();
            } else if (command.equalsIgnoreCase("session")) {
                if(parameters.length > 2){
                    timezone = parameters[2];
                }
                String sessionId = parameters[1];
                for (GamingSession session : sessions) {
                    if (session.getGamingId().equals(sessionId)) {
                        new MessageBuilder(BotClient.client).withChannel(event.getMessage().getChannel()).withContent(facade.getFormattedCurrentGamingSession(session, timezone, true)).build();
                        break;
                    }
                }
            }
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
