package com.jiresoft.smartcallblocker.data.model;

/**
 * Created by victorariasgonzalez on 2/15/18.
 */

public class PhoneNumber {

    private long id;
    private String number;
    private int type;

    public PhoneNumber () {
    }

    public PhoneNumber (String phoneNumber) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj.getClass().isInstance(new PhoneNumber())) {
            final PhoneNumber bl = (PhoneNumber) obj;
            if (bl.number.equalsIgnoreCase(this.number))
                return true;
        }
        return false;
    }
}
