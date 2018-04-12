package com.jiresoft.smartcallblocker.data.model;

import java.util.Date;

/**
 * Created by victorariasgonzalez on 2/19/18.
 */

public class BlockedPhoneNumber {

    private long id;
    private String number;
    private long blockedTime;

    public BlockedPhoneNumber() {

    }

    public BlockedPhoneNumber(String phoneNumber) {
        this.number = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getBlockedTime() {
        return blockedTime;
    }

    public void setBlockedTime(long blockedTime) {
        this.blockedTime = blockedTime;
    }
}
