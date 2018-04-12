package com.jiresoft.smartcallblocker.service;

import android.content.Context;

import com.jiresoft.smartcallblocker.data.dao.BlockedPhoneNumberDAO;
import com.jiresoft.smartcallblocker.data.dao.PhoneNumberDAO;
import com.jiresoft.smartcallblocker.data.model.BlockedPhoneNumber;
import com.jiresoft.smartcallblocker.data.model.PhoneNumber;
import com.jiresoft.smartcallblocker.data.model.Type;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by victorariasgonzalez on 2/20/18.
 */

public class PhoneNumberService {

    private PhoneNumberDAO phoneNumberDAO;
    private BlockedPhoneNumberDAO blockedPhoneNumberDAO;

    public PhoneNumberService(Context context) {
        phoneNumberDAO = new PhoneNumberDAO(context);
        blockedPhoneNumberDAO = new BlockedPhoneNumberDAO(context);
    }

    public boolean isInBlackList(String phoneNumber) {
        boolean isBlackList = false;
        if(StringUtils.isNotBlank(phoneNumber)) {
            List<PhoneNumber> blackList = phoneNumberDAO.getPhoneNumberByType(Type.BLACK_LIST);
            if(blackList.contains(new PhoneNumber(phoneNumber))) {
                isBlackList = true;
            }
        }
        return isBlackList;
    }

    public List<PhoneNumber> getPhoneNumbersList(Type type) {
        List<PhoneNumber> phoneNumberList = phoneNumberDAO.getPhoneNumberByType(type);
        return phoneNumberList;
    }

    public void deletePhoneNumber(PhoneNumber phoneNumber) {
        phoneNumberDAO.delete(phoneNumber);
    }

    public void addBlockedPhone(String phoneNumber) {
        if(StringUtils.isNotBlank(phoneNumber)) {
            BlockedPhoneNumber blockedPhoneNumber = new BlockedPhoneNumber(phoneNumber);
            blockedPhoneNumberDAO.create(blockedPhoneNumber);
        }
    }
}
