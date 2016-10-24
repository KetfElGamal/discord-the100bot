package org.ketfelgamal.the100.apis.dto;

import org.ketfelgamal.the100.apis.dto.common.The100Field;
import org.ketfelgamal.the100.apis.dto.common.The100Object;

/**
 * Created by Waseem on 17-Oct-16.
 */
public class Session implements The100Object {

    @The100Field(name="reserve_spot")
    private String reserveSpot;

    @The100Field(name="user_id")
    private Long userId;

    @The100Field(name="user")
    private User user;

    public String getReserveSpot() {
        return reserveSpot;
    }

    public void setReserveSpot(String reserveSpot) {
        this.reserveSpot = reserveSpot;
    }



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
