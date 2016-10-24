package org.ketfelgamal.the100.apis.dto;

import org.ketfelgamal.the100.apis.dto.common.The100Field;
import org.ketfelgamal.the100.apis.dto.common.The100Object;

/**
 * Created by Waseem on 23-Aug-16.
 */
public class User implements The100Object{

    @The100Field(name="id")
    private Long userId;

    @The100Field(name="gamertag")
    private String gamertag;

    /*
    @The100Field(name = "time_zone")
    private String timezone;



    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
*/

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGamertag() {
        return gamertag;
    }

    public void setGamertag(String gamertag) {
        this.gamertag = gamertag;
    }
}
