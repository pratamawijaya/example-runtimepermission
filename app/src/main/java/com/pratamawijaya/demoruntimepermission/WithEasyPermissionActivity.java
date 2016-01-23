package com.pratamawijaya.demoruntimepermission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.pratamawijaya.demoruntimepermission.easypermission.AfterPermissionGranted;
import com.pratamawijaya.demoruntimepermission.easypermission.EasyPermissions;
import java.util.List;

public class WithEasyPermissionActivity extends AppCompatActivity
    implements EasyPermissions.PermissionCallbacks {

  private static final int RP_ACCESS_LOCATION = 1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_with_easy_permission);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        accessLocation();
      }
    });
  }

  /**
   * get access location
   */
  @AfterPermissionGranted(RP_ACCESS_LOCATION) private void accessLocation() {
    String perm[] = { Manifest.permission.ACCESS_FINE_LOCATION };
    if (EasyPermissions.hasPermissions(this, perm)) {
      Toast.makeText(WithEasyPermissionActivity.this, "Yay, has location permission",
          Toast.LENGTH_SHORT).show();
    } else {
      EasyPermissions.requestPermissions(this, "Access Location dibutuhkan", RP_ACCESS_LOCATION,
          perm);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  @Override public void onPermissionsGranted(List<String> perms) {

  }

  @Override public void onPermissionsDenied(List<String> perms) {

  }
}
