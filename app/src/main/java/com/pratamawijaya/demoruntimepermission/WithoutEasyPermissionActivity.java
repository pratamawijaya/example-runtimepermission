package com.pratamawijaya.demoruntimepermission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class WithoutEasyPermissionActivity extends AppCompatActivity {

  private static final int RP_ACCESS_LOCATION = 1;
  private Context context;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_without_easy_permission);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    context = this;

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
  private void accessLocation() {
    // cek apakah sudah memiliki permission untuk access fine location
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      // cek apakah perlu menampilkan info kenapa membutuhkan access fine location
      if (ActivityCompat.shouldShowRequestPermissionRationale(WithoutEasyPermissionActivity.this,
          Manifest.permission.ACCESS_FINE_LOCATION)) {
        Toast.makeText(WithoutEasyPermissionActivity.this, "Access Location dibutuhkan",
            Toast.LENGTH_SHORT).show();
      } else {
        // request permission untuk access fine location
        String[] perm = { Manifest.permission.ACCESS_FINE_LOCATION };
        ActivityCompat.requestPermissions(WithoutEasyPermissionActivity.this, perm,
            RP_ACCESS_LOCATION);
      }
    } else {
      // permission access fine location didapat
      Toast.makeText(WithoutEasyPermissionActivity.this, "Yay, has location permission",
          Toast.LENGTH_SHORT).show();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case RP_ACCESS_LOCATION:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // do location thing
          // access location didapatkan
          Toast.makeText(WithoutEasyPermissionActivity.this, "Yay, has location permission",
              Toast.LENGTH_SHORT).show();
        } else {
          // access location ditolak user
          Toast.makeText(WithoutEasyPermissionActivity.this, "permission ditolak user",
              Toast.LENGTH_SHORT).show();
        }
        return;
    }
  }
}
