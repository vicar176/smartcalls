package com.jiresoft.smartcallblocker.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jiresoft.smartcallblocker.R;
import com.jiresoft.smartcallblocker.data.model.Type;
import com.jiresoft.smartcallblocker.security.PermissionManager;

public class MainActivity extends  AppCompatActivity implements View.OnClickListener {

    private Button call_history_btn;
    private Button black_list_btn;
    private Button white_list_btn;

    private static final int PERMISSIONS_ALL = 1;

    @Override
    protected void onStart() {
        super.onStart();
        PermissionManager.check(this, Manifest.permission.CALL_PHONE, REQUEST_CODE_CALL_PHONE);
        PermissionManager.check(this, Manifest.permission.READ_PHONE_STATE, REQUEST_CODE_READ_PHONE_STATE);

        String [] requestedPermissions = {Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE};
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_CALL_PHONE){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this.getApplicationContext(), " Permissions granted!!!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this.getApplicationContext(), " The app won't work, sorry!", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==REQUEST_CODE_READ_PHONE_STATE) {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this.getApplicationContext(), " Permissions granted!!!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this.getApplicationContext(), " The app won't work, sorry!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialization of the button of the Main screen
        call_history_btn = (Button) findViewById(R.id.call_history_btn);
        black_list_btn = (Button) findViewById(R.id.black_list_btn);
        white_list_btn = (Button) findViewById(R.id.white_list_btn);
        // Attachment of onClickListner for it
        call_history_btn.setOnClickListener(this);
        black_list_btn.setOnClickListener(this);
        white_list_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v == call_history_btn) {
            startActivity(new Intent(this, CallHistoryActivity.class));
        } else if(v == black_list_btn) {
            Intent intent = new Intent(this, PhoneListActivity.class);
            intent.putExtra("LIST_TYPE", Type.BLACK_LIST.name());
            startActivity(intent);
        } else if(v == white_list_btn) {
            Intent intent = new Intent(this, PhoneListActivity.class);
            intent.putExtra("LIST_TYPE", Type.WHITE_LIST.name());
            startActivity(intent);
        }
    }

}
