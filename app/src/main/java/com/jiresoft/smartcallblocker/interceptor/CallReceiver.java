package com.jiresoft.smartcallblocker.interceptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.jiresoft.smartcallblocker.activity.MainActivity;
import com.jiresoft.smartcallblocker.data.dao.PhoneNumberDAO;
import com.jiresoft.smartcallblocker.data.model.PhoneNumber;
import com.jiresoft.smartcallblocker.service.PhoneNumberService;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;


/**
 * Created by victorariasgonzalez on 2/15/18.
 */

public class CallReceiver extends BroadcastReceiver {

    private PhoneNumberService phoneNumberService;

    @Override
    public void onReceive(Context context, Intent intent) {
        phoneNumberService = new PhoneNumberService(context);
        if (!intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            return;
        }else {
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            //Toast.makeText(context, " Receiver start, " + number, Toast.LENGTH_SHORT).show();
            if(StringUtils.isNotBlank(number) && phoneNumberService.isInBlackList(number)){
                phoneNumberService.addBlockedPhone(number);
                disconnectPhoneItelephony(context);
            }
            return;
        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void disconnectPhoneItelephony(Context context)
    {
        ITelephony telephonyService;
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        try
        {
            Class c = Class.forName(telephony.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(telephony);
            telephonyService.endCall();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
