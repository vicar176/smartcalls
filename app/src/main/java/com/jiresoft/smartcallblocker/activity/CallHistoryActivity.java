package com.jiresoft.smartcallblocker.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.jiresoft.smartcallblocker.R;
import com.jiresoft.smartcallblocker.data.dao.BlockedPhoneNumberDAO;
import com.jiresoft.smartcallblocker.data.model.BlockedPhoneNumber;
import com.jiresoft.smartcallblocker.data.model.Type;
import com.jiresoft.smartcallblocker.data.util.CustomArrayAdapter;

import java.util.List;

public class CallHistoryActivity extends AppCompatActivity {



    // Object of BlacklistDAO to query to database
    private BlockedPhoneNumberDAO blockedPhoneNumberDao;

    // It holds the list of Blacklist objects fetched from Database
    public static List<BlockedPhoneNumber> blockList;

    public ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);

        // Initialization of the listview of the Main screen to display black listed phone numbers
        listview = (ListView) findViewById(R.id.listHistoryView);

        // Set the header of the ListView
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_history_item, listview, false);
        listview.addHeaderView(rowView);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Initialize the DAO object
        blockedPhoneNumberDao = new BlockedPhoneNumberDAO(this);

        // Fetch the list of Black listed numbers from Database using DAO object
        blockList = blockedPhoneNumberDao.getPhoneCallHistory();

        // Remove the footer view
        if(listview.getChildCount() > 1)
            listview.removeFooterView(listview.getChildAt(listview.getChildCount() - 1));

        //Now, link the  CustomArrayAdapter with the ListView
        listview.setAdapter(new CustomArrayAdapter(this, R.layout.list_history_item, blockList));

    }
}
