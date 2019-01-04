package com.hamaar.meetapresto.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hamaar.meetapresto.R;
import com.hamaar.meetapresto.Utils.AppPermissions;
import com.hamaar.meetapresto.Utils.GlobalHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnAllOrder;
    Button btnNewOrder;
    Button btnInsertMenu;

    private static final String[] ALL_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 901;
    private static final int READ_EXTERNAL_STORAGE_CODE = 902;
    private static final int CAMERA_CODE = 904;
    private static final int ACCESS_CALL_PHONE = 903;
    private static final int ALL_REQUEST_CODE = 999;
    private AppPermissions mRuntimePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRuntimePermission = new AppPermissions(this);

        if (mRuntimePermission.hasPermission(ALL_PERMISSIONS)) {
            GlobalHelper.CreateFolder();
        }else{
            mRuntimePermission.requestPermission(this, ALL_PERMISSIONS, ALL_REQUEST_CODE);
        }


        btnNewOrder =  findViewById(R.id.new_order);

        btnAllOrder = findViewById(R.id.all_order);

        btnInsertMenu =  findViewById(R.id.insert_menu_activity);

        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuListActivity.class);
                startActivity(intent);
            }
        });

        btnAllOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.on_development), Toast.LENGTH_SHORT).show();
            }


        });

        btnInsertMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_3 = new Intent(getApplicationContext(), InsertMenuActivity.class);
                startActivity(i_3);
            }
        });

    }


    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_REQUEST_CODE:
                List<Integer> permissionResults = new ArrayList<>();
                for (int grantResult : grantResults) {
                    permissionResults.add(grantResult);
                }
                if (permissionResults.contains(PackageManager.PERMISSION_DENIED)) {
                    Toast.makeText(this, getString(R.string.all_permission_not_granted), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.all_permission_granted), Toast.LENGTH_SHORT).show();
                    refreshActivity();
                }
                break;
            case WRITE_EXTERNAL_STORAGE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, getString(R.string.write_external_permission_not_granted), Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Write External Storage Permissions granted", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case READ_EXTERNAL_STORAGE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, getString(R.string.read_external_permission_not_granted), Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Read External Storage Permissions granted", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case CAMERA_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, getString(R.string.camera_permission_not_granted), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Camera Permissions granted", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
        }
    }

    private void refreshActivity(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
    }

}
