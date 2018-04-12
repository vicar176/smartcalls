package com.jiresoft.smartcallblocker.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jiresoft.smartcallblocker.R;
import com.jiresoft.smartcallblocker.data.dao.PhoneNumberDAO;
import com.jiresoft.smartcallblocker.data.model.PhoneNumber;
import com.jiresoft.smartcallblocker.data.model.Type;

public class AddPhoneToListActivity extends AppCompatActivity implements View.OnClickListener {

    // Declaration all on screen components
    private EditText country_code_et, phone_et;
    private Button reset_btn, submit_btn;

    // Declaration of PhoneNumberDAO to interact with SQlite database
    private PhoneNumberDAO phoneNumberDao;

    private String listType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_to_list);

        // Initialization of the DAO object.
        phoneNumberDao = new PhoneNumberDAO(this);

        country_code_et = (EditText) findViewById(R.id.country_code_et);
        phone_et = (EditText) findViewById(R.id.phone_et);
        reset_btn = (Button) findViewById(R.id.reset_btn);
        submit_btn = (Button) findViewById(R.id.submit_btn);

        reset_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        listType = getIntent().getStringExtra("LIST_TYPE");

    }

    @Override
    public void onClick(View v) {

        if(v == submit_btn)
        {
            // All input fields are mandatory, so made a check
            if(country_code_et.getText().toString().trim().length() > 0 &&
                    phone_et.getText().toString().trim().length() > 0)
            {
                // Once click on "Submit", it's first creates the PhoneNumber object
                final PhoneNumber phone = new PhoneNumber();

                // Then, set all the values from user input
                String phoneNumber = country_code_et.getText().toString() + phone_et.getText().toString();
                phone.setNumber(phoneNumber);
                phone.setType(Type.valueOf(listType).getValue());

                // Insert the object to the database
                phoneNumberDao.create(phone);

                // Show the success message to user
                showDialog();
            }
            // Show a dialog with appropriate message in case input fields are blank
            else
            {
                showMessageDialog("All fields are mandatory !!");
            }
        }
        else if(v == reset_btn)
        {
            reset();
        }
    }

    // Clear the entered text
    private void reset()
    {
        country_code_et.setText("");
        phone_et.setText("");
    }

    private void showDialog()
    {
        // After submission, Dialog opens up with "Success" message. So, build the AlartBox first
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Set the appropriate message into it.
        alertDialogBuilder.setMessage("Phone Number added to block list successfully !!");

        // Add a positive button and it's action. In our case action would be, just hide the dialog box ,
        // and erase the user inputs.
        alertDialogBuilder.setPositiveButton("Add More",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        reset();
                    }
                });

        // Add a negative button and it's action. In our case, close the current screen
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        // Now, create the Dialog and show it.
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showMessageDialog(final String message)
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
