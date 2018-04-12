package com.jiresoft.smartcallblocker.data.model;

/**
 * Created by victorariasgonzalez on 2/20/18.
 */

public enum Type {
    BLACK_LIST (1),
    WHITE_LIST (2);

    private final int value;

    Type (int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
