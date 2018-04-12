package com.jiresoft.smartcallblocker.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jiresoft.smartcallblocker.data.DatabaseHelper;
import com.jiresoft.smartcallblocker.data.model.BlockedPhoneNumber;
import com.jiresoft.smartcallblocker.data.model.PhoneNumber;
import com.jiresoft.smartcallblocker.data.model.Type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by victorariasgonzalez on 2/20/18.
 */

public class BlockedPhoneNumberDAO extends BaseDAO {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_BLOCKEDTIME = "blocked_time";

    public BlockedPhoneNumberDAO(Context context) {
        super(context);
    }

    public BlockedPhoneNumber create(final BlockedPhoneNumber phoneNumber) {

        final ContentValues values = new ContentValues();

        values.put(COLUMN_NUMBER, phoneNumber.getNumber());
        values.put(COLUMN_BLOCKEDTIME, new Date().getTime());

        final long id = getDatabase().insert(DatabaseHelper.TABLE_BLOCKEDPHONENUMBER , null, values);

        phoneNumber.setId(id);
        return phoneNumber;
    }

    public void clearHistory() {
        getDatabase().delete(DatabaseHelper.TABLE_BLOCKEDPHONENUMBER, null, null);
    }

    public List<BlockedPhoneNumber> getPhoneCallHistory() {

        final List<BlockedPhoneNumber> phoneNumberList = new ArrayList<BlockedPhoneNumber>();

        final Cursor cursor = getDatabase().query(DatabaseHelper.TABLE_BLOCKEDPHONENUMBER, new String[]{COLUMN_ID, COLUMN_NUMBER, COLUMN_BLOCKEDTIME}, null, null,null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final BlockedPhoneNumber number = new BlockedPhoneNumber();
            number.setId(cursor.getLong(0));
            number.setNumber(cursor.getString(1));
            number.setBlockedTime(cursor.getLong(2));

            phoneNumberList.add(number);

            cursor.moveToNext();
        }
        return phoneNumberList;
    }

}
