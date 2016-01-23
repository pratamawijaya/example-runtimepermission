package com.pratamawijaya.demoruntimepermission;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.location.LocationRequest;
import com.pratamawijaya.demoruntimepermission.easypermission.AfterPermissionGranted;
import com.pratamawijaya.demoruntimepermission.easypermission.EasyPermissions;
import java.util.List;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.functions.Action1;

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

    String[] permission = { Manifest.permission.ACCESS_FINE_LOCATION };
    if (EasyPermissions.hasPermissions(this, permission)) {
      LocationRequest request = LocationRequest.create() //standard GMS LocationRequest
          .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setNumUpdates(5).setInterval(100);

      ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(this);

      locationProvider.getUpdatedLocation(request).subscribe(new Action1<Location>() {
        @Override public void call(Location location) {
          Log.d("tag", "location : " + location.getLatitude());
          Toast.makeText(WithEasyPermissionActivity.this, "" + location.getLatitude(),
              Toast.LENGTH_SHORT).show();
        }
      });
    } else {
      Log.d("tag", "need permission");
      EasyPermissions.requestPermissions(this, "Access Location dibutuhkan", RP_ACCESS_LOCATION,
          permission);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  @Override public void onPermissionsGranted(List<String> perms) {
    Log.d("tag", "permission granted");
  }

  @Override public void onPermissionsDenied(List<String> perms) {
    Log.d("tag", "permission denied");
  }
}
