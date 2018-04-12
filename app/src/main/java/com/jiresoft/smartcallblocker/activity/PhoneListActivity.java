package com.jiresoft.smartcallblocker.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jiresoft.smartcallblocker.R;
import com.jiresoft.smartcallblocker.data.model.PhoneNumber;
import com.jiresoft.smartcallblocker.data.model.Type;
import com.jiresoft.smartcallblocker.data.util.CustomArrayAdapter;
import com.jiresoft.smartcallblocker.service.PhoneNumberService;

import java.util.List;

public class PhoneListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener{

    // Declaration all on screen components of the Main screen
    private Button add_phone_to_list_btn;
    public ListView listview;

    private String listType;

    // It holds the list of Blacklist objects fetched from Database
    public static List<PhoneNumber> phoneNumbersList;
    PhoneNumberService phoneNumberService;

    // This holds the value of the row number, which user has selected for further action
    private int selectedRecordPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        phoneNumberService = new PhoneNumberService(this.getApplicationContext());

        setContentView(R.layout.activity_black_list);

        add_phone_to_list_btn = (Button) findViewById(R.id.add_blacklist_btn);
        add_phone_to_list_btn.setOnClickListener(this);


        listview = (ListView) findViewById(R.id.listview);

        // Set the header of the ListView
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_item, listview, false);
        listview.addHeaderView(rowView);

        // Attach OnItemLongClickListener to track user action and perform accordingly
        listview.setOnItemLongClickListener(this);
    }

    private void populateNoRecordMsg()
    {
        // If, no record found in the database, appropriate message needs to be displayed.
        if(phoneNumbersList.size() == 0)
        {
            final TextView tv = new TextView(this);
            tv.setPadding(5, 5, 5, 5);
            tv.setTextSize(15);
            tv.setText("No Record Found !!");
            listview.addFooterView(tv);
        }
    }

    @Override
    public void onClick(View v) {
        // Render AddToBlocklistActivity screen once click on "Add" Button
        if (v == add_phone_to_list_btn) {
            Intent intent = new Intent(this, AddPhoneToListActivity.class);
            intent.putExtra("LIST_TYPE", listType);
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // If the pressed row is not a header, update selectedRecordPosition and
        // show dialog for further selection
        if (position > 0) {
            selectedRecordPosition = position - 1;
            showDialog();
        }
        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();

        listType = getIntent().getStringExtra("LIST_TYPE");

        // Fetch the list of Black listed numbers from Database using DAO object
        phoneNumbersList = phoneNumberService.getPhoneNumbersList(Type.valueOf(listType));

        // Remove the footer view
        if(listview.getChildCount() > 1)
            listview.removeFooterView(listview.getChildAt(listview.getChildCount() - 1));

        //Now, link the  CustomArrayAdapter with the ListView
        listview.setAdapter(new CustomArrayAdapter(this, R.layout.list_item, phoneNumbersList));

        // If, no record found in the database, appropriate message needs to be displayed.
        populateNoRecordMsg();
    }

    private void showDialog()
    {
        // Before deletion of the long pressed record, need to confirm with the user. So, build the AlartBox first
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Set the appropriate message into it.
        alertDialogBuilder.setMessage("Are you Really want to delete the selected record ?");

        // Add a positive button and it's action. In our case action would be deletion of the data
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {

                            phoneNumberService.deletePhoneNumber(phoneNumbersList.get(selectedRecordPosition));
                            // Removing the same from the List to remove from display as well
                            phoneNumbersList.remove(selectedRecordPosition);
                            listview.invalidateViews();

                            // Reset the value of selectedRecordPosition
                            selectedRecordPosition = -1;
                            populateNoRecordMsg();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        // Add a negative button and it's action. In our case, just hide the dialog box
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        // Now, create the Dialog and show it.
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
