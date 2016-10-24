package org.ketfelgamal.the100.apis;

import org.apache.commons.lang3.ClassUtils;
import org.ketfelgamal.bots.TimezoneUtils;
import org.ketfelgamal.the100.apis.dto.GamingSession;
import org.ketfelgamal.the100.apis.dto.Session;
import org.ketfelgamal.the100.apis.dto.User;
import org.ketfelgamal.the100.apis.dto.common.The100Object;
import org.ketfelgamal.the100.apis.utils.The100Utils;

import javax.naming.NamingException;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Waseem on 23-Aug-16.
 */
public class The100Facade {
    private static final String BACKUP_TOKEN = "<YOUR_FALLBACK_TOKEN_HERE>";//
    private static final String USERS = "/users";
    private static final String GAMING_SESSIONS = "/gaming_sessions";
    private static final String STATUSES = "/statuses";
    private static final String DEFAULT_GROUP_ID = "<YOUR_FALLBACK_GROUP_ID>";
    private final String groupId;
    private final String theToken;

    public The100Facade(String the100Token, String groupId){
        this.groupId = groupId;
        this.theToken = the100Token;
    }

    public The100Facade(){
        this.groupId=DEFAULT_GROUP_ID;
        this.theToken = BACKUP_TOKEN;
    }


    public List<User> getAllUsers() throws IllegalAccessException, ParseException, IntrospectionException, IOException, InstantiationException, NamingException, InvocationTargetException {
        return The100Utils.retrieveThe100Objects(theToken, this.groupId + USERS, null, "?page=all", User.class);
    }

    public List<GamingSession> getCurrentGamingSessions() throws IllegalAccessException, ParseException, IntrospectionException, IOException, InstantiationException, NamingException, InvocationTargetException {
        return The100Utils.retrieveThe100Objects(theToken, this.groupId + GAMING_SESSIONS, null, null, GamingSession.class);
    }

    public String getFormattedCurrentGamingSessions(List<GamingSession> gamingSessions, String timezone) throws IllegalAccessException, ParseException, IntrospectionException, IOException, InstantiationException, NamingException, InvocationTargetException {

        StringBuilder builder = new StringBuilder();
        if (gamingSessions != null && gamingSessions.size() > 0) {
            builder.append("Group: **" + gamingSessions.get(0).getGroupName() + "**\r\n\r\n");
            for (GamingSession s : gamingSessions) {
                builder.append(getFormattedCurrentGamingSession(s, timezone, false));
            }
        } else {
            builder.append("No sessions founds!");
        }
        return builder.toString();
    }

    public String getFormattedCurrentGamingSession(GamingSession s, String timezone, boolean withLinks) throws IllegalAccessException, ParseException, IntrospectionException, IOException, InstantiationException, NamingException, InvocationTargetException {

        StringBuilder builder = new StringBuilder();
        builder.append("__**" + s.getName() + "\t(" + s.getCategory() + ")**__(" + s.getGamingId() + ")\r\n");
        builder.append("*" + TimezoneUtils.formatDate(s.getStartTime(),timezone) + "* \r\n");
        builder.append("**Members**\r\n");
        int i = 1;
        for (Session session : s.getConfirmedSessions()) {
            builder.append("\t" + i + ". " + session.getUser().getGamertag() + "\t" + (session.getReserveSpot().equals("true") ? "(Reserve)" : "") + "\r\n");
            i++;
        }
        if (withLinks) {
            builder.append("https://www.the100.io/game/" + s.getGamingId());
        }
        builder.append("\r\n\r\n");
        return builder.toString();
    }

    public static void main(String[] args) throws IllegalAccessException, ParseException, IntrospectionException, IOException, InstantiationException, NamingException, InvocationTargetException {
        The100Facade tester = new The100Facade();

        //System.out.println(tester.getFormattedCurrentGamingSessions("1601"));
    }


}
