package org.ketfelgamal.the100.apis.dto;

import org.ketfelgamal.the100.apis.dto.common.The100Field;
import org.ketfelgamal.the100.apis.dto.common.The100Object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Waseem on 23-Aug-16.
 */
public class GamingSession implements The100Object {

    @The100Field(name="id")
    private String gamingId;

    @The100Field(name="name")
    private String name;

    @The100Field(name="start_time")
    private Date startTime;

    @The100Field(name = "category")
    private String category;

    @The100Field(name = "creator_gamertag")
    private String creatorGamertag;

    @The100Field(name="confirmed_sessions")
    private List<Session> confirmedSessions;

    @The100Field(name="group_name")
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public String getGamingId() {
        return gamingId;
    }

    public void setGamingId(String gamingId) {
        this.gamingId = gamingId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatorGamertag() {
        return creatorGamertag;
    }

    public void setCreatorGamertag(String creatorGamertag) {
        this.creatorGamertag = creatorGamertag;
    }

    public List<Session> getConfirmedSessions() {
        if(confirmedSessions == null)
            confirmedSessions = new ArrayList<>();

        return confirmedSessions;
    }

    public void setConfirmedSessions(List<Session> confirmedSessions) {
        this.confirmedSessions = confirmedSessions;
    }
}
