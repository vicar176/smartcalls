package com.jiresoft.smartcallblocker.data.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jiresoft.smartcallblocker.R;
import com.jiresoft.smartcallblocker.data.model.BlockedPhoneNumber;
import com.jiresoft.smartcallblocker.data.model.PhoneNumber;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by victorariasgonzalez on 2/20/18.
 */

public class CustomArrayAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;

    // This would hold the database objects i.e. Blacklist
    private List<PhoneNumber> records;

    @SuppressWarnings("unchecked")
    public CustomArrayAdapter(Context context, int resource, @SuppressWarnings("rawtypes") List objects) {
        super(context, resource, objects);

        this.records = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Reuse the view to make the scroll effect smooth


        // Fetch phone number from the database object
        Object object = records.get(position);
        String phoneNumber = StringUtils.EMPTY;
        boolean isBlockedPhone = false;
        if(object instanceof PhoneNumber) {
            if (convertView == null)
                convertView = inflater.inflate(R.layout.list_item, parent, false);
            phoneNumber = ((PhoneNumber) object).getNumber();
        } else if (object instanceof BlockedPhoneNumber) {
            if (convertView == null)
                convertView = inflater.inflate(R.layout.list_history_item, parent, false);
            phoneNumber = ((BlockedPhoneNumber) object).getNumber();
            isBlockedPhone = true;
        }
        //final PhoneNumber phoneNumber = records.get(position);

        // Set to screen component to display results
        ((TextView) convertView.findViewById(R.id.serial_tv)).setText("" + (position + 1));
        ((TextView) convertView.findViewById(R.id.phone_number_tv)).setText((CharSequence) phoneNumber);
        if(isBlockedPhone) {
            String date = DateConverter.dateToString(((BlockedPhoneNumber)object).getBlockedTime());
            TextView t = ((TextView) convertView.findViewById(R.id.date));
            if(t != null) {
                t.setText(date);
            }

        }
        return convertView;
    }
}
